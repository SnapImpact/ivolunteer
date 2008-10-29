/*
 *  IntegrationsResource
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
import converter.IntegrationsConverter;
import converter.IntegrationConverter;
import persistence.Integrations;


/**
 *
 * @author dave
 */

@Path("/integrations/")
public class IntegrationsResource {
    @Context
    private UriInfo context;
  
    /** Creates a new instance of IntegrationsResource */
    public IntegrationsResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public IntegrationsResource(UriInfo context) {
        this.context = context;
    }

    /**
     * Get method for retrieving a collection of Integrations instance in XML format.
     *
     * @return an instance of IntegrationsConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public IntegrationsConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max) {
        try {
            return new IntegrationsConverter(getEntities(start, max), context.getAbsolutePath());
        } finally {
            
        }
    }

    /**
     * Post method for creating an instance of Integrations using XML as the input format.
     *
     * @param data an IntegrationConverter entity that is deserialized from an XML stream
     * @return an instance of IntegrationConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(IntegrationConverter data) {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            Integrations entity = data.getEntity();
            createEntity(entity);
            
            return Response.created(context.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            
        }
    }

    /**
     * Returns a dynamic instance of IntegrationResource used for entity navigation.
     *
     * @return an instance of IntegrationResource
     */
    @Path("{id}/")
    public IntegrationResource getIntegrationResource(@PathParam("id")
    String id) {
        return new IntegrationResource(id, context);
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Integrations instances
     */
    protected Collection<Integrations> getEntities(int start, int max) {
        return new PersistenceServiceBean().createQuery("SELECT e FROM Integrations e").setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Integrations entity) {
        new PersistenceServiceBean().persistEntity(entity);
    }
}
