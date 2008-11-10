/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
import converter.EventListConverter;
import session.EventFacadeLocal;

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
        try {
            return new EventsConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
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
        try {
            Event entity = data.getEntity();
            createEntity(entity);
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
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
        
        return lookupEventFacade().findAll(start, max);
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Event entity) {
        lookupEventFacade().create(entity);
    }

    @Path("list/")
    @GET
    @Produces({"application/json"})
    public EventListConverter list(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max, @QueryParam("query")
    @DefaultValue("SELECT e FROM Event e")
    String query) {
        try {
            return new EventListConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), uriInfo.getBaseUri());
        } finally {

        }
    }

    private EventFacadeLocal lookupEventFacade() {
        try {
            javax.naming.Context c = new InitialContext();
            return (EventFacadeLocal) c.lookup("java:comp/env/EventFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
