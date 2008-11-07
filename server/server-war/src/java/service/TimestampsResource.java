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
import persistence.Event;
import converter.TimestampsConverter;
import converter.TimestampConverter;
import persistence.Timestamp;

/**
 *
 * @author dave
 */

@Path("/timestamps/")
public class TimestampsResource {
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
  
    /** Creates a new instance of TimestampsResource */
    public TimestampsResource() {
    }

    /**
     * Get method for retrieving a collection of Timestamp instance in XML format.
     *
     * @return an instance of TimestampsConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public TimestampsConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max, @QueryParam("expandLevel")
    @DefaultValue("1")
    int expandLevel, @QueryParam("query")
    @DefaultValue("SELECT e FROM Timestamp e")
    String query) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            return new TimestampsConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
            persistenceSvc.commitTx();
            persistenceSvc.close();
        }
    }

    /**
     * Post method for creating an instance of Timestamp using XML as the input format.
     *
     * @param data an TimestampConverter entity that is deserialized from an XML stream
     * @return an instance of TimestampConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(TimestampConverter data) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            EntityManager em = persistenceSvc.getEntityManager();
            Timestamp entity = data.resolveEntity(em);
            createEntity(data.resolveEntity(em));
            persistenceSvc.commitTx();
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Returns a dynamic instance of TimestampResource used for entity navigation.
     *
     * @return an instance of TimestampResource
     */
    @Path("{id}/")
    public service.TimestampResource getTimestampResource(@PathParam("id")
    String id) {
        TimestampResource resource = resourceContext.getResource(TimestampResource.class);
        resource.setId(id);
        return resource;
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Timestamp instances
     */
    protected Collection<Timestamp> getEntities(int start, int max, String query) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        return em.createQuery(query).setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Timestamp entity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        em.persist(entity);
        for (Event value : entity.getEventCollection()) {
            value.getTimestampCollection().add(entity);
        }
    }
}
