/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import java.util.Collection;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import com.sun.jersey.api.core.ResourceContext;
import javax.persistence.EntityManager;
import persistence.Filter;
import converter.TimeframesConverter;
import converter.TimeframeConverter;
import persistence.Timeframe;

/**
 *
 * @author dave
 */

@Path("/timeframes/")
public class TimeframesResource {
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
  
    /** Creates a new instance of TimeframesResource */
    public TimeframesResource() {
    }

    /**
     * Get method for retrieving a collection of Timeframe instance in XML format.
     *
     * @return an instance of TimeframesConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public TimeframesConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max, @QueryParam("expandLevel")
    @DefaultValue("1")
    int expandLevel, @QueryParam("query")
    @DefaultValue("SELECT e FROM Timeframe e")
    String query) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            return new TimeframesConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
            persistenceSvc.commitTx();
            persistenceSvc.close();
        }
    }

    /**
     * Post method for creating an instance of Timeframe using XML as the input format.
     *
     * @param data an TimeframeConverter entity that is deserialized from an XML stream
     * @return an instance of TimeframeConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(TimeframeConverter data) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            EntityManager em = persistenceSvc.getEntityManager();
            Timeframe entity = data.resolveEntity(em);
            createEntity(data.resolveEntity(em));
            persistenceSvc.commitTx();
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Returns a dynamic instance of TimeframeResource used for entity navigation.
     *
     * @return an instance of TimeframeResource
     */
    @Path("{id}/")
    public service.TimeframeResource getTimeframeResource(@PathParam("id")
    String id) {
        TimeframeResource resource = resourceContext.getResource(TimeframeResource.class);
        resource.setId(id);
        return resource;
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Timeframe instances
     */
    protected Collection<Timeframe> getEntities(int start, int max, String query) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        return em.createQuery(query).setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Timeframe entity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        em.persist(entity);
        for (Filter value : entity.getFilterCollection()) {
            Timeframe oldEntity = value.getTimeframeId();
            value.setTimeframeId(entity);
            if (oldEntity != null) {
                oldEntity.getFilterCollection().remove(entity);
            }
        }
    }
}
