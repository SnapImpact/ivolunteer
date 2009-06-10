package service;


import helpers.EmailSender;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;


import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author markchance
 */

@Path("attendEvent")
public class AttendEventResource {
    @Context
    private UriInfo context;

    /** Creates a new instance of AttendEventResource */
    public AttendEventResource() {
    }

    /**
     * Retrieves representation of an instance of service.AttendEventResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    public String get(@QueryParam("email") String email,
            @QueryParam("name") String name,
            @QueryParam("event") String eventId) throws WebApplicationException {
        EmailSender sender = new EmailSender();
        sender.sendAttendanceEmail(email, name, eventId);
        return "Message Sent";
    }
}
