/*
 *  InterestAreaResource
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
import persistence.InterestAreas;
import persistence.Organizations;
import java.util.Collection;
import persistence.Events;
import persistence.Filter;
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
    @Produces({"application/xml", "application/json"})
    public InterestAreaConverter get() {
        try {
            return new InterestAreaConverter(getEntity(), context.getAbsolutePath());
        } finally {
            
        }
    }

    /**
     * Put method for updating an instance of InterestAreas identified by id using XML as the input format.
     *
     * @param id identifier for the entity
     * @param data an InterestAreaConverter entity that is deserialized from a XML stream
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void put(InterestAreaConverter data) {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            updateEntity(getEntity(), data.getEntity());
            
        } finally {
            
        }
    }

    /**
     * Delete method for deleting an instance of InterestAreas identified by id.
     *
     * @param id identifier for the entity
     */
    @DELETE
    public void delete() {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            InterestAreas entity = getEntity();
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
     * Returns a dynamic instance of FiltersResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of FiltersResource
     */
    @Path("filters/")
    public FiltersResource getFiltersResource() {
        final InterestAreas parent = getEntity();
        return new FiltersResource(context) {

            @Override
            protected Collection<Filter> getEntities(int start, int max) {
                Collection<Filter> result = new java.util.ArrayList<Filter>();
                int index = 0;
                for (Filter e : parent.getFilterIdCollection()) {
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
            return (InterestAreas) new PersistenceServiceBean().createQuery("SELECT e FROM InterestAreas e where e.id = :id").setParameter("id", id).getSingleResult();
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
        entity = new PersistenceServiceBean().mergeEntity(newEntity);
        return entity;
    }
}
