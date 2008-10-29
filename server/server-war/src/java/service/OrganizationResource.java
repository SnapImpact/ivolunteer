/*
 *  OrganizationResource
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
import persistence.OrganizationTypes;
import java.util.Collection;
import persistence.Events;
import persistence.Organizations;
import persistence.Sources;
import persistence.InterestAreas;
import converter.OrganizationConverter;


/**
 *
 * @author dave
 */

public class OrganizationResource {
    private String id;
    private UriInfo context;
  
    /** Creates a new instance of OrganizationResource */
    public OrganizationResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public OrganizationResource(String id, UriInfo context) {
        this.id = id;
        this.context = context;
    }

    /**
     * Get method for retrieving an instance of Organizations identified by id in XML format.
     *
     * @param id identifier for the entity
     * @return an instance of OrganizationConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public OrganizationConverter get() {
        try {
            return new OrganizationConverter(getEntity(), context.getAbsolutePath());
        } finally {
            
        }
    }

    /**
     * Put method for updating an instance of Organizations identified by id using XML as the input format.
     *
     * @param id identifier for the entity
     * @param data an OrganizationConverter entity that is deserialized from a XML stream
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void put(OrganizationConverter data) {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            updateEntity(getEntity(), data.getEntity());
            
        } finally {
            
        }
    }

    /**
     * Delete method for deleting an instance of Organizations identified by id.
     *
     * @param id identifier for the entity
     */
    @DELETE
    public void delete() {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            Organizations entity = getEntity();
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
        final Organizations parent = getEntity();
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
                if (!entity.getOrganizationIdCollection().contains(parent)) {
                    entity.getOrganizationIdCollection().add(parent);
                }
            }
        };
    }

    /**
     * Returns a dynamic instance of OrganizationTypeResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of OrganizationTypeResource
     */
    @Path("organizationType/")
    public OrganizationTypeResource getOrganizationTypeResource() {
        final Organizations parent = getEntity();
        return new OrganizationTypeResource(null, context) {

            @Override
            protected OrganizationTypes getEntity() {
                OrganizationTypes entity = parent.getOrganizationTypeId();
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
        final Organizations parent = getEntity();
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
     * Returns a dynamic instance of EventsResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of EventsResource
     */
    @Path("events/")
    public EventsResource getEventsResource() {
        final Organizations parent = getEntity();
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
                entity.setOrganizationId(parent);
            }
        };
    }

    /**
     * Returns an instance of Organizations identified by id.
     *
     * @param id identifier for the entity
     * @return an instance of Organizations
     */
    protected Organizations getEntity() {
        try {
            return (Organizations) new PersistenceServiceBean().createQuery("SELECT e FROM Organizations e where e.id = :id").setParameter("id", id).getSingleResult();
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
    protected Organizations updateEntity(Organizations entity, Organizations newEntity) {
        newEntity.setId(entity.getId());
        entity.getEventsCollection().removeAll(newEntity.getEventsCollection());
        for (Events value : entity.getEventsCollection()) {
            value.setOrganizationId(null);
        }
        entity = new PersistenceServiceBean().mergeEntity(newEntity);
        for (Events value : entity.getEventsCollection()) {
            value.setOrganizationId(entity);
        }
        return entity;
    }
}
