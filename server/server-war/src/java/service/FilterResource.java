/*
 *  FilterResource
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
import persistence.Filter;
import persistence.OrganizationTypes;
import java.util.Collection;
import persistence.Distances;
import persistence.Users;
import persistence.Timeframes;
import persistence.InterestAreas;
import converter.FilterConverter;


/**
 *
 * @author dave
 */

public class FilterResource {
    private String id;
    private UriInfo context;
  
    /** Creates a new instance of FilterResource */
    public FilterResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public FilterResource(String id, UriInfo context) {
        this.id = id;
        this.context = context;
    }

    /**
     * Get method for retrieving an instance of Filter identified by id in XML format.
     *
     * @param id identifier for the entity
     * @return an instance of FilterConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public FilterConverter get() {
        try {
            return new FilterConverter(getEntity(), context.getAbsolutePath());
        } finally {
            
        }
    }

    /**
     * Put method for updating an instance of Filter identified by id using XML as the input format.
     *
     * @param id identifier for the entity
     * @param data an FilterConverter entity that is deserialized from a XML stream
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void put(FilterConverter data) {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            updateEntity(getEntity(), data.getEntity());
            
        } finally {
            
        }
    }

    /**
     * Delete method for deleting an instance of Filter identified by id.
     *
     * @param id identifier for the entity
     */
    @DELETE
    public void delete() {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            Filter entity = getEntity();
            persistenceSvc.removeEntity(entity);
            
        } finally {
            
        }
    }

    /**
     * Returns a dynamic instance of OrganizationTypesResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of OrganizationTypesResource
     */
    @Path("organizationTypes/")
    public OrganizationTypesResource getOrganizationTypesResource() {
        final Filter parent = getEntity();
        return new OrganizationTypesResource(context) {

            @Override
            protected Collection<OrganizationTypes> getEntities(int start, int max) {
                Collection<OrganizationTypes> result = new java.util.ArrayList<OrganizationTypes>();
                int index = 0;
                for (OrganizationTypes e : parent.getOrganizationTypeIdCollection()) {
                    if (index >= start && (index - start) < max) {
                        result.add(e);
                    }
                    index++;
                }
                return result;
            }

            @Override
            protected void createEntity(OrganizationTypes entity) {
                super.createEntity(entity);
                if (!entity.getFilterIdCollection().contains(parent)) {
                    entity.getFilterIdCollection().add(parent);
                }
            }
        };
    }

    /**
     * Returns a dynamic instance of InterestAreasResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of InterestAreasResource
     */
    @Path("interestAreas/")
    public InterestAreasResource getInterestAreasResource() {
        final Filter parent = getEntity();
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
                if (!entity.getFilterIdCollection().contains(parent)) {
                    entity.getFilterIdCollection().add(parent);
                }
            }
        };
    }

    /**
     * Returns a dynamic instance of DistanceResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of DistanceResource
     */
    @Path("distance/")
    public DistanceResource getDistanceResource() {
        final Filter parent = getEntity();
        return new DistanceResource(null, context) {

            @Override
            protected Distances getEntity() {
                Distances entity = parent.getDistanceId();
                if (entity == null) {
                    throw new WebApplicationException(new Throwable("Resource for " + context.getAbsolutePath() + " does not exist."), 404);
                }
                return entity;
            }
        };
    }

    /**
     * Returns a dynamic instance of TimeframeResource used for entity navigation.
     *
     * @param id identifier for the parent entity
     * @return an instance of TimeframeResource
     */
    @Path("timeframe/")
    public TimeframeResource getTimeframeResource() {
        final Filter parent = getEntity();
        return new TimeframeResource(null, context) {

            @Override
            protected Timeframes getEntity() {
                Timeframes entity = parent.getTimeframeId();
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
        final Filter parent = getEntity();
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
     * Returns an instance of Filter identified by id.
     *
     * @param id identifier for the entity
     * @return an instance of Filter
     */
    protected Filter getEntity() {
        try {
            return (Filter) new PersistenceServiceBean().createQuery("SELECT e FROM Filter e where e.id = :id").setParameter("id", id).getSingleResult();
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
    protected Filter updateEntity(Filter entity, Filter newEntity) {
        newEntity.setId(entity.getId());
        entity = new PersistenceServiceBean().mergeEntity(newEntity);
        return entity;
    }
}
