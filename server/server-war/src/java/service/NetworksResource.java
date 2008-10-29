/*
 *  NetworksResource
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
import converter.NetworksConverter;
import converter.NetworkConverter;
import persistence.Networks;


/**
 *
 * @author dave
 */

@Path("/networks/")
public class NetworksResource {
    @Context
    private UriInfo context;
  
    /** Creates a new instance of NetworksResource */
    public NetworksResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public NetworksResource(UriInfo context) {
        this.context = context;
    }

    /**
     * Get method for retrieving a collection of Networks instance in XML format.
     *
     * @return an instance of NetworksConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public NetworksConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max) {
        try {
            return new NetworksConverter(getEntities(start, max), context.getAbsolutePath());
        } finally {
            
        }
    }

    /**
     * Post method for creating an instance of Networks using XML as the input format.
     *
     * @param data an NetworkConverter entity that is deserialized from an XML stream
     * @return an instance of NetworkConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(NetworkConverter data) {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            Networks entity = data.getEntity();
            createEntity(entity);
            
            return Response.created(context.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            
        }
    }

    /**
     * Returns a dynamic instance of NetworkResource used for entity navigation.
     *
     * @return an instance of NetworkResource
     */
    @Path("{id}/")
    public NetworkResource getNetworkResource(@PathParam("id")
    String id) {
        return new NetworkResource(id, context);
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Networks instances
     */
    protected Collection<Networks> getEntities(int start, int max) {
        return new PersistenceServiceBean().createQuery("SELECT e FROM Networks e").setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Networks entity) {
        new PersistenceServiceBean().persistEntity(entity);
        for (Integrations value : entity.getIntegrationsCollection()) {
            value.setNetworkId(entity);
        }
    }
}
