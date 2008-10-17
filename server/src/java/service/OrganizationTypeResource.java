/*
 *  OrganizationTypeResource
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
import persistence.OrganizationTypes;
import persistence.Organizations;
import java.util.Collection;
import converter.OrganizationTypeConverter;


/**
 *
 * @author dave
 */

public class OrganizationTypeResource {
    private String id;
    private UriInfo context;
  
    /** Creates a new instance of OrganizationTypeResource */
    public OrganizationTypeResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public OrganizationTypeResource(String id, UriInfo context) {
        this.id = id;
        this.context = context;
    }

    /**
     * Get method for retrieving an instance of OrganizationTypes identified by id in XML format.
     *
     * @param id identifier for the entity
     * @return an instance of OrganizationTypeConverter
     */
    @GET
    @ProduceMime({"application/xml", "application/json"})
    public OrganizationTypeConverter get() {
        try {
            return new OrganizationTypeConverter(getEntity(), context.getAbsolutePath());
        } finally {
            PersistenceService.getInstance().close();
        }
    }

    /**
     * Put method for updating an instance of OrganizationTypes identified by id using XML as the input format.
     *
     * @param id identifier for the entity
     * @param data an OrganizationTypeConverter entity that is deserialized from a XML stream
     */
    @PUT
    @ConsumeMime({"application/xml", "application/json"})
    public void put(OrganizationTypeConverter data) {
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
     * Delete method for deleting an instance of OrganizationTypes identified by id.
     *
     * @param id identifier for the entity
     */
    @DELETE
    public void delete() {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            OrganizationTypes entity = getEntity();
            persistenceSvc.removeEntity(entity);
            persistenceSvc.commitTx();
        } finally {
            persistenceSvc.close();
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
        final OrganizationTypes parent = getEntity();
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
                entity.setOrganizationTypeId(parent);
            }
        };
    }

    /**
     * Returns an instance of OrganizationTypes identified by id.
     *
     * @param id identifier for the entity
     * @return an instance of OrganizationTypes
     */
    protected OrganizationTypes getEntity() {
        try {
            return (OrganizationTypes) PersistenceService.getInstance().createQuery("SELECT e FROM OrganizationTypes e where e.id = :id").setParameter("id", id).getSingleResult();
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
    protected OrganizationTypes updateEntity(OrganizationTypes entity, OrganizationTypes newEntity) {
        newEntity.setId(entity.getId());
        entity.getOrganizationsCollection().removeAll(newEntity.getOrganizationsCollection());
        for (Organizations value : entity.getOrganizationsCollection()) {
            value.setOrganizationTypeId(null);
        }
        entity = PersistenceService.getInstance().mergeEntity(newEntity);
        for (Organizations value : entity.getOrganizationsCollection()) {
            value.setOrganizationTypeId(entity);
        }
        return entity;
    }
}
