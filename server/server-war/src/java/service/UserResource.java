/*
 *  UserResource
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
import persistence.Filter;
import converter.UserConverter;
import persistence.Users;


/**
 *
 * @author dave
 */

public class UserResource {
    private String id;
    private UriInfo context;
  
    /** Creates a new instance of UserResource */
    public UserResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public UserResource(String id, UriInfo context) {
        this.id = id;
        this.context = context;
    }

    /**
     * Get method for retrieving an instance of Users identified by id in XML format.
     *
     * @param id identifier for the entity
     * @return an instance of UserConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public UserConverter get() {
        try {
            return new UserConverter(getEntity(), context.getAbsolutePath());
        } finally {
            
        }
    }

    /**
     * Put method for updating an instance of Users identified by id using XML as the input format.
     *
     * @param id identifier for the entity
     * @param data an UserConverter entity that is deserialized from a XML stream
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void put(UserConverter data) {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            updateEntity(getEntity(), data.getEntity());
            
        } finally {
            
        }
    }

    /**
     * Delete method for deleting an instance of Users identified by id.
     *
     * @param id identifier for the entity
     */
    @DELETE
    public void delete() {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            Users entity = getEntity();
            persistenceSvc.removeEntity(entity);
            
        } finally {
            
        }
    }

    /**
     * Returns a dynamic instance of FiltersResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of FiltersResource
     */
    @Path("filters/")
    public FiltersResource getFiltersResource() {
        final Users parent = getEntity();
        return new FiltersResource(context) {

            @Override
            protected Collection<Filter> getEntities(int start, int max) {
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

            @Override
            protected void createEntity(Filter entity) {
                super.createEntity(entity);
                entity.setUserId(parent);
            }
        };
    }

    /**
     * Returns a dynamic instance of IntegrationsResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of IntegrationsResource
     */
    @Path("integrations/")
    public IntegrationsResource getIntegrationsResource() {
        final Users parent = getEntity();
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
                entity.setUserId(parent);
            }
        };
    }

    /**
     * Returns an instance of Users identified by id.
     *
     * @param id identifier for the entity
     * @return an instance of Users
     */
    protected Users getEntity() {
        try {
            return (Users) new PersistenceServiceBean().createQuery("SELECT e FROM Users e where e.id = :id").setParameter("id", id).getSingleResult();
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
    protected Users updateEntity(Users entity, Users newEntity) {
        newEntity.setId(entity.getId());
        entity.getFilterCollection().removeAll(newEntity.getFilterCollection());
        for (Filter value : entity.getFilterCollection()) {
            value.setUserId(null);
        }
        entity.getIntegrationsCollection().removeAll(newEntity.getIntegrationsCollection());
        for (Integrations value : entity.getIntegrationsCollection()) {
            value.setUserId(null);
        }
        entity = new PersistenceServiceBean().mergeEntity(newEntity);
        for (Filter value : entity.getFilterCollection()) {
            value.setUserId(entity);
        }
        for (Integrations value : entity.getIntegrationsCollection()) {
            value.setUserId(entity);
        }
        return entity;
    }
}
