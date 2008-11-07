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
import persistence.Filter;
import converter.IvUsersConverter;
import converter.IvUserConverter;
import persistence.IvUser;

/**
 *
 * @author dave
 */

@Path("/ivUsers/")
public class IvUsersResource {
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
  
    /** Creates a new instance of IvUsersResource */
    public IvUsersResource() {
    }

    /**
     * Get method for retrieving a collection of IvUser instance in XML format.
     *
     * @return an instance of IvUsersConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public IvUsersConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max, @QueryParam("expandLevel")
    @DefaultValue("1")
    int expandLevel, @QueryParam("query")
    @DefaultValue("SELECT e FROM IvUser e")
    String query) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            return new IvUsersConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
            persistenceSvc.commitTx();
            persistenceSvc.close();
        }
    }

    /**
     * Post method for creating an instance of IvUser using XML as the input format.
     *
     * @param data an IvUserConverter entity that is deserialized from an XML stream
     * @return an instance of IvUserConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(IvUserConverter data) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            EntityManager em = persistenceSvc.getEntityManager();
            IvUser entity = data.resolveEntity(em);
            createEntity(data.resolveEntity(em));
            persistenceSvc.commitTx();
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Returns a dynamic instance of IvUserResource used for entity navigation.
     *
     * @return an instance of IvUserResource
     */
    @Path("{id}/")
    public service.IvUserResource getIvUserResource(@PathParam("id")
    String id) {
        IvUserResource resource = resourceContext.getResource(IvUserResource.class);
        resource.setId(id);
        return resource;
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of IvUser instances
     */
    protected Collection<IvUser> getEntities(int start, int max, String query) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        return em.createQuery(query).setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(IvUser entity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        em.persist(entity);
        for (Filter value : entity.getFilterCollection()) {
            IvUser oldEntity = value.getUserId();
            value.setUserId(entity);
            if (oldEntity != null) {
                oldEntity.getFilterCollection().remove(entity);
            }
        }
        for (Integration value : entity.getIntegrationCollection()) {
            IvUser oldEntity = value.getUserId();
            value.setUserId(entity);
            if (oldEntity != null) {
                oldEntity.getIntegrationCollection().remove(entity);
            }
        }
    }
}
