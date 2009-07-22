/*
 *  Copyright (c) 2008 Boulder Community Foundation - iVolunteer
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.Collection;
import java.util.logging.Logger;
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
import persistence.Event;
import persistence.Location;
import converter.EventsConverter;
import converter.EventConverter;
import converter.list.EventListConverter;
import converter.consolidated.ConsolidatedConverter;
import javax.ws.rs.core.MultivaluedMap;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.util.logging.Level;
import saas.iplocationtools.ResponseConverter;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author Dave Angulo
 */
@Path("/events/")
public class EventsResource extends Base {

    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
    @Context
    HttpServletRequest hsr;
    Client client = Client.create();
    WebResource r = client.resource("http://ipinfodb.com");

    /** Creates a new instance of EventsResource */
    public EventsResource() {
    }

    /**
     * Get method for retrieving a collection of Event instance in XML format.
     *
     * @return an instance of ConsolidatedEventsConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public EventsConverter get(@QueryParam("start") @DefaultValue("0") int start,
            @QueryParam("max") @DefaultValue("10") int max,
            @QueryParam("expandLevel") @DefaultValue("1") int expandLevel,
            @QueryParam("query") @DefaultValue("SELECT e FROM Event e") String query) {
        return new EventsConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(),
                expandLevel);
    }

    /**
     * Post method for creating an instance of Event using XML as the input
     * format.
     *
     * @param data
     *            an EventConverter entity that is deserialized from an XML
     *            stream
     * @return an instance of EventConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(EventConverter data) {
        Event entity = data.getEntity();
        createEntity(entity);
        return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
    }

    /**
     * Returns a dynamic instance of EventResource used for entity navigation.
     *
     * @return an instance of EventResource
     */
    @Path("{id}/")
    public service.EventResource getEventResource(@PathParam("id") String id) {
        EventResource resource = resourceContext.getResource(EventResource.class);
        resource.setId(id);
        return resource;
    }

    @Path("list/")
    @GET
    @Produces({"application/json"})
    public EventListConverter list(@QueryParam("start") @DefaultValue("0") int start,
            @QueryParam("max") @DefaultValue("10") int max,
            @QueryParam("query") @DefaultValue("SELECT e FROM Event e") String query) {
        return new EventListConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(),
                uriInfo.getBaseUri());
    }

    @Path("consolidated/")
    @GET
    @Produces({"application/json", "application/xml"})
    public ConsolidatedConverter consolidated(@QueryParam("start") @DefaultValue("0") int start,
            @QueryParam("max") @DefaultValue("10") int max,
            @QueryParam("expandLevel") @DefaultValue("1") int expandLevel,
            @QueryParam("ip") String ip,
            @QueryParam("lat") String lat,
            @QueryParam("long") String lng,
            @QueryParam("street") String street,
            @QueryParam("city") String city,
            @QueryParam("state") String state,
            @QueryParam("zip") String zip,
            @QueryParam("country") String country,
            @QueryParam("radius") @DefaultValue("100") int radius) {

        if (lat == null || lng == null) {

            if (street != null || city != null || state != null || zip != null) {
                Location loc = new Location();
                loc.setStreet(street);
                loc.setCity(city);
                loc.setState(state);
                loc.setZip(zip);
                loc.setCountry(country);

                geo.encodeAddress(loc);
                lat = loc.getLatitude();
                lng = loc.getLongitude();
            } else {
                if (ip == null) {
                    ip = hsr.getRemoteAddr();
                }

                ResponseConverter resp = getIpGeo(ip);

                if (resp != null &
                        (resp.getLatitude() != null &&
                        resp.getLongitude() != null)) {
                    lat = resp.getLatitude();
                    lng = resp.getLongitude();
                }
            }
        }

        Logger.getLogger(EventsResource.class.getName()).log(Level.INFO, "Caller ip address " + hsr.getRemoteAddr() + " resolved to ( " + lat + ", " + lng + " )");

        return new ConsolidatedConverter(getEntitiesByLoc(start, max, lat, lng, radius), uriInfo.getAbsolutePath(),
                uriInfo.getBaseUri(), expandLevel, lat, lng);
    }

    @Consumes("application/json")
    protected ResponseConverter getIpGeo(String ip) {


        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("ip", ip);
        params.add("output", "json");

        ClientResponse cr = r.path("ip_query.php").queryParams(params).get(ClientResponse.class);
        cr.getHeaders().remove("Content-Type");
        cr.getHeaders().putSingle("Content-Type", "application/json");
        ResponseConverter response = cr.getEntity(ResponseConverter.class);

        return response;
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Event instances
     */
    @Override
    protected Collection<Event> getEntities(int start, int max, String query) {
        return (Collection<Event>) super.getEntities(start, max, query);
    }

    protected Collection<Event> getEntitiesByLoc(int start, int max, String lat, String lng, int radius) {
        return (Collection<Event>) persistenceFacade.findByLoc("Event.findNearLocation", start, max, lat, lng, radius);
    }
}
