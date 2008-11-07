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
import persistence.Source;
import persistence.InterestArea;
import persistence.Organization;
import persistence.Location;
import persistence.Timestamp;
import converter.EventsConverter;
import converter.EventConverter;

/**
 *
 * @author dave
 */

@Path("/events/")
public class EventsResource {
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
  
    /** Creates a new instance of EventsResource */
    public EventsResource() {
    }

    /**
     * Get method for retrieving a collection of Event instance in XML format.
     *
     * @return an instance of EventsConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public EventsConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max, @QueryParam("expandLevel")
    @DefaultValue("1")
    int expandLevel, @QueryParam("query")
    @DefaultValue("SELECT e FROM Event e")
    String query) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            return new EventsConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
            persistenceSvc.commitTx();
            persistenceSvc.close();
        }
    }

    /**
     * Post method for creating an instance of Event using XML as the input format.
     *
     * @param data an EventConverter entity that is deserialized from an XML stream
     * @return an instance of EventConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(EventConverter data) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            EntityManager em = persistenceSvc.getEntityManager();
            Event entity = data.resolveEntity(em);
            createEntity(data.resolveEntity(em));
            persistenceSvc.commitTx();
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Returns a dynamic instance of EventResource used for entity navigation.
     *
     * @return an instance of EventResource
     */
    @Path("{id}/")
    public service.EventResource getEventResource(@PathParam("id")
    String id) {
        EventResource resource = resourceContext.getResource(EventResource.class);
        resource.setId(id);
        return resource;
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Event instances
     */
    protected Collection<Event> getEntities(int start, int max, String query) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        return em.createQuery(query).setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Event entity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        em.persist(entity);
        for (InterestArea value : entity.getInterestAreaCollection()) {
            value.getEventCollection().add(entity);
        }
        for (Timestamp value : entity.getTimestampCollection()) {
            value.getEventCollection().add(entity);
        }
        for (Location value : entity.getLocationCollection()) {
            value.getEventCollection().add(entity);
        }
        for (Organization value : entity.getOrganizationCollection()) {
            value.getEventCollection().add(entity);
        }
        Source sourceId = entity.getSourceId();
        if (sourceId != null) {
            sourceId.getEventCollection().add(entity);
        }
    }
}
