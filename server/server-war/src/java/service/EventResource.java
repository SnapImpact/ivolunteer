/*
 *  EventResource
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
import persistence.Events;
import persistence.Organizations;
import java.util.Collection;
import persistence.Sources;
import persistence.InterestAreas;
import converter.EventConverter;


/**
 *
 * @author dave
 */

public class EventResource {
    private String id;
    private UriInfo context;
  
    /** Creates a new instance of EventResource */
    public EventResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public EventResource(String id, UriInfo context) {
        this.id = id;
        this.context = context;
    }

    /**
     * Get method for retrieving an instance of Events identified by id in XML format.
     *
     * @param id identifier for the entity
     * @return an instance of EventConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public EventConverter get() {
        try {
            return new EventConverter(getEntity(), context.getAbsolutePath());
        } finally {
            
        }
    }

    /**
     * Put method for updating an instance of Events identified by id using XML as the input format.
     *
     * @param id identifier for the entity
     * @param data an EventConverter entity that is deserialized from a XML stream
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void put(EventConverter data) {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            updateEntity(getEntity(), data.getEntity());
            
        } finally {
            
        }
    }

    /**
     * Delete method for deleting an instance of Events identified by id.
     *
     * @param id identifier for the entity
     */
    @DELETE
    public void delete() {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            Events entity = getEntity();
            persistenceSvc.removeEntity(entity);
            
        } finally {
            
        }
    }

    /**
     * Returns a dynamic instance of InterestAreasResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of InterestAreasResource
     */
    @Path("interestAreas/")
    public InterestAreasResource getInterestAreasResource() {
        final Events parent = getEntity();
        return new InterestAreasResource(context) {

            @Override
            protected Collection<InterestAreas> getEntities(int start, int max) {
                Collection<InterestAreas> result = new java.util.ArrayList<InterestAreas>();
                int index = 0;
                for (InterestAreas e : parent.getInterestAreaIdCollection()) {
                    if (index >= start && (index - start) < max) {
                        result.add(e);
                    }
                    index++;
                }
                return result;
            }

            @Override
            protected void createEntity(InterestAreas entity) {
                super.createEntity(entity);
                if (!entity.getEventIdCollection().contains(parent)) {
                    entity.getEventIdCollection().add(parent);
                }
            }
        };
    }

    /**
     * Returns a dynamic instance of OrganizationResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of OrganizationResource
     */
    @Path("organization/")
    public OrganizationResource getOrganizationResource() {
        final Events parent = getEntity();
        return new OrganizationResource(null, context) {

            @Override
            protected Organizations getEntity() {
                Organizations entity = parent.getOrganizationId();
                if (entity == null) {
                    throw new WebApplicationException(new Throwable("Resource for " + context.getAbsolutePath() + " does not exist."), 404);
                }
                return entity;
            }
        };
    }

    /**
     * Returns a dynamic instance of SourceResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of SourceResource
     */
    @Path("source/")
    public SourceResource getSourceResource() {
        final Events parent = getEntity();
        return new SourceResource(null, context) {

            @Override
            protected Sources getEntity() {
                Sources entity = parent.getSourceId();
                if (entity == null) {
                    throw new WebApplicationException(new Throwable("Resource for " + context.getAbsolutePath() + " does not exist."), 404);
                }
                return entity;
            }
        };
    }

    /**
     * Returns an instance of Events identified by id.
     *
     * @param id identifier for the entity
     * @return an instance of Events
     */
    protected Events getEntity() {
        try {
            return (Events) new PersistenceServiceBean().createQuery("SELECT e FROM Events e where e.id = :id").setParameter("id", id).getSingleResult();
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
    protected Events updateEntity(Events entity, Events newEntity) {
        newEntity.setId(entity.getId());
        entity = new PersistenceServiceBean().mergeEntity(newEntity);
        return entity;
    }
}
