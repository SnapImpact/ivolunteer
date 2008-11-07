/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import com.sun.jersey.api.core.ResourceContext;
import javax.ws.rs.WebApplicationException;
import javax.persistence.NoResultException;
import javax.persistence.EntityManager;
import java.util.Collection;
import persistence.Organization;
import persistence.Filter;
import persistence.OrganizationType;
import persistence.SourceOrgTypeMap;
import converter.OrganizationTypeConverter;

/**
 *
 * @author dave
 */

public class OrganizationTypeResource {
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
    protected String id;
  
    /** Creates a new instance of OrganizationTypeResource */
    public OrganizationTypeResource() {
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get method for retrieving an instance of OrganizationType identified by id in XML format.
     *
     * @param id identifier for the entity
     * @return an instance of OrganizationTypeConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public OrganizationTypeConverter get(@QueryParam("expandLevel")
    @DefaultValue("1")
    int expandLevel) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            return new OrganizationTypeConverter(getEntity(), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
            PersistenceService.getInstance().close();
        }
    }

    /**
     * Put method for updating an instance of OrganizationType identified by id using XML as the input format.
     *
     * @param id identifier for the entity
     * @param data an OrganizationTypeConverter entity that is deserialized from a XML stream
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void put(OrganizationTypeConverter data) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            EntityManager em = persistenceSvc.getEntityManager();
            updateEntity(getEntity(), data.resolveEntity(em));
            persistenceSvc.commitTx();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Delete method for deleting an instance of OrganizationType identified by id.
     *
     * @param id identifier for the entity
     */
    @DELETE
    public void delete() {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            deleteEntity(getEntity());
            persistenceSvc.commitTx();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Returns an instance of OrganizationType identified by id.
     *
     * @param id identifier for the entity
     * @return an instance of OrganizationType
     */
    protected OrganizationType getEntity() {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        try {
            return (OrganizationType) em.createQuery("SELECT e FROM OrganizationType e where e.id = :id").setParameter("id", id).getSingleResult();
        } catch (NoResultException ex) {
            throw new WebApplicationException(new Throwable("Resource for " + uriInfo.getAbsolutePath() + " does not exist."), 404);
        }
    }

    /**
     * Updates entity using data from newEntity.
     *
     * @param entity the entity to update
     * @param newEntity the entity containing the new data
     * @return the updated entity
     */
    protected OrganizationType updateEntity(OrganizationType entity, OrganizationType newEntity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        Collection<Filter> filterCollection = entity.getFilterCollection();
        Collection<Filter> filterCollectionNew = newEntity.getFilterCollection();
        Collection<Organization> organizationCollection = entity.getOrganizationCollection();
        Collection<Organization> organizationCollectionNew = newEntity.getOrganizationCollection();
        Collection<SourceOrgTypeMap> sourceOrgTypeMapCollection = entity.getSourceOrgTypeMapCollection();
        Collection<SourceOrgTypeMap> sourceOrgTypeMapCollectionNew = newEntity.getSourceOrgTypeMapCollection();
        entity = em.merge(newEntity);
        for (Filter value : filterCollection) {
            if (!filterCollectionNew.contains(value)) {
                value.getOrganizationTypeCollection().remove(entity);
            }
        }
        for (Filter value : filterCollectionNew) {
            if (!filterCollection.contains(value)) {
                value.getOrganizationTypeCollection().add(entity);
            }
        }
        for (Organization value : organizationCollection) {
            if (!organizationCollectionNew.contains(value)) {
                throw new WebApplicationException(new Throwable("Cannot remove items from organizationCollection"));
            }
        }
        for (Organization value : organizationCollectionNew) {
            if (!organizationCollection.contains(value)) {
                OrganizationType oldEntity = value.getOrganizationTypeId();
                value.setOrganizationTypeId(entity);
                if (oldEntity != null && !oldEntity.equals(entity)) {
                    oldEntity.getOrganizationCollection().remove(value);
                }
            }
        }
        for (SourceOrgTypeMap value : sourceOrgTypeMapCollection) {
            if (!sourceOrgTypeMapCollectionNew.contains(value)) {
                throw new WebApplicationException(new Throwable("Cannot remove items from sourceOrgTypeMapCollection"));
            }
        }
        for (SourceOrgTypeMap value : sourceOrgTypeMapCollectionNew) {
            if (!sourceOrgTypeMapCollection.contains(value)) {
                OrganizationType oldEntity = value.getOrganizationTypeId();
                value.setOrganizationTypeId(entity);
                if (oldEntity != null && !oldEntity.equals(entity)) {
                    oldEntity.getSourceOrgTypeMapCollection().remove(value);
                }
            }
        }
        return entity;
    }

    /**
     * Deletes the entity.
     *
     * @param entity the entity to deletle
     */
    protected void deleteEntity(OrganizationType entity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        for (Filter value : entity.getFilterCollection()) {
            value.getOrganizationTypeCollection().remove(entity);
        }
        if (!entity.getOrganizationCollection().isEmpty()) {
            throw new WebApplicationException(new Throwable("Cannot delete entity because organizationCollection is not empty."));
        }
        if (!entity.getSourceOrgTypeMapCollection().isEmpty()) {
            throw new WebApplicationException(new Throwable("Cannot delete entity because sourceOrgTypeMapCollection is not empty."));
        }
        em.remove(entity);
    }

    /**
     * Returns a dynamic instance of FiltersResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of FiltersResource
     */
    @Path("filterCollection/")
    public FiltersResource getFilterCollectionResource() {
        FilterCollectionResourceSub resource = resourceContext.getResource(FilterCollectionResourceSub.class);
        resource.setParent(getEntity());
        return resource;
    }

    /**
     * Returns a dynamic instance of OrganizationsResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of OrganizationsResource
     */
    @Path("organizationCollection/")
    public OrganizationsResource getOrganizationCollectionResource() {
        OrganizationCollectionResourceSub resource = resourceContext.getResource(OrganizationCollectionResourceSub.class);
        resource.setParent(getEntity());
        return resource;
    }

    /**
     * Returns a dynamic instance of SourceOrgTypeMapsResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of SourceOrgTypeMapsResource
     */
    @Path("sourceOrgTypeMapCollection/")
    public SourceOrgTypeMapsResource getSourceOrgTypeMapCollectionResource() {
        SourceOrgTypeMapCollectionResourceSub resource = resourceContext.getResource(SourceOrgTypeMapCollectionResourceSub.class);
        resource.setParent(getEntity());
        return resource;
    }

    public static class FilterCollectionResourceSub extends FiltersResource {

        private OrganizationType parent;

        public void setParent(OrganizationType parent) {
            this.parent = parent;
        }

        @Override
        protected Collection<Filter> getEntities(int start, int max, String query) {
            Collection<Filter> result = new java.util.ArrayList<Filter>();
            int index = 0;
            for (Filter e : parent.getFilterCollection()) {
                if (index >= start && (index - start) < max) {
                    result.add(e);
                }
                index++;
            }
            return result;
        }
    }

    public static class OrganizationCollectionResourceSub extends OrganizationsResource {

        private OrganizationType parent;

        public void setParent(OrganizationType parent) {
            this.parent = parent;
        }

        @Override
        protected Collection<Organization> getEntities(int start, int max, String query) {
            Collection<Organization> result = new java.util.ArrayList<Organization>();
            int index = 0;
            for (Organization e : parent.getOrganizationCollection()) {
                if (index >= start && (index - start) < max) {
                    result.add(e);
                }
                index++;
            }
            return result;
        }
    }

    public static class SourceOrgTypeMapCollectionResourceSub extends SourceOrgTypeMapsResource {

        private OrganizationType parent;

        public void setParent(OrganizationType parent) {
            this.parent = parent;
        }

        @Override
        protected Collection<SourceOrgTypeMap> getEntities(int start, int max, String query) {
            Collection<SourceOrgTypeMap> result = new java.util.ArrayList<SourceOrgTypeMap>();
            int index = 0;
            for (SourceOrgTypeMap e : parent.getSourceOrgTypeMapCollection()) {
                if (index >= start && (index - start) < max) {
                    result.add(e);
                }
                index++;
            }
            return result;
        }
    }
}
