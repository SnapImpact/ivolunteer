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
import javax.persistence.EntityManager;
import persistence.Source;
import persistence.InterestArea;
import converter.SourceInterestMapsConverter;
import converter.SourceInterestMapConverter;
import persistence.SourceInterestMap;

/**
 * 
 * @author dave
 */

@Path("/sourceInterestMaps/")
public class SourceInterestMapsResource extends Base {
	@Context
	protected UriInfo			uriInfo;
	@Context
	protected ResourceContext	resourceContext;

	/** Creates a new instance of SourceInterestMapsResource */
	public SourceInterestMapsResource() {
	}

	/**
	 * Get method for retrieving a collection of SourceInterestMap instance in
	 * XML format.
	 * 
	 * @return an instance of SourceInterestMapsConverter
	 */
	@GET
	@Produces( { "application/xml", "application/json" })
	public SourceInterestMapsConverter get(@QueryParam("start") @DefaultValue("0") int start,
			@QueryParam("max") @DefaultValue("10") int max,
			@QueryParam("expandLevel") @DefaultValue("1") int expandLevel,
			@QueryParam("query") @DefaultValue("SELECT e FROM SourceInterestMap e") String query) {
		return new SourceInterestMapsConverter(getEntities(start, max, query), uriInfo
				.getAbsolutePath(), expandLevel);
	}

	/**
	 * Post method for creating an instance of SourceInterestMap using XML as
	 * the input format.
	 * 
	 * @param data
	 *            an SourceInterestMapConverter entity that is deserialized from
	 *            an XML stream
	 * @return an instance of SourceInterestMapConverter
	 */
	@POST
	@Consumes( { "application/xml", "application/json" })
	public Response post(SourceInterestMapConverter data) {
		SourceInterestMap entity = data.getEntity();
		createEntity(entity);
		return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
	}

	/**
	 * Returns a dynamic instance of SourceInterestMapResource used for entity
	 * navigation.
	 * 
	 * @return an instance of SourceInterestMapResource
	 */
	@Path("{id}/")
	public service.SourceInterestMapResource getSourceInterestMapResource(@PathParam("id") String id) {
		SourceInterestMapResource resource = resourceContext
				.getResource(SourceInterestMapResource.class);
		resource.setId(id);
		return resource;
	}

	/**
	 * Returns all the entities associated with this resource.
	 * 
	 * @return a collection of SourceInterestMap instances
	 */
	@Override
	protected Collection<SourceInterestMap> getEntities(int start, int max, String query) {
		return (Collection<SourceInterestMap>) super.getEntities(start, max, query);
	}
}
