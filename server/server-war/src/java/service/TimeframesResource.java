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
import converter.TimeframesConverter;
import converter.TimeframeConverter;
import converter.TimeframeListConverter;
import persistence.Timeframe;

/**
 * 
 * @author dave
 */

@Path("/timeframes/")
public class TimeframesResource extends Base {
	@Context
	protected UriInfo			uriInfo;
	@Context
	protected ResourceContext	resourceContext;

	/** Creates a new instance of TimeframesResource */
	public TimeframesResource() {
	}

	/**
	 * Get method for retrieving a collection of Timeframe instance in XML
	 * format.
	 * 
	 * @return an instance of TimeframesConverter
	 */
	@GET
	@Produces( { "application/xml", "application/json" })
	public TimeframesConverter get(@QueryParam("start") @DefaultValue("0") int start,
			@QueryParam("max") @DefaultValue("10") int max,
			@QueryParam("expandLevel") @DefaultValue("1") int expandLevel,
			@QueryParam("query") @DefaultValue("SELECT e FROM Timeframe e") String query) {
		return new TimeframesConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(),
				expandLevel);
	}

	/**
	 * Post method for creating an instance of Timeframe using XML as the input
	 * format.
	 * 
	 * @param data
	 *            an TimeframeConverter entity that is deserialized from an XML
	 *            stream
	 * @return an instance of TimeframeConverter
	 */
	@POST
	@Consumes( { "application/xml", "application/json" })
	public Response post(TimeframeConverter data) {
		Timeframe entity = data.getEntity();
		createEntity(entity);
		return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
	}

	/**
	 * Returns a dynamic instance of TimeframeResource used for entity
	 * navigation.
	 * 
	 * @return an instance of TimeframeResource
	 */
	@Path("{id}/")
	public service.TimeframeResource getTimeframeResource(@PathParam("id") String id) {
		TimeframeResource resource = resourceContext.getResource(TimeframeResource.class);
		resource.setId(id);
		return resource;
	}

	@Path("list/")
	@GET
	@Produces( { "application/json" })
	public TimeframeListConverter list(@QueryParam("start") @DefaultValue("0") int start,
			@QueryParam("max") @DefaultValue("10") int max,
			@QueryParam("query") @DefaultValue("SELECT e FROM Timeframe e") String query) {
		return new TimeframeListConverter(getEntities(start, max, query),
				uriInfo.getAbsolutePath(), uriInfo.getBaseUri());
	}

	/**
	 * Returns all the entities associated with this resource.
	 * 
	 * @return a collection of Timeframe instances
	 */
	@Override
	protected Collection<Timeframe> getEntities(int start, int max, String query) {
		return (Collection<Timeframe>) super.getEntities(start, max, query);
	}
}
