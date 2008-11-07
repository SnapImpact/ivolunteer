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
import persistence.Location;
import persistence.Organization;
import converter.LocationsConverter;
import converter.LocationConverter;

/**
 *
 * @author dave
 */

@Path("/locations/")
public class LocationsResource {
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
  
    /** Creates a new instance of LocationsResource */
    public LocationsResource() {
    }

    /**
     * Get method for retrieving a collection of Location instance in XML format.
     *
     * @return an instance of LocationsConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public LocationsConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max, @QueryParam("expandLevel")
    @DefaultValue("1")
    int expandLevel, @QueryParam("query")
    @DefaultValue("SELECT e FROM Location e")
    String query) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            return new LocationsConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
            persistenceSvc.commitTx();
            persistenceSvc.close();
        }
    }

    /**
     * Post method for creating an instance of Location using XML as the input format.
     *
     * @param data an LocationConverter entity that is deserialized from an XML stream
     * @return an instance of LocationConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(LocationConverter data) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            EntityManager em = persistenceSvc.getEntityManager();
            Location entity = data.resolveEntity(em);
            createEntity(data.resolveEntity(em));
            persistenceSvc.commitTx();
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Returns a dynamic instance of LocationResource used for entity navigation.
     *
     * @return an instance of LocationResource
     */
    @Path("{id}/")
    public service.LocationResource getLocationResource(@PathParam("id")
    String id) {
        LocationResource resource = resourceContext.getResource(LocationResource.class);
        resource.setId(id);
        return resource;
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Location instances
     */
    protected Collection<Location> getEntities(int start, int max, String query) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        return em.createQuery(query).setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Location entity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        em.persist(entity);
        for (Organization value : entity.getOrganizationCollection()) {
            value.getLocationCollection().add(entity);
        }
        for (Event value : entity.getEventCollection()) {
            value.getLocationCollection().add(entity);
        }
    }
}
