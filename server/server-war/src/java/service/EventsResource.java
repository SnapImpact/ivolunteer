/*
 *  EventsResource
 *
 * Created on October 24, 2008, 9:55 PM
 *
 * To change this template, choose Tools | Template Manager
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
import converter.EventsConverter;
import converter.EventsListConverter;
import converter.EventConverter;
import persistence.Events;
import session.EventsFacadeLocal;


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
    @Produces({"application/xml", "application/json"})
    public EventsConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max) {
        try {
            return new EventsConverter(getEntities(start, max), context.getAbsolutePath());
        } finally {
            
        }
    }

    /**
     * Post method for creating an instance of Events using XML as the input format.
     *
     * @param data an EventConverter entity that is deserialized from an XML stream
     * @return an instance of EventConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(EventConverter data) {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            Events entity = data.getEntity();
            createEntity(entity);
            
            return Response.created(context.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            
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
        return lookupEventsFacade().findAll(start, max);
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Events entity) {
        lookupEventsFacade().create(entity);
    }
    
    @Path("list/")
    @GET
    @Produces({"application/json"})
    public EventsListConverter list(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max) {
        try {
            return new EventsListConverter(getEntities(start, max), context.getAbsolutePath(), context.getBaseUri());
        } finally {
            
        }
    }

    private EventsFacadeLocal lookupEventsFacade() {
        try {
            javax.naming.Context c = new InitialContext();
            return (EventsFacadeLocal) c.lookup("java:comp/env/EventsFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
