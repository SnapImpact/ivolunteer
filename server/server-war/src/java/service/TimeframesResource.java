/*
 *  TimeframesResource
 *
 * Created on October 24, 2008, 9:56 PM
 *
 * To change this template, choose Tools | Template Manager
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
import persistence.Filter;
import converter.TimeframesConverter;
import converter.TimeframeConverter;
import persistence.Timeframes;


/**
 *
 * @author dave
 */

@Path("/timeframes/")
public class TimeframesResource {
    @Context
    private UriInfo context;
  
    /** Creates a new instance of TimeframesResource */
    public TimeframesResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public TimeframesResource(UriInfo context) {
        this.context = context;
    }

    /**
     * Get method for retrieving a collection of Timeframes instance in XML format.
     *
     * @return an instance of TimeframesConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public TimeframesConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max) {
        try {
            return new TimeframesConverter(getEntities(start, max), context.getAbsolutePath());
        } finally {
            
        }
    }

    /**
     * Post method for creating an instance of Timeframes using XML as the input format.
     *
     * @param data an TimeframeConverter entity that is deserialized from an XML stream
     * @return an instance of TimeframeConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(TimeframeConverter data) {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            Timeframes entity = data.getEntity();
            createEntity(entity);
            
            return Response.created(context.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            
        }
    }

    /**
     * Returns a dynamic instance of TimeframeResource used for entity navigation.
     *
     * @return an instance of TimeframeResource
     */
    @Path("{id}/")
    public TimeframeResource getTimeframeResource(@PathParam("id")
    String id) {
        return new TimeframeResource(id, context);
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Timeframes instances
     */
    protected Collection<Timeframes> getEntities(int start, int max) {
        return new PersistenceServiceBean().createQuery("SELECT e FROM Timeframes e").setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Timeframes entity) {
        new PersistenceServiceBean().persistEntity(entity);
        for (Filter value : entity.getFilterCollection()) {
            value.setTimeframeId(entity);
        }
    }
}
