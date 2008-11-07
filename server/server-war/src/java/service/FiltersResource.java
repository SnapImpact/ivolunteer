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
import persistence.InterestArea;
import persistence.OrganizationType;
import persistence.Timeframe;
import persistence.Distance;
import persistence.IvUser;
import converter.FiltersConverter;
import converter.FilterConverter;

/**
 *
 * @author dave
 */

@Path("/filters/")
public class FiltersResource {
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
  
    /** Creates a new instance of FiltersResource */
    public FiltersResource() {
    }

    /**
     * Get method for retrieving a collection of Filter instance in XML format.
     *
     * @return an instance of FiltersConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public FiltersConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max, @QueryParam("expandLevel")
    @DefaultValue("1")
    int expandLevel, @QueryParam("query")
    @DefaultValue("SELECT e FROM Filter e")
    String query) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            return new FiltersConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
            persistenceSvc.commitTx();
            persistenceSvc.close();
        }
    }

    /**
     * Post method for creating an instance of Filter using XML as the input format.
     *
     * @param data an FilterConverter entity that is deserialized from an XML stream
     * @return an instance of FilterConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(FilterConverter data) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            EntityManager em = persistenceSvc.getEntityManager();
            Filter entity = data.resolveEntity(em);
            createEntity(data.resolveEntity(em));
            persistenceSvc.commitTx();
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Returns a dynamic instance of FilterResource used for entity navigation.
     *
     * @return an instance of FilterResource
     */
    @Path("{id}/")
    public service.FilterResource getFilterResource(@PathParam("id")
    String id) {
        FilterResource resource = resourceContext.getResource(FilterResource.class);
        resource.setId(id);
        return resource;
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Filter instances
     */
    protected Collection<Filter> getEntities(int start, int max, String query) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        return em.createQuery(query).setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Filter entity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        em.persist(entity);
        for (OrganizationType value : entity.getOrganizationTypeCollection()) {
            value.getFilterCollection().add(entity);
        }
        for (InterestArea value : entity.getInterestAreaCollection()) {
            value.getFilterCollection().add(entity);
        }
        Distance distanceId = entity.getDistanceId();
        if (distanceId != null) {
            distanceId.getFilterCollection().add(entity);
        }
        IvUser userId = entity.getUserId();
        if (userId != null) {
            userId.getFilterCollection().add(entity);
        }
        Timeframe timeframeId = entity.getTimeframeId();
        if (timeframeId != null) {
            timeframeId.getFilterCollection().add(entity);
        }
    }
}
