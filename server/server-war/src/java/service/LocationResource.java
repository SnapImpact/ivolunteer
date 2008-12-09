/*
 *  Copyright (c) 2008 Boulder Community Foundation - iVolunteer
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
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
import persistence.Location;
import persistence.Organization;
import converter.LocationConverter;

/**
 * 
 * @author Dave Angulo
 */

public class LocationResource {
	@Context
	protected UriInfo			uriInfo;
	@Context
	protected ResourceContext	resourceContext;
	protected String			id;

	/** Creates a new instance of LocationResource */
	public LocationResource() {
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get method for retrieving an instance of Location identified by id in XML
	 * format.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @return an instance of LocationConverter
	 */
	@GET
	@Produces( { "application/xml", "application/json" })
	public LocationConverter get(@QueryParam("expandLevel") @DefaultValue("1") int expandLevel) {
		PersistenceService persistenceSvc = PersistenceService.getInstance();
		try {
			persistenceSvc.beginTx();
			return new LocationConverter(getEntity(), uriInfo.getAbsolutePath(), expandLevel);
		} finally {
			PersistenceService.getInstance().close();
		}
	}

	/**
	 * Put method for updating an instance of Location identified by id using
	 * XML as the input format.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @param data
	 *            an LocationConverter entity that is deserialized from a XML
	 *            stream
	 */
	@PUT
	@Consumes( { "application/xml", "application/json" })
	public void put(LocationConverter data) {
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
	 * Delete method for deleting an instance of Location identified by id.
	 * 
	 * @param id
	 *            identifier for the entity
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
	 * Returns an instance of Location identified by id.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @return an instance of Location
	 */
	protected Location getEntity() {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		try {
			return (Location) em.createQuery("SELECT e FROM Location e where e.id = :id")
					.setParameter("id", id).getSingleResult();
		} catch (NoResultException ex) {
			throw new WebApplicationException(new Throwable("Resource for "
					+ uriInfo.getAbsolutePath() + " does not exist."), 404);
		}
	}

	/**
	 * Updates entity using data from newEntity.
	 * 
	 * @param entity
	 *            the entity to update
	 * @param newEntity
	 *            the entity containing the new data
	 * @return the updated entity
	 */
	protected Location updateEntity(Location entity, Location newEntity) {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		Collection<Organization> organizationCollection = entity.getOrganizationCollection();
		Collection<Organization> organizationCollectionNew = newEntity.getOrganizationCollection();
		Collection<Event> eventCollection = entity.getEventCollection();
		Collection<Event> eventCollectionNew = newEntity.getEventCollection();
		entity = em.merge(newEntity);
		for (Organization value : organizationCollection) {
			if (!organizationCollectionNew.contains(value)) {
				value.getLocationCollection().remove(entity);
			}
		}
		for (Organization value : organizationCollectionNew) {
			if (!organizationCollection.contains(value)) {
				value.getLocationCollection().add(entity);
			}
		}
		for (Event value : eventCollection) {
			if (!eventCollectionNew.contains(value)) {
				value.getLocationCollection().remove(entity);
			}
		}
		for (Event value : eventCollectionNew) {
			if (!eventCollection.contains(value)) {
				value.getLocationCollection().add(entity);
			}
		}
		return entity;
	}

	/**
	 * Deletes the entity.
	 * 
	 * @param entity
	 *            the entity to deletle
	 */
	protected void deleteEntity(Location entity) {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		for (Organization value : entity.getOrganizationCollection()) {
			value.getLocationCollection().remove(entity);
		}
		for (Event value : entity.getEventCollection()) {
			value.getLocationCollection().remove(entity);
		}
		em.remove(entity);
	}

	/**
	 * Returns a dynamic instance of OrganizationsResource used for entity
	 * navigation.
	 * 
	 * @param id
	 *            identifier for the parent entity
	 * @return an instance of OrganizationsResource
	 */
	@Path("organizationCollection/")
	public OrganizationsResource getOrganizationCollectionResource() {
		OrganizationCollectionResourceSub resource = resourceContext
				.getResource(OrganizationCollectionResourceSub.class);
		resource.setParent(getEntity());
		return resource;
	}

	/**
	 * Returns a dynamic instance of EventsResource used for entity navigation.
	 * 
	 * @param id
	 *            identifier for the parent entity
	 * @return an instance of EventsResource
	 */
	@Path("eventCollection/")
	public EventsResource getEventCollectionResource() {
		EventCollectionResourceSub resource = resourceContext
				.getResource(EventCollectionResourceSub.class);
		resource.setParent(getEntity());
		return resource;
	}

	public static class OrganizationCollectionResourceSub extends OrganizationsResource {

		private Location	parent;

		public void setParent(Location parent) {
			this.parent = parent;
		}

		@Override
		protected Collection<Organization> getEntities(int start, int max, String query) {
			Collection<Organization> result = new java.util.ArrayList<Organization>();
			int index = 0;
			for (Organization e : parent.getOrganizationCollection()) {
				if (index >= start && (index - start) < max) {
					result.add(e);
				}
				index++;
			}
			return result;
		}
	}

	public static class EventCollectionResourceSub extends EventsResource {

		private Location	parent;

		public void setParent(Location parent) {
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
