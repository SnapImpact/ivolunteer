/*
 *  EventsResource
 *
 * Created on October 11, 2008, 9:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package service;

import java.util.Collection;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.ProduceMime;
import javax.ws.rs.ConsumeMime;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import converter.EventsConverter;
import converter.EventConverter;
import persistence.Events;


/**
 *
 * @author dave
 */

@Path("/events/")
public class EventsResource {
    @Context
    private UriInfo context;
  
    /** Creates a new instance of EventsResource */
    public EventsResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public EventsResource(UriInfo context) {
        this.context = context;
    }

    /**
     * Get method for retrieving a collection of Events instance in XML format.
     *
     * @return an instance of EventsConverter
     */
    @GET
    @ProduceMime({"application/xml", "application/json"})
    public EventsConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max) {
        try {
            return new EventsConverter(getEntities(start, max), context.getAbsolutePath());
        } finally {
            PersistenceService.getInstance().close();
        }
    }

    /**
     * Post method for creating an instance of Events using XML as the input format.
     *
     * @param data an EventConverter entity that is deserialized from an XML stream
     * @return an instance of EventConverter
     */
    @POST
    @ConsumeMime({"application/xml", "application/json"})
    public Response post(EventConverter data) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            Events entity = data.getEntity();
            createEntity(entity);
            persistenceSvc.commitTx();
            return Response.created(context.getAbsolutePath().resolve(entity.getId() + "/")).build();
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
    public EventResource getEventResource(@PathParam("id")
    String id) {
        return new EventResource(id, context);
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Events instances
     */
    protected Collection<Events> getEntities(int start, int max) {
        return PersistenceService.getInstance().createQuery("SELECT e FROM Events e").setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Events entity) {
        PersistenceService.getInstance().persistEntity(entity);
    }
}
