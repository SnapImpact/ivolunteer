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
import persistence.Integration;
import converter.NetworkConverter;
import persistence.Network;

/**
 *
 * @author dave
 */

public class NetworkResource {
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
    protected String id;
  
    /** Creates a new instance of NetworkResource */
    public NetworkResource() {
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get method for retrieving an instance of Network identified by id in XML format.
     *
     * @param id identifier for the entity
     * @return an instance of NetworkConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public NetworkConverter get(@QueryParam("expandLevel")
    @DefaultValue("1")
    int expandLevel) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            return new NetworkConverter(getEntity(), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
            PersistenceService.getInstance().close();
        }
    }

    /**
     * Put method for updating an instance of Network identified by id using XML as the input format.
     *
     * @param id identifier for the entity
     * @param data an NetworkConverter entity that is deserialized from a XML stream
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void put(NetworkConverter data) {
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
     * Delete method for deleting an instance of Network identified by id.
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
     * Returns an instance of Network identified by id.
     *
     * @param id identifier for the entity
     * @return an instance of Network
     */
    protected Network getEntity() {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        try {
            return (Network) em.createQuery("SELECT e FROM Network e where e.id = :id").setParameter("id", id).getSingleResult();
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
    protected Network updateEntity(Network entity, Network newEntity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        Collection<Integration> integrationCollection = entity.getIntegrationCollection();
        Collection<Integration> integrationCollectionNew = newEntity.getIntegrationCollection();
        entity = em.merge(newEntity);
        for (Integration value : integrationCollection) {
            if (!integrationCollectionNew.contains(value)) {
                throw new WebApplicationException(new Throwable("Cannot remove items from integrationCollection"));
            }
        }
        for (Integration value : integrationCollectionNew) {
            if (!integrationCollection.contains(value)) {
                Network oldEntity = value.getNetworkId();
                value.setNetworkId(entity);
                if (oldEntity != null && !oldEntity.equals(entity)) {
                    oldEntity.getIntegrationCollection().remove(value);
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
    protected void deleteEntity(Network entity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        if (!entity.getIntegrationCollection().isEmpty()) {
            throw new WebApplicationException(new Throwable("Cannot delete entity because integrationCollection is not empty."));
        }
        em.remove(entity);
    }

    /**
     * Returns a dynamic instance of IntegrationsResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of IntegrationsResource
     */
    @Path("integrationCollection/")
    public IntegrationsResource getIntegrationCollectionResource() {
        IntegrationCollectionResourceSub resource = resourceContext.getResource(IntegrationCollectionResourceSub.class);
        resource.setParent(getEntity());
        return resource;
    }

    public static class IntegrationCollectionResourceSub extends IntegrationsResource {

        private Network parent;

        public void setParent(Network parent) {
            this.parent = parent;
        }

        @Override
        protected Collection<Integration> getEntities(int start, int max, String query) {
            Collection<Integration> result = new java.util.ArrayList<Integration>();
            int index = 0;
            for (Integration e : parent.getIntegrationCollection()) {
                if (index >= start && (index - start) < max) {
                    result.add(e);
                }
                index++;
            }
            return result;
        }
    }
}
