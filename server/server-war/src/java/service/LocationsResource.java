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
import persistence.Location;
import converter.LocationsConverter;
import converter.LocationConverter;
import converter.LocationListConverter;

/**
 *
 * @author dave
 */

@Path("/locations/")
public class LocationsResource extends Base {
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
        return new LocationsConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
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
        Location entity = data.getEntity();
            createEntity(entity);
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
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
            return new LocationListConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), uriInfo.getBaseUri());
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Location instances
     */
    @Override
    protected Collection<Location> getEntities(int start, int max, String query) {
        return (Collection<Location>) super.getEntities(start, max, query);
    }



}
