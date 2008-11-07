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
import persistence.Organization;
import persistence.OrganizationType;
import persistence.SourceOrgTypeMap;
import persistence.Filter;
import converter.OrganizationTypesConverter;
import converter.OrganizationTypeConverter;

/**
 *
 * @author dave
 */

@Path("/organizationTypes/")
public class OrganizationTypesResource {
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
  
    /** Creates a new instance of OrganizationTypesResource */
    public OrganizationTypesResource() {
    }

    /**
     * Get method for retrieving a collection of OrganizationType instance in XML format.
     *
     * @return an instance of OrganizationTypesConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public OrganizationTypesConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max, @QueryParam("expandLevel")
    @DefaultValue("1")
    int expandLevel, @QueryParam("query")
    @DefaultValue("SELECT e FROM OrganizationType e")
    String query) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            return new OrganizationTypesConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
            persistenceSvc.commitTx();
            persistenceSvc.close();
        }
    }

    /**
     * Post method for creating an instance of OrganizationType using XML as the input format.
     *
     * @param data an OrganizationTypeConverter entity that is deserialized from an XML stream
     * @return an instance of OrganizationTypeConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(OrganizationTypeConverter data) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            EntityManager em = persistenceSvc.getEntityManager();
            OrganizationType entity = data.resolveEntity(em);
            createEntity(data.resolveEntity(em));
            persistenceSvc.commitTx();
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Returns a dynamic instance of OrganizationTypeResource used for entity navigation.
     *
     * @return an instance of OrganizationTypeResource
     */
    @Path("{id}/")
    public service.OrganizationTypeResource getOrganizationTypeResource(@PathParam("id")
    String id) {
        OrganizationTypeResource resource = resourceContext.getResource(OrganizationTypeResource.class);
        resource.setId(id);
        return resource;
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of OrganizationType instances
     */
    protected Collection<OrganizationType> getEntities(int start, int max, String query) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        return em.createQuery(query).setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(OrganizationType entity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        em.persist(entity);
        for (Filter value : entity.getFilterCollection()) {
            value.getOrganizationTypeCollection().add(entity);
        }
        for (Organization value : entity.getOrganizationCollection()) {
            OrganizationType oldEntity = value.getOrganizationTypeId();
            value.setOrganizationTypeId(entity);
            if (oldEntity != null) {
                oldEntity.getOrganizationCollection().remove(entity);
            }
        }
        for (SourceOrgTypeMap value : entity.getSourceOrgTypeMapCollection()) {
            OrganizationType oldEntity = value.getOrganizationTypeId();
            value.setOrganizationTypeId(entity);
            if (oldEntity != null) {
                oldEntity.getSourceOrgTypeMapCollection().remove(entity);
            }
        }
    }
}
