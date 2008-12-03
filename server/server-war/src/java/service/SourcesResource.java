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
import persistence.Source;
import converter.SourcesConverter;
import converter.SourceConverter;

/**
 * 
 * @author dave
 */

@Path("/sources/")
public class SourcesResource extends Base {
	@Context
	protected UriInfo			uriInfo;
	@Context
	protected ResourceContext	resourceContext;

	/** Creates a new instance of SourcesResource */
	public SourcesResource() {
	}

	/**
	 * Get method for retrieving a collection of Source instance in XML format.
	 * 
	 * @return an instance of SourcesConverter
	 */
	@GET
	@Produces( { "application/xml", "application/json" })
	public SourcesConverter get(@QueryParam("start") @DefaultValue("0") int start,
			@QueryParam("max") @DefaultValue("10") int max,
			@QueryParam("expandLevel") @DefaultValue("1") int expandLevel,
			@QueryParam("query") @DefaultValue("SELECT e FROM Source e") String query) {
		return new SourcesConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(),
				expandLevel);
	}

	/**
	 * Post method for creating an instance of Source using XML as the input
	 * format.
	 * 
	 * @param data
	 *            an SourceConverter entity that is deserialized from an XML
	 *            stream
	 * @return an instance of SourceConverter
	 */
	@POST
	@Consumes( { "application/xml", "application/json" })
	public Response post(SourceConverter data) {
		Source entity = data.getEntity();
		createEntity(entity);
		return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
	}

	/**
	 * Returns a dynamic instance of SourceResource used for entity navigation.
	 * 
	 * @return an instance of SourceResource
	 */
	@Path("{id}/")
	public service.SourceResource getSourceResource(@PathParam("id") String id) {
		SourceResource resource = resourceContext.getResource(SourceResource.class);
		resource.setId(id);
		return resource;
	}

	/**
	 * Returns all the entities associated with this resource.
	 * 
	 * @return a collection of Source instances
	 */
	@Override
	protected Collection<Source> getEntities(int start, int max, String query) {
		return (Collection<Source>) super.getEntities(start, max, query);
	}
}
