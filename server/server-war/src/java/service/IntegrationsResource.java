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
import persistence.Integration;
import converter.IntegrationsConverter;
import converter.IntegrationConverter;

/**
 *
 * @author dave
 */

@Path("/integrations/")
public class IntegrationsResource extends Base {
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
  
    /** Creates a new instance of IntegrationsResource */
    public IntegrationsResource() {
    }

    /**
     * Get method for retrieving a collection of Integration instance in XML format.
     *
     * @return an instance of IntegrationsConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public IntegrationsConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max, @QueryParam("expandLevel")
    @DefaultValue("1")
    int expandLevel, @QueryParam("query")
    @DefaultValue("SELECT e FROM Integration e")
    String query) {
           return new IntegrationsConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
    }

    /**
     * Post method for creating an instance of Integration using XML as the input format.
     *
     * @param data an IntegrationConverter entity that is deserialized from an XML stream
     * @return an instance of IntegrationConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(IntegrationConverter data) {
            Integration entity = data.getEntity();
            createEntity(entity);
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
    }

    /**
     * Returns a dynamic instance of IntegrationResource used for entity navigation.
     *
     * @return an instance of IntegrationResource
     */
    @Path("{id}/")
    public service.IntegrationResource getIntegrationResource(@PathParam("id")
    String id) {
        IntegrationResource resource = resourceContext.getResource(IntegrationResource.class);
        resource.setId(id);
        return resource;
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Integration instances
     */
    protected Collection<Integration> getEntities(int start, int max, String query) {
        return (Collection<Integration>) super.getEntities(start, max, query);
    }
}
