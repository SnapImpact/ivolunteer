/*
 *  UsersResource
 *
 * Created on October 24, 2008, 9:56 PM
 *
 * To change this template, choose Tools | Template Manager
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
import persistence.Integrations;
import persistence.Filter;
import converter.UsersConverter;
import converter.UserConverter;
import persistence.Users;


/**
 *
 * @author dave
 */

@Path("/users/")
public class UsersResource {
    @Context
    private UriInfo context;
  
    /** Creates a new instance of UsersResource */
    public UsersResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public UsersResource(UriInfo context) {
        this.context = context;
    }

    /**
     * Get method for retrieving a collection of Users instance in XML format.
     *
     * @return an instance of UsersConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public UsersConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max) {
        try {
            return new UsersConverter(getEntities(start, max), context.getAbsolutePath());
        } finally {
            
        }
    }

    /**
     * Post method for creating an instance of Users using XML as the input format.
     *
     * @param data an UserConverter entity that is deserialized from an XML stream
     * @return an instance of UserConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(UserConverter data) {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            Users entity = data.getEntity();
            createEntity(entity);
            
            return Response.created(context.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            
        }
    }

    /**
     * Returns a dynamic instance of UserResource used for entity navigation.
     *
     * @return an instance of UserResource
     */
    @Path("{id}/")
    public UserResource getUserResource(@PathParam("id")
    String id) {
        return new UserResource(id, context);
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Users instances
     */
    protected Collection<Users> getEntities(int start, int max) {
        return new PersistenceServiceBean().createQuery("SELECT e FROM Users e").setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Users entity) {
        new PersistenceServiceBean().persistEntity(entity);
        for (Filter value : entity.getFilterCollection()) {
            value.setUserId(entity);
        }
        for (Integrations value : entity.getIntegrationsCollection()) {
            value.setUserId(entity);
        }
    }
}
