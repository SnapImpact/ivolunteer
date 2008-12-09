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
import persistence.Organization;
import persistence.Source;
import persistence.SourceOrgTypeMap;
import persistence.SourceInterestMap;
import converter.SourceConverter;

/**
 * 
 * @author Dave Angulo
 */

public class SourceResource {
	@Context
	protected UriInfo			uriInfo;
	@Context
	protected ResourceContext	resourceContext;
	protected String			id;

	/** Creates a new instance of SourceResource */
	public SourceResource() {
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get method for retrieving an instance of Source identified by id in XML
	 * format.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @return an instance of SourceConverter
	 */
	@GET
	@Produces( { "application/xml", "application/json" })
	public SourceConverter get(@QueryParam("expandLevel") @DefaultValue("1") int expandLevel) {
		PersistenceService persistenceSvc = PersistenceService.getInstance();
		try {
			persistenceSvc.beginTx();
			return new SourceConverter(getEntity(), uriInfo.getAbsolutePath(), expandLevel);
		} finally {
			PersistenceService.getInstance().close();
		}
	}

	/**
	 * Put method for updating an instance of Source identified by id using XML
	 * as the input format.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @param data
	 *            an SourceConverter entity that is deserialized from a XML
	 *            stream
	 */
	@PUT
	@Consumes( { "application/xml", "application/json" })
	public void put(SourceConverter data) {
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
	 * Delete method for deleting an instance of Source identified by id.
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
	 * Returns an instance of Source identified by id.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @return an instance of Source
	 */
	protected Source getEntity() {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		try {
			return (Source) em.createQuery("SELECT e FROM Source e where e.id = :id").setParameter(
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
	protected Source updateEntity(Source entity, Source newEntity) {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		Collection<Organization> organizationCollection = entity.getOrganizationCollection();
		Collection<Organization> organizationCollectionNew = newEntity.getOrganizationCollection();
		Collection<SourceInterestMap> sourceInterestMapCollection = entity
				.getSourceInterestMapCollection();
		Collection<SourceInterestMap> sourceInterestMapCollectionNew = newEntity
				.getSourceInterestMapCollection();
		Collection<Event> eventCollection = entity.getEventCollection();
		Collection<Event> eventCollectionNew = newEntity.getEventCollection();
		Collection<SourceOrgTypeMap> sourceOrgTypeMapCollection = entity
				.getSourceOrgTypeMapCollection();
		Collection<SourceOrgTypeMap> sourceOrgTypeMapCollectionNew = newEntity
				.getSourceOrgTypeMapCollection();
		entity = em.merge(newEntity);
		for (Organization value : organizationCollection) {
			if (!organizationCollectionNew.contains(value)) {
				throw new WebApplicationException(new Throwable(
						"Cannot remove items from organizationCollection"));
			}
		}
		for (Organization value : organizationCollectionNew) {
			if (!organizationCollection.contains(value)) {
				Source oldEntity = value.getSourceId();
				value.setSourceId(entity);
				if (oldEntity != null && !oldEntity.equals(entity)) {
					oldEntity.getOrganizationCollection().remove(value);
				}
			}
		}
		for (SourceInterestMap value : sourceInterestMapCollection) {
			if (!sourceInterestMapCollectionNew.contains(value)) {
				throw new WebApplicationException(new Throwable(
						"Cannot remove items from sourceInterestMapCollection"));
			}
		}
		for (SourceInterestMap value : sourceInterestMapCollectionNew) {
			if (!sourceInterestMapCollection.contains(value)) {
				Source oldEntity = value.getSourceId();
				value.setSourceId(entity);
				if (oldEntity != null && !oldEntity.equals(entity)) {
					oldEntity.getSourceInterestMapCollection().remove(value);
				}
			}
		}
		for (Event value : eventCollection) {
			if (!eventCollectionNew.contains(value)) {
				throw new WebApplicationException(new Throwable(
						"Cannot remove items from eventCollection"));
			}
		}
		for (Event value : eventCollectionNew) {
			if (!eventCollection.contains(value)) {
				Source oldEntity = value.getSourceId();
				value.setSourceId(entity);
				if (oldEntity != null && !oldEntity.equals(entity)) {
					oldEntity.getEventCollection().remove(value);
				}
			}
		}
		for (SourceOrgTypeMap value : sourceOrgTypeMapCollection) {
			if (!sourceOrgTypeMapCollectionNew.contains(value)) {
				throw new WebApplicationException(new Throwable(
						"Cannot remove items from sourceOrgTypeMapCollection"));
			}
		}
		for (SourceOrgTypeMap value : sourceOrgTypeMapCollectionNew) {
			if (!sourceOrgTypeMapCollection.contains(value)) {
				Source oldEntity = value.getSourceId();
				value.setSourceId(entity);
				if (oldEntity != null && !oldEntity.equals(entity)) {
					oldEntity.getSourceOrgTypeMapCollection().remove(value);
				}
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
	protected void deleteEntity(Source entity) {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		if (!entity.getOrganizationCollection().isEmpty()) {
			throw new WebApplicationException(new Throwable(
					"Cannot delete entity because organizationCollection is not empty."));
		}
		if (!entity.getSourceInterestMapCollection().isEmpty()) {
			throw new WebApplicationException(new Throwable(
					"Cannot delete entity because sourceInterestMapCollection is not empty."));
		}
		if (!entity.getEventCollection().isEmpty()) {
			throw new WebApplicationException(new Throwable(
					"Cannot delete entity because eventCollection is not empty."));
		}
		if (!entity.getSourceOrgTypeMapCollection().isEmpty()) {
			throw new WebApplicationException(new Throwable(
					"Cannot delete entity because sourceOrgTypeMapCollection is not empty."));
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
	 * Returns a dynamic instance of SourceInterestMapsResource used for entity
	 * navigation.
	 * 
	 * @param id
	 *            identifier for the parent entity
	 * @return an instance of SourceInterestMapsResource
	 */
	@Path("sourceInterestMapCollection/")
	public SourceInterestMapsResource getSourceInterestMapCollectionResource() {
		SourceInterestMapCollectionResourceSub resource = resourceContext
				.getResource(SourceInterestMapCollectionResourceSub.class);
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

	/**
	 * Returns a dynamic instance of SourceOrgTypeMapsResource used for entity
	 * navigation.
	 * 
	 * @param id
	 *            identifier for the parent entity
	 * @return an instance of SourceOrgTypeMapsResource
	 */
	@Path("sourceOrgTypeMapCollection/")
	public SourceOrgTypeMapsResource getSourceOrgTypeMapCollectionResource() {
		SourceOrgTypeMapCollectionResourceSub resource = resourceContext
				.getResource(SourceOrgTypeMapCollectionResourceSub.class);
		resource.setParent(getEntity());
		return resource;
	}

	public static class OrganizationCollectionResourceSub extends OrganizationsResource {

		private Source	parent;

		public void setParent(Source parent) {
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

	public static class SourceInterestMapCollectionResourceSub extends SourceInterestMapsResource {

		private Source	parent;

		public void setParent(Source parent) {
			this.parent = parent;
		}

		@Override
		protected Collection<SourceInterestMap> getEntities(int start, int max, String query) {
			Collection<SourceInterestMap> result = new java.util.ArrayList<SourceInterestMap>();
			int index = 0;
			for (SourceInterestMap e : parent.getSourceInterestMapCollection()) {
				if (index >= start && (index - start) < max) {
					result.add(e);
				}
				index++;
			}
			return result;
		}
	}

	public static class EventCollectionResourceSub extends EventsResource {

		private Source	parent;

		public void setParent(Source parent) {
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

	public static class SourceOrgTypeMapCollectionResourceSub extends SourceOrgTypeMapsResource {

		private Source	parent;

		public void setParent(Source parent) {
			this.parent = parent;
		}

		@Override
		protected Collection<SourceOrgTypeMap> getEntities(int start, int max, String query) {
			Collection<SourceOrgTypeMap> result = new java.util.ArrayList<SourceOrgTypeMap>();
			int index = 0;
			for (SourceOrgTypeMap e : parent.getSourceOrgTypeMapCollection()) {
				if (index >= start && (index - start) < max) {
					result.add(e);
				}
				index++;
			}
			return result;
		}
	}
}
