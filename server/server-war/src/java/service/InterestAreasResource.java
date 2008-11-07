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
import persistence.InterestArea;
import persistence.Organization;
import persistence.Filter;
import persistence.SourceInterestMap;
import converter.InterestAreasConverter;
import converter.InterestAreaConverter;

/**
 *
 * @author dave
 */

@Path("/interestAreas/")
public class InterestAreasResource {
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
  
    /** Creates a new instance of InterestAreasResource */
    public InterestAreasResource() {
    }

    /**
     * Get method for retrieving a collection of InterestArea instance in XML format.
     *
     * @return an instance of InterestAreasConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public InterestAreasConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max, @QueryParam("expandLevel")
    @DefaultValue("1")
    int expandLevel, @QueryParam("query")
    @DefaultValue("SELECT e FROM InterestArea e")
    String query) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            return new InterestAreasConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
            persistenceSvc.commitTx();
            persistenceSvc.close();
        }
    }

    /**
     * Post method for creating an instance of InterestArea using XML as the input format.
     *
     * @param data an InterestAreaConverter entity that is deserialized from an XML stream
     * @return an instance of InterestAreaConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(InterestAreaConverter data) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            EntityManager em = persistenceSvc.getEntityManager();
            InterestArea entity = data.resolveEntity(em);
            createEntity(data.resolveEntity(em));
            persistenceSvc.commitTx();
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Returns a dynamic instance of InterestAreaResource used for entity navigation.
     *
     * @return an instance of InterestAreaResource
     */
    @Path("{id}/")
    public service.InterestAreaResource getInterestAreaResource(@PathParam("id")
    String id) {
        InterestAreaResource resource = resourceContext.getResource(InterestAreaResource.class);
        resource.setId(id);
        return resource;
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of InterestArea instances
     */
    protected Collection<InterestArea> getEntities(int start, int max, String query) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        return em.createQuery(query).setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(InterestArea entity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        em.persist(entity);
        for (Event value : entity.getEventCollection()) {
            value.getInterestAreaCollection().add(entity);
        }
        for (Organization value : entity.getOrganizationCollection()) {
            value.getInterestAreaCollection().add(entity);
        }
        for (Filter value : entity.getFilterCollection()) {
            value.getInterestAreaCollection().add(entity);
        }
        for (SourceInterestMap value : entity.getSourceInterestMapCollection()) {
            InterestArea oldEntity = value.getInterestAreaId();
            value.setInterestAreaId(entity);
            if (oldEntity != null) {
                oldEntity.getSourceInterestMapCollection().remove(entity);
            }
        }
    }
}
