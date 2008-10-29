/*
 *  OrganizationsResource
 *
 * Created on October 24, 2008, 9:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package service;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
import persistence.Events;
import converter.OrganizationsConverter;
import converter.OrganizationsListConverter;
import converter.OrganizationConverter;
import persistence.Organizations;
import session.OrganizationsFacadeLocal;


/**
 *
 * @author dave
 */

@Path("/organizations/")
public class OrganizationsResource {
    @Context
    private UriInfo context;

    private OrganizationsFacadeLocal organizationsFacade = lookupOrganizationsFacade();
  
    /** Creates a new instance of OrganizationsResource */
    public OrganizationsResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public OrganizationsResource(UriInfo context) {
        this.context = context;
    }

    /**
     * Get method for retrieving a collection of Organizations instance in XML format.
     *
     * @return an instance of OrganizationsConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public OrganizationsConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max) {
        try {
            return new OrganizationsConverter(getEntities(start, max), context.getAbsolutePath());
        } finally {
            
        }
    }

    /**
     * Post method for creating an instance of Organizations using XML as the input format.
     *
     * @param data an OrganizationConverter entity that is deserialized from an XML stream
     * @return an instance of OrganizationConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(OrganizationConverter data) {
        try {
            
            Organizations entity = data.getEntity();
            createEntity(entity);
            
            return Response.created(context.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            
        }
    }

    /**
     * Returns a dynamic instance of OrganizationResource used for entity navigation.
     *
     * @return an instance of OrganizationResource
     */
    @Path("{id}/")
    public OrganizationResource getOrganizationResource(@PathParam("id")
    String id) {
        return new OrganizationResource(id, context);
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Organizations instances
     */
    protected Collection<Organizations> getEntities(int start, int max) {
        return organizationsFacade.findAll(start,max);
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Organizations entity) {
        organizationsFacade.create(entity);
        for (Events value : entity.getEventsCollection()) {
            value.setOrganizationId(entity);
        }
    }
    
    @Path("list/")
    @GET
    @Produces({"application/json"})
    public OrganizationsListConverter list(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max) {
        try {
            return new OrganizationsListConverter(getEntities(start, max), context.getAbsolutePath(), context.getBaseUri());
        } finally {
            
        }
    }

    private OrganizationsFacadeLocal lookupOrganizationsFacade() {
        try {
            javax.naming.Context c = new InitialContext();
            return (OrganizationsFacadeLocal) c.lookup("java:comp/env/OrganizationsFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
