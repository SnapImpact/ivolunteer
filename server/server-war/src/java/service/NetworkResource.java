/*
 *  NetworkResource
 *
 * Created on October 24, 2008, 9:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package service;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.persistence.NoResultException;
import java.util.Collection;
import javax.ws.rs.core.UriInfo;
import persistence.Integrations;
import converter.NetworkConverter;
import persistence.Networks;


/**
 *
 * @author dave
 */

public class NetworkResource {
    private String id;
    private UriInfo context;
  
    /** Creates a new instance of NetworkResource */
    public NetworkResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public NetworkResource(String id, UriInfo context) {
        this.id = id;
        this.context = context;
    }

    /**
     * Get method for retrieving an instance of Networks identified by id in XML format.
     *
     * @param id identifier for the entity
     * @return an instance of NetworkConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public NetworkConverter get() {
        try {
            return new NetworkConverter(getEntity(), context.getAbsolutePath());
        } finally {
            
        }
    }

    /**
     * Put method for updating an instance of Networks identified by id using XML as the input format.
     *
     * @param id identifier for the entity
     * @param data an NetworkConverter entity that is deserialized from a XML stream
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void put(NetworkConverter data) {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            updateEntity(getEntity(), data.getEntity());
            
        } finally {
            
        }
    }

    /**
     * Delete method for deleting an instance of Networks identified by id.
     *
     * @param id identifier for the entity
     */
    @DELETE
    public void delete() {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            Networks entity = getEntity();
            persistenceSvc.removeEntity(entity);
            
        } finally {
            
        }
    }

    /**
     * Returns a dynamic instance of IntegrationsResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of IntegrationsResource
     */
    @Path("integrations/")
    public IntegrationsResource getIntegrationsResource() {
        final Networks parent = getEntity();
        return new IntegrationsResource(context) {

            @Override
            protected Collection<Integrations> getEntities(int start, int max) {
                Collection<Integrations> result = new java.util.ArrayList<Integrations>();
                int index = 0;
                for (Integrations e : parent.getIntegrationsCollection()) {
                    if (index >= start && (index - start) < max) {
                        result.add(e);
                    }
                    index++;
                }
                return result;
            }

            @Override
            protected void createEntity(Integrations entity) {
                super.createEntity(entity);
                entity.setNetworkId(parent);
            }
        };
    }

    /**
     * Returns an instance of Networks identified by id.
     *
     * @param id identifier for the entity
     * @return an instance of Networks
     */
    protected Networks getEntity() {
        try {
            return (Networks) new PersistenceServiceBean().createQuery("SELECT e FROM Networks e where e.id = :id").setParameter("id", id).getSingleResult();
        } catch (NoResultException ex) {
            throw new WebApplicationException(new Throwable("Resource for " + context.getAbsolutePath() + " does not exist."), 404);
        }
    }

    /**
     * Updates entity using data from newEntity.
     *
     * @param entity the entity to update
     * @param newEntity the entity containing the new data
     * @return the updated entity
     */
    protected Networks updateEntity(Networks entity, Networks newEntity) {
        newEntity.setId(entity.getId());
        entity.getIntegrationsCollection().removeAll(newEntity.getIntegrationsCollection());
        for (Integrations value : entity.getIntegrationsCollection()) {
            value.setNetworkId(null);
        }
        entity = new PersistenceServiceBean().mergeEntity(newEntity);
        for (Integrations value : entity.getIntegrationsCollection()) {
            value.setNetworkId(entity);
        }
        return entity;
    }
}
