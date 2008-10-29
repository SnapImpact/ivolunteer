/*
 *  IntegrationResource
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
import javax.ws.rs.core.UriInfo;
import persistence.Integrations;
import persistence.Networks;
import persistence.Users;
import converter.IntegrationConverter;


/**
 *
 * @author dave
 */

public class IntegrationResource {
    private String id;
    private UriInfo context;
  
    /** Creates a new instance of IntegrationResource */
    public IntegrationResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public IntegrationResource(String id, UriInfo context) {
        this.id = id;
        this.context = context;
    }

    /**
     * Get method for retrieving an instance of Integrations identified by id in XML format.
     *
     * @param id identifier for the entity
     * @return an instance of IntegrationConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public IntegrationConverter get() {
        try {
            return new IntegrationConverter(getEntity(), context.getAbsolutePath());
        } finally {
            //
        }
    }

    /**
     * Put method for updating an instance of Integrations identified by id using XML as the input format.
     *
     * @param id identifier for the entity
     * @param data an IntegrationConverter entity that is deserialized from a XML stream
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void put(IntegrationConverter data) {
        try {
            
            updateEntity(getEntity(), data.getEntity());
            
        } finally {
            
        }
    }

    /**
     * Delete method for deleting an instance of Integrations identified by id.
     *
     * @param id identifier for the entity
     */
    @DELETE
    public void delete() {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            Integrations entity = getEntity();
            persistenceSvc.removeEntity(entity);
            
        } finally {
            
        }
    }

    /**
     * Returns a dynamic instance of NetworkResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of NetworkResource
     */
    @Path("network/")
    public NetworkResource getNetworkResource() {
        final Integrations parent = getEntity();
        return new NetworkResource(null, context) {

            @Override
            protected Networks getEntity() {
                Networks entity = parent.getNetworkId();
                if (entity == null) {
                    throw new WebApplicationException(new Throwable("Resource for " + context.getAbsolutePath() + " does not exist."), 404);
                }
                return entity;
            }
        };
    }

    /**
     * Returns a dynamic instance of UserResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of UserResource
     */
    @Path("user/")
    public UserResource getUserResource() {
        final Integrations parent = getEntity();
        return new UserResource(null, context) {

            @Override
            protected Users getEntity() {
                Users entity = parent.getUserId();
                if (entity == null) {
                    throw new WebApplicationException(new Throwable("Resource for " + context.getAbsolutePath() + " does not exist."), 404);
                }
                return entity;
            }
        };
    }

    /**
     * Returns an instance of Integrations identified by id.
     *
     * @param id identifier for the entity
     * @return an instance of Integrations
     */
    protected Integrations getEntity() {
        try {
            return (Integrations) new PersistenceServiceBean().createQuery("SELECT e FROM Integrations e where e.id = :id").setParameter("id", id).getSingleResult();
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
    protected Integrations updateEntity(Integrations entity, Integrations newEntity) {
        newEntity.setId(entity.getId());
        entity = new PersistenceServiceBean().mergeEntity(newEntity);
        return entity;
    }
}
