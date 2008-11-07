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
import persistence.Event;
import java.util.Collection;
import converter.TimestampConverter;
import persistence.Timestamp;

/**
 *
 * @author dave
 */

public class TimestampResource {
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
    protected String id;
  
    /** Creates a new instance of TimestampResource */
    public TimestampResource() {
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get method for retrieving an instance of Timestamp identified by id in XML format.
     *
     * @param id identifier for the entity
     * @return an instance of TimestampConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public TimestampConverter get(@QueryParam("expandLevel")
    @DefaultValue("1")
    int expandLevel) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            return new TimestampConverter(getEntity(), uriInfo.getAbsolutePath(), expandLevel);
        } finally {
            PersistenceService.getInstance().close();
        }
    }

    /**
     * Put method for updating an instance of Timestamp identified by id using XML as the input format.
     *
     * @param id identifier for the entity
     * @param data an TimestampConverter entity that is deserialized from a XML stream
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void put(TimestampConverter data) {
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
     * Delete method for deleting an instance of Timestamp identified by id.
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
     * Returns an instance of Timestamp identified by id.
     *
     * @param id identifier for the entity
     * @return an instance of Timestamp
     */
    protected Timestamp getEntity() {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        try {
            return (Timestamp) em.createQuery("SELECT e FROM Timestamp e where e.id = :id").setParameter("id", id).getSingleResult();
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
    protected Timestamp updateEntity(Timestamp entity, Timestamp newEntity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        Collection<Event> eventCollection = entity.getEventCollection();
        Collection<Event> eventCollectionNew = newEntity.getEventCollection();
        entity = em.merge(newEntity);
        for (Event value : eventCollection) {
            if (!eventCollectionNew.contains(value)) {
                value.getTimestampCollection().remove(entity);
            }
        }
        for (Event value : eventCollectionNew) {
            if (!eventCollection.contains(value)) {
                value.getTimestampCollection().add(entity);
            }
        }
        return entity;
    }

    /**
     * Deletes the entity.
     *
     * @param entity the entity to deletle
     */
    protected void deleteEntity(Timestamp entity) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        for (Event value : entity.getEventCollection()) {
            value.getTimestampCollection().remove(entity);
        }
        em.remove(entity);
    }

    /**
     * Returns a dynamic instance of EventsResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of EventsResource
     */
    @Path("eventCollection/")
    public EventsResource getEventCollectionResource() {
        EventCollectionResourceSub resource = resourceContext.getResource(EventCollectionResourceSub.class);
        resource.setParent(getEntity());
        return resource;
    }

    public static class EventCollectionResourceSub extends EventsResource {

        private Timestamp parent;

        public void setParent(Timestamp parent) {
            this.parent = parent;
        }

        @Override
        protected Collection<Event> getEntities(int start, int max, String query) {
            Collection<Event> result = new java.util.ArrayList<Event>();
            int index = 0;
            for (Event e : parent.getEventCollection()) {
                if (index >= start && (index - start) < max) {
                    result.add(e);
                }
                index++;
            }
            return result;
        }
    }
}
