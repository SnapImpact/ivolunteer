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
import persistence.Location;
import persistence.Organization;
import converter.LocationsConverter;
import converter.LocationConverter;
import converter.LocationListConverter;
import session.LocationFacadeLocal;

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
        try {
            return new LocationsConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
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
        try {
            Location entity = data.getEntity();
            createEntity(entity);
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
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
        return lookupLocationFacade().findAll(start, max);
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Location entity) {
        lookupLocationFacade().create(entity);
    }

    @Path("list/")
    @GET
    @Produces({"application/json"})
    public LocationListConverter list(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max, @QueryParam("query")
    @DefaultValue("SELECT e FROM Event e")
    String query) {
        try {
            return new LocationListConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), uriInfo.getBaseUri());
        } finally {

        }
    }

    private LocationFacadeLocal lookupLocationFacade() {
        try {
            javax.naming.Context c = new InitialContext();
            return (LocationFacadeLocal) c.lookup("java:comp/env/LocationFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
