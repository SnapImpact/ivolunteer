/*
 *  SourceResource
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
import persistence.Organizations;
import java.util.Collection;
import persistence.Events;
import converter.SourceConverter;
import persistence.Sources;


/**
 *
 * @author dave
 */

public class SourceResource {
    private String id;
    private UriInfo context;
  
    /** Creates a new instance of SourceResource */
    public SourceResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public SourceResource(String id, UriInfo context) {
        this.id = id;
        this.context = context;
    }

    /**
     * Get method for retrieving an instance of Sources identified by id in XML format.
     *
     * @param id identifier for the entity
     * @return an instance of SourceConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public SourceConverter get() {
        try {
            return new SourceConverter(getEntity(), context.getAbsolutePath());
        } finally {
            
        }
    }

    /**
     * Put method for updating an instance of Sources identified by id using XML as the input format.
     *
     * @param id identifier for the entity
     * @param data an SourceConverter entity that is deserialized from a XML stream
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void put(SourceConverter data) {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            updateEntity(getEntity(), data.getEntity());
            
        } finally {
            
        }
    }

    /**
     * Delete method for deleting an instance of Sources identified by id.
     *
     * @param id identifier for the entity
     */
    @DELETE
    public void delete() {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            Sources entity = getEntity();
            persistenceSvc.removeEntity(entity);
            
        } finally {
            
        }
    }

    /**
     * Returns a dynamic instance of OrganizationsResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of OrganizationsResource
     */
    @Path("organizations/")
    public OrganizationsResource getOrganizationsResource() {
        final Sources parent = getEntity();
        return new OrganizationsResource(context) {

            @Override
            protected Collection<Organizations> getEntities(int start, int max) {
                Collection<Organizations> result = new java.util.ArrayList<Organizations>();
                int index = 0;
                for (Organizations e : parent.getOrganizationsCollection()) {
                    if (index >= start && (index - start) < max) {
                        result.add(e);
                    }
                    index++;
                }
                return result;
            }

            @Override
            protected void createEntity(Organizations entity) {
                super.createEntity(entity);
                entity.setSourceId(parent);
            }
        };
    }

    /**
     * Returns a dynamic instance of EventsResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of EventsResource
     */
    @Path("events/")
    public EventsResource getEventsResource() {
        final Sources parent = getEntity();
        return new EventsResource(context) {

            @Override
            protected Collection<Events> getEntities(int start, int max) {
                Collection<Events> result = new java.util.ArrayList<Events>();
                int index = 0;
                for (Events e : parent.getEventsCollection()) {
                    if (index >= start && (index - start) < max) {
                        result.add(e);
                    }
                    index++;
                }
                return result;
            }

            @Override
            protected void createEntity(Events entity) {
                super.createEntity(entity);
                entity.setSourceId(parent);
            }
        };
    }

    /**
     * Returns an instance of Sources identified by id.
     *
     * @param id identifier for the entity
     * @return an instance of Sources
     */
    protected Sources getEntity() {
        try {
            return (Sources) new PersistenceServiceBean().createQuery("SELECT e FROM Sources e where e.id = :id").setParameter("id", id).getSingleResult();
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
    protected Sources updateEntity(Sources entity, Sources newEntity) {
        newEntity.setId(entity.getId());
        entity.getOrganizationsCollection().removeAll(newEntity.getOrganizationsCollection());
        for (Organizations value : entity.getOrganizationsCollection()) {
            value.setSourceId(null);
        }
        entity.getEventsCollection().removeAll(newEntity.getEventsCollection());
        for (Events value : entity.getEventsCollection()) {
            value.setSourceId(null);
        }
        entity = new PersistenceServiceBean().mergeEntity(newEntity);
        for (Organizations value : entity.getOrganizationsCollection()) {
            value.setSourceId(entity);
        }
        for (Events value : entity.getEventsCollection()) {
            value.setSourceId(entity);
        }
        return entity;
    }
}
