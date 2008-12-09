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
import persistence.Source;
import persistence.InterestArea;
import java.util.Collection;
import persistence.Organization;
import persistence.Location;
import persistence.Timestamp;
import converter.EventConverter;

/**
 * 
 * @author Dave Angulo
 */

public class EventResource {
	@Context
	protected UriInfo			uriInfo;
	@Context
	protected ResourceContext	resourceContext;
	protected String			id;

	/** Creates a new instance of EventResource */
	public EventResource() {
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get method for retrieving an instance of Event identified by id in XML
	 * format.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @return an instance of EventConverter
	 */
	@GET
	@Produces( { "application/xml", "application/json" })
	public EventConverter get(@QueryParam("expandLevel") @DefaultValue("1") int expandLevel) {
		PersistenceService persistenceSvc = PersistenceService.getInstance();
		try {
			persistenceSvc.beginTx();
			return new EventConverter(getEntity(), uriInfo.getAbsolutePath(), expandLevel);
		} finally {
			PersistenceService.getInstance().close();
		}
	}

	/**
	 * Put method for updating an instance of Event identified by id using XML
	 * as the input format.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @param data
	 *            an EventConverter entity that is deserialized from a XML
	 *            stream
	 */
	@PUT
	@Consumes( { "application/xml", "application/json" })
	public void put(EventConverter data) {
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
	 * Delete method for deleting an instance of Event identified by id.
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
	 * Returns an instance of Event identified by id.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @return an instance of Event
	 */
	protected Event getEntity() {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		try {
			return (Event) em.createQuery("SELECT e FROM Event e where e.id = :id").setParameter(
					"id", id).getSingleResult();
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
	protected Event updateEntity(Event entity, Event newEntity) {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		Collection<InterestArea> interestAreaCollection = entity.getInterestAreaCollection();
		Collection<InterestArea> interestAreaCollectionNew = newEntity.getInterestAreaCollection();
		Collection<Timestamp> timestampCollection = entity.getTimestampCollection();
		Collection<Timestamp> timestampCollectionNew = newEntity.getTimestampCollection();
		Collection<Location> locationCollection = entity.getLocationCollection();
		Collection<Location> locationCollectionNew = newEntity.getLocationCollection();
		Collection<Organization> organizationCollection = entity.getOrganizationCollection();
		Collection<Organization> organizationCollectionNew = newEntity.getOrganizationCollection();
		Source sourceId = entity.getSourceId();
		Source sourceIdNew = newEntity.getSourceId();
		entity = em.merge(newEntity);
		for (InterestArea value : interestAreaCollection) {
			if (!interestAreaCollectionNew.contains(value)) {
				value.getEventCollection().remove(entity);
			}
		}
		for (InterestArea value : interestAreaCollectionNew) {
			if (!interestAreaCollection.contains(value)) {
				value.getEventCollection().add(entity);
			}
		}
		for (Timestamp value : timestampCollection) {
			if (!timestampCollectionNew.contains(value)) {
				value.getEventCollection().remove(entity);
			}
		}
		for (Timestamp value : timestampCollectionNew) {
			if (!timestampCollection.contains(value)) {
				value.getEventCollection().add(entity);
			}
		}
		for (Location value : locationCollection) {
			if (!locationCollectionNew.contains(value)) {
				value.getEventCollection().remove(entity);
			}
		}
		for (Location value : locationCollectionNew) {
			if (!locationCollection.contains(value)) {
				value.getEventCollection().add(entity);
			}
		}
		for (Organization value : organizationCollection) {
			if (!organizationCollectionNew.contains(value)) {
				value.getEventCollection().remove(entity);
			}
		}
		for (Organization value : organizationCollectionNew) {
			if (!organizationCollection.contains(value)) {
				value.getEventCollection().add(entity);
			}
		}
		if (sourceId != null && !sourceId.equals(sourceIdNew)) {
			sourceId.getEventCollection().remove(entity);
		}
		if (sourceIdNew != null && !sourceIdNew.equals(sourceId)) {
			sourceIdNew.getEventCollection().add(entity);
		}
		return entity;
	}

	/**
	 * Deletes the entity.
	 * 
	 * @param entity
	 *            the entity to deletle
	 */
	protected void deleteEntity(Event entity) {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		for (InterestArea value : entity.getInterestAreaCollection()) {
			value.getEventCollection().remove(entity);
		}
		for (Timestamp value : entity.getTimestampCollection()) {
			value.getEventCollection().remove(entity);
		}
		for (Location value : entity.getLocationCollection()) {
			value.getEventCollection().remove(entity);
		}
		for (Organization value : entity.getOrganizationCollection()) {
			value.getEventCollection().remove(entity);
		}
		Source sourceId = entity.getSourceId();
		if (sourceId != null) {
			sourceId.getEventCollection().remove(entity);
		}
		em.remove(entity);
	}

	/**
	 * Returns a dynamic instance of InterestAreasResource used for entity
	 * navigation.
	 * 
	 * @param id
	 *            identifier for the parent entity
	 * @return an instance of InterestAreasResource
	 */
	@Path("interestAreaCollection/")
	public InterestAreasResource getInterestAreaCollectionResource() {
		InterestAreaCollectionResourceSub resource = resourceContext
				.getResource(InterestAreaCollectionResourceSub.class);
		resource.setParent(getEntity());
		return resource;
	}

	/**
	 * Returns a dynamic instance of TimestampsResource used for entity
	 * navigation.
	 * 
	 * @param id
	 *            identifier for the parent entity
	 * @return an instance of TimestampsResource
	 */
	@Path("timestampCollection/")
	public TimestampsResource getTimestampCollectionResource() {
		TimestampCollectionResourceSub resource = resourceContext
				.getResource(TimestampCollectionResourceSub.class);
		resource.setParent(getEntity());
		return resource;
	}

	/**
	 * Returns a dynamic instance of LocationsResource used for entity
	 * navigation.
	 * 
	 * @param id
	 *            identifier for the parent entity
	 * @return an instance of LocationsResource
	 */
	@Path("locationCollection/")
	public LocationsResource getLocationCollectionResource() {
		LocationCollectionResourceSub resource = resourceContext
				.getResource(LocationCollectionResourceSub.class);
		resource.setParent(getEntity());
		return resource;
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
	 * Returns a dynamic instance of SourceResource used for entity navigation.
	 * 
	 * @param id
	 *            identifier for the parent entity
	 * @return an instance of SourceResource
	 */
	@Path("sourceId/")
	public SourceResource getSourceIdResource() {
		SourceIdResourceSub resource = resourceContext.getResource(SourceIdResourceSub.class);
		resource.setParent(getEntity());
		return resource;
	}

	public static class InterestAreaCollectionResourceSub extends InterestAreasResource {

		private Event	parent;

		public void setParent(Event parent) {
			this.parent = parent;
		}

		@Override
		protected Collection<InterestArea> getEntities(int start, int max, String query) {
			Collection<InterestArea> result = new java.util.ArrayList<InterestArea>();
			int index = 0;
			for (InterestArea e : parent.getInterestAreaCollection()) {
				if (index >= start && (index - start) < max) {
					result.add(e);
				}
				index++;
			}
			return result;
		}
	}

	public static class TimestampCollectionResourceSub extends TimestampsResource {

		private Event	parent;

		public void setParent(Event parent) {
			this.parent = parent;
		}

		@Override
		protected Collection<Timestamp> getEntities(int start, int max, String query) {
			Collection<Timestamp> result = new java.util.ArrayList<Timestamp>();
			int index = 0;
			for (Timestamp e : parent.getTimestampCollection()) {
				if (index >= start && (index - start) < max) {
					result.add(e);
				}
				index++;
			}
			return result;
		}
	}

	public static class LocationCollectionResourceSub extends LocationsResource {

		private Event	parent;

		public void setParent(Event parent) {
			this.parent = parent;
		}

		@Override
		protected Collection<Location> getEntities(int start, int max, String query) {
			Collection<Location> result = new java.util.ArrayList<Location>();
			int index = 0;
			for (Location e : parent.getLocationCollection()) {
				if (index >= start && (index - start) < max) {
					result.add(e);
				}
				index++;
			}
			return result;
		}
	}

	public static class OrganizationCollectionResourceSub extends OrganizationsResource {

		private Event	parent;

		public void setParent(Event parent) {
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

	public static class SourceIdResourceSub extends SourceResource {

		private Event	parent;

		public void setParent(Event parent) {
			this.parent = parent;
		}

		@Override
		protected Source getEntity() {
			Source entity = parent.getSourceId();
			if (entity == null) {
				throw new WebApplicationException(new Throwable("Resource for "
						+ uriInfo.getAbsolutePath() + " does not exist."), 404);
			}
			return entity;
		}
	}
}
