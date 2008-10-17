/*
 *  InterestAreaResource
 *
 * Created on October 11, 2008, 9:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package service;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.ProduceMime;
import javax.ws.rs.ConsumeMime;
import javax.ws.rs.WebApplicationException;
import javax.persistence.NoResultException;
import javax.ws.rs.core.UriInfo;
import persistence.InterestAreas;
import persistence.Organizations;
import java.util.Collection;
import persistence.Events;
import converter.InterestAreaConverter;


/**
 *
 * @author dave
 */

public class InterestAreaResource {
    private String id;
    private UriInfo context;
  
    /** Creates a new instance of InterestAreaResource */
    public InterestAreaResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public InterestAreaResource(String id, UriInfo context) {
        this.id = id;
        this.context = context;
    }

    /**
     * Get method for retrieving an instance of InterestAreas identified by id in XML format.
     *
     * @param id identifier for the entity
     * @return an instance of InterestAreaConverter
     */
    @GET
    @ProduceMime({"application/xml", "application/json"})
    public InterestAreaConverter get() {
        try {
            return new InterestAreaConverter(getEntity(), context.getAbsolutePath());
        } finally {
            PersistenceService.getInstance().close();
        }
    }

    /**
     * Put method for updating an instance of InterestAreas identified by id using XML as the input format.
     *
     * @param id identifier for the entity
     * @param data an InterestAreaConverter entity that is deserialized from a XML stream
     */
    @PUT
    @ConsumeMime({"application/xml", "application/json"})
    public void put(InterestAreaConverter data) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            updateEntity(getEntity(), data.getEntity());
            persistenceSvc.commitTx();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Delete method for deleting an instance of InterestAreas identified by id.
     *
     * @param id identifier for the entity
     */
    @DELETE
    public void delete() {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            InterestAreas entity = getEntity();
            persistenceSvc.removeEntity(entity);
            persistenceSvc.commitTx();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Returns a dynamic instance of EventsResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of EventsResource
     */
    @Path("events/")
    public EventsResource getEventsResource() {
        final InterestAreas parent = getEntity();
        return new EventsResource(context) {

            @Override
            protected Collection<Events> getEntities(int start, int max) {
                Collection<Events> result = new java.util.ArrayList<Events>();
                int index = 0;
                for (Events e : parent.getEventIdCollection()) {
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
                if (!entity.getInterestAreaIdCollection().contains(parent)) {
                    entity.getInterestAreaIdCollection().add(parent);
                }
            }
        };
    }

    /**
     * Returns a dynamic instance of OrganizationsResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of OrganizationsResource
     */
    @Path("organizations/")
    public OrganizationsResource getOrganizationsResource() {
        final InterestAreas parent = getEntity();
        return new OrganizationsResource(context) {

            @Override
            protected Collection<Organizations> getEntities(int start, int max) {
                Collection<Organizations> result = new java.util.ArrayList<Organizations>();
                int index = 0;
                for (Organizations e : parent.getOrganizationIdCollection()) {
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
                if (!entity.getInterestAreaIdCollection().contains(parent)) {
                    entity.getInterestAreaIdCollection().add(parent);
                }
            }
        };
    }

    /**
     * Returns an instance of InterestAreas identified by id.
     *
     * @param id identifier for the entity
     * @return an instance of InterestAreas
     */
    protected InterestAreas getEntity() {
        try {
            return (InterestAreas) PersistenceService.getInstance().createQuery("SELECT e FROM InterestAreas e where e.id = :id").setParameter("id", id).getSingleResult();
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
    protected InterestAreas updateEntity(InterestAreas entity, InterestAreas newEntity) {
        newEntity.setId(entity.getId());
        entity = PersistenceService.getInstance().mergeEntity(newEntity);
        return entity;
    }
}
