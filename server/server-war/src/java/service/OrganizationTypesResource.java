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
import persistence.OrganizationType;
import converter.OrganizationTypesConverter;
import converter.OrganizationTypeConverter;
import converter.OrganizationTypeListConverter;

/**
 *
 * @author dave
 */

@Path("/organizationTypes/")
public class OrganizationTypesResource extends Base {
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
  
    /** Creates a new instance of OrganizationTypesResource */
    public OrganizationTypesResource() {
    }

    /**
     * Get method for retrieving a collection of OrganizationType instance in XML format.
     *
     * @return an instance of OrganizationTypesConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public OrganizationTypesConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max, @QueryParam("expandLevel")
    @DefaultValue("1")
    int expandLevel, @QueryParam("query")
    @DefaultValue("SELECT e FROM OrganizationType e")
    String query) {
            return new OrganizationTypesConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
    }

    /**
     * Post method for creating an instance of OrganizationType using XML as the input format.
     *
     * @param data an OrganizationTypeConverter entity that is deserialized from an XML stream
     * @return an instance of OrganizationTypeConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(OrganizationTypeConverter data) {
            OrganizationType entity = data.getEntity();
            createEntity(entity);
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
    }

    /**
     * Returns a dynamic instance of OrganizationTypeResource used for entity navigation.
     *
     * @return an instance of OrganizationTypeResource
     */
    @Path("{id}/")
    public service.OrganizationTypeResource getOrganizationTypeResource(@PathParam("id")
    String id) {
        OrganizationTypeResource resource = resourceContext.getResource(OrganizationTypeResource.class);
        resource.setId(id);
        return resource;
    }

    @Path("list/")
    @GET
    @Produces({"application/json"})
    public OrganizationTypeListConverter list(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max, @QueryParam("query")
    @DefaultValue("SELECT e FROM OrganizationType e")
    String query) {
            return new OrganizationTypeListConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), uriInfo.getBaseUri());
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of OrganizationType instances
     */
    @Override
    protected Collection<OrganizationType> getEntities(int start, int max, String query) {
        return (Collection<OrganizationType>) super.getEntities(start, max, query);
    }
}
