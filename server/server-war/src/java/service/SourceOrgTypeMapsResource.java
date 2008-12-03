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
import converter.SourceOrgTypeMapsConverter;
import converter.SourceOrgTypeMapConverter;
import persistence.SourceOrgTypeMap;

/**
 *
 * @author dave
 */

@Path("/sourceOrgTypeMaps/")
public class SourceOrgTypeMapsResource extends Base {
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
  
    /** Creates a new instance of SourceOrgTypeMapsResource */
    public SourceOrgTypeMapsResource() {
    }

    /**
     * Get method for retrieving a collection of SourceOrgTypeMap instance in XML format.
     *
     * @return an instance of SourceOrgTypeMapsConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public SourceOrgTypeMapsConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max, @QueryParam("expandLevel")
    @DefaultValue("1")
    int expandLevel, @QueryParam("query")
    @DefaultValue("SELECT e FROM SourceOrgTypeMap e")
    String query) {
        return new SourceOrgTypeMapsConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
    }

    /**
     * Post method for creating an instance of SourceOrgTypeMap using XML as the input format.
     *
     * @param data an SourceOrgTypeMapConverter entity that is deserialized from an XML stream
     * @return an instance of SourceOrgTypeMapConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(SourceOrgTypeMapConverter data) {
            SourceOrgTypeMap entity = data.getEntity();
            createEntity(entity);
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
    }

    /**
     * Returns a dynamic instance of SourceOrgTypeMapResource used for entity navigation.
     *
     * @return an instance of SourceOrgTypeMapResource
     */
    @Path("{id}/")
    public service.SourceOrgTypeMapResource getSourceOrgTypeMapResource(@PathParam("id")
    String id) {
        SourceOrgTypeMapResource resource = resourceContext.getResource(SourceOrgTypeMapResource.class);
        resource.setId(id);
        return resource;
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of SourceOrgTypeMap instances
     */
    @Override
    protected Collection<SourceOrgTypeMap> getEntities(int start, int max, String query) {
        return (Collection<SourceOrgTypeMap>) super.getEntities(start, max, query);
    }
}
