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
import persistence.Distance;
import converter.DistancesConverter;
import converter.DistanceConverter;
import converter.DistanceListConverter;

/**
 *
 * @author dave
 */

@Path("/distances/")
public class DistancesResource extends Base {
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
  
    /** Creates a new instance of DistancesResource */
    public DistancesResource() {
    }

    /**
     * Get method for retrieving a collection of Distance instance in XML format.
     *
     * @return an instance of DistancesConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public DistancesConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max, @QueryParam("expandLevel")
    @DefaultValue("1")
    int expandLevel, @QueryParam("query")
    @DefaultValue("SELECT e FROM Distance e")
    String query) {
        return new DistancesConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
    }

    /**
     * Post method for creating an instance of Distance using XML as the input format.
     *
     * @param data an DistanceConverter entity that is deserialized from an XML stream
     * @return an instance of DistanceConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(DistanceConverter data) {
            Distance entity = data.getEntity();
            createEntity(entity);
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
    }

    /**
     * Returns a dynamic instance of DistanceResource used for entity navigation.
     *
     * @return an instance of DistanceResource
     */
    @Path("{id}/")
    public service.DistanceResource getDistanceResource(@PathParam("id")
    String id) {
        DistanceResource resource = resourceContext.getResource(DistanceResource.class);
        resource.setId(id);
        return resource;
    }

    @Path("list/")
    @GET
    @Produces({"application/json"})
    public DistanceListConverter list(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max, @QueryParam("query")
    @DefaultValue("SELECT e FROM Distance e")
    String query) {
        return new DistanceListConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), uriInfo.getBaseUri());
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Distance instances
     */
    @Override
    protected Collection<Distance> getEntities(int start, int max, String query) {
        return (Collection<Distance>) super.getEntities(start, max, query);
    }
}
