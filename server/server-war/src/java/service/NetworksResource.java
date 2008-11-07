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
import persistence.Integration;
import converter.NetworksConverter;
import converter.NetworkConverter;
import persistence.Network;

/**
 *
 * @author dave
 */

@Path("/networks/")
public class NetworksResource {
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
  
    /** Creates a new instance of NetworksResource */
    public NetworksResource() {
    }

    /**
     * Get method for retrieving a collection of Network instance in XML format.
     *
     * @return an instance of NetworksConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public NetworksConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max, @QueryParam("expandLevel")
    @DefaultValue("1")
    int expandLevel, @QueryParam("query")
    @DefaultValue("SELECT e FROM Network e")
    String query) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            return new NetworksConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
            persistenceSvc.commitTx();
            persistenceSvc.close();
        }
    }

    /**
     * Post method for creating an instance of Network using XML as the input format.
     *
     * @param data an NetworkConverter entity that is deserialized from an XML stream
     * @return an instance of NetworkConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(NetworkConverter data) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            EntityManager em = persistenceSvc.getEntityManager();
            Network entity = data.resolveEntity(em);
            createEntity(data.resolveEntity(em));
            persistenceSvc.commitTx();
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Returns a dynamic instance of NetworkResource used for entity navigation.
     *
     * @return an instance of NetworkResource
     */
    @Path("{id}/")
    public service.NetworkResource getNetworkResource(@PathParam("id")
    String id) {
        NetworkResource resource = resourceContext.getResource(NetworkResource.class);
        resource.setId(id);
        return resource;
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Network instances
     */
    protected Collection<Network> getEntities(int start, int max, String query) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        return em.createQuery(query).setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Network entity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        em.persist(entity);
        for (Integration value : entity.getIntegrationCollection()) {
            Network oldEntity = value.getNetworkId();
            value.setNetworkId(entity);
            if (oldEntity != null) {
                oldEntity.getIntegrationCollection().remove(entity);
            }
        }
    }
}
