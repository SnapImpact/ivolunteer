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
import java.util.Collection;
import persistence.Integration;
import persistence.Filter;
import converter.IvUserConverter;
import persistence.IvUser;

/**
 * 
 * @author Dave Angulo
 */

public class IvUserResource {
	@Context
	protected UriInfo			uriInfo;
	@Context
	protected ResourceContext	resourceContext;
	protected String			id;

	/** Creates a new instance of IvUserResource */
	public IvUserResource() {
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get method for retrieving an instance of IvUser identified by id in XML
	 * format.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @return an instance of IvUserConverter
	 */
	@GET
	@Produces( { "application/xml", "application/json" })
	public IvUserConverter get(@QueryParam("expandLevel") @DefaultValue("1") int expandLevel) {
		PersistenceService persistenceSvc = PersistenceService.getInstance();
		try {
			persistenceSvc.beginTx();
			return new IvUserConverter(getEntity(), uriInfo.getAbsolutePath(), expandLevel);
		} finally {
			PersistenceService.getInstance().close();
		}
	}

	/**
	 * Put method for updating an instance of IvUser identified by id using XML
	 * as the input format.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @param data
	 *            an IvUserConverter entity that is deserialized from a XML
	 *            stream
	 */
	@PUT
	@Consumes( { "application/xml", "application/json" })
	public void put(IvUserConverter data) {
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
	 * Delete method for deleting an instance of IvUser identified by id.
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
	 * Returns an instance of IvUser identified by id.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @return an instance of IvUser
	 */
	protected IvUser getEntity() {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		try {
			return (IvUser) em.createQuery("SELECT e FROM IvUser e where e.id = :id").setParameter(
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
	protected IvUser updateEntity(IvUser entity, IvUser newEntity) {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		Collection<Filter> filterCollection = entity.getFilterCollection();
		Collection<Filter> filterCollectionNew = newEntity.getFilterCollection();
		Collection<Integration> integrationCollection = entity.getIntegrationCollection();
		Collection<Integration> integrationCollectionNew = newEntity.getIntegrationCollection();
		entity = em.merge(newEntity);
		for (Filter value : filterCollection) {
			if (!filterCollectionNew.contains(value)) {
				throw new WebApplicationException(new Throwable(
						"Cannot remove items from filterCollection"));
			}
		}
		for (Filter value : filterCollectionNew) {
			if (!filterCollection.contains(value)) {
				IvUser oldEntity = value.getUserId();
				value.setUserId(entity);
				if (oldEntity != null && !oldEntity.equals(entity)) {
					oldEntity.getFilterCollection().remove(value);
				}
			}
		}
		for (Integration value : integrationCollection) {
			if (!integrationCollectionNew.contains(value)) {
				throw new WebApplicationException(new Throwable(
						"Cannot remove items from integrationCollection"));
			}
		}
		for (Integration value : integrationCollectionNew) {
			if (!integrationCollection.contains(value)) {
				IvUser oldEntity = value.getUserId();
				value.setUserId(entity);
				if (oldEntity != null && !oldEntity.equals(entity)) {
					oldEntity.getIntegrationCollection().remove(value);
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
	protected void deleteEntity(IvUser entity) {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		if (!entity.getFilterCollection().isEmpty()) {
			throw new WebApplicationException(new Throwable(
					"Cannot delete entity because filterCollection is not empty."));
		}
		if (!entity.getIntegrationCollection().isEmpty()) {
			throw new WebApplicationException(new Throwable(
					"Cannot delete entity because integrationCollection is not empty."));
		}
		em.remove(entity);
	}

	/**
	 * Returns a dynamic instance of FiltersResource used for entity navigation.
	 * 
	 * @param id
	 *            identifier for the parent entity
	 * @return an instance of FiltersResource
	 */
	@Path("filterCollection/")
	public FiltersResource getFilterCollectionResource() {
		FilterCollectionResourceSub resource = resourceContext
				.getResource(FilterCollectionResourceSub.class);
		resource.setParent(getEntity());
		return resource;
	}

	/**
	 * Returns a dynamic instance of IntegrationsResource used for entity
	 * navigation.
	 * 
	 * @param id
	 *            identifier for the parent entity
	 * @return an instance of IntegrationsResource
	 */
	@Path("integrationCollection/")
	public IntegrationsResource getIntegrationCollectionResource() {
		IntegrationCollectionResourceSub resource = resourceContext
				.getResource(IntegrationCollectionResourceSub.class);
		resource.setParent(getEntity());
		return resource;
	}

	public static class FilterCollectionResourceSub extends FiltersResource {

		private IvUser	parent;

		public void setParent(IvUser parent) {
			this.parent = parent;
		}

		@Override
		protected Collection<Filter> getEntities(int start, int max, String query) {
			Collection<Filter> result = new java.util.ArrayList<Filter>();
			int index = 0;
			for (Filter e : parent.getFilterCollection()) {
				if (index >= start && (index - start) < max) {
					result.add(e);
				}
				index++;
			}
			return result;
		}
	}

	public static class IntegrationCollectionResourceSub extends IntegrationsResource {

		private IvUser	parent;

		public void setParent(IvUser parent) {
			this.parent = parent;
		}

		@Override
		protected Collection<Integration> getEntities(int start, int max, String query) {
			Collection<Integration> result = new java.util.ArrayList<Integration>();
			int index = 0;
			for (Integration e : parent.getIntegrationCollection()) {
				if (index >= start && (index - start) < max) {
					result.add(e);
				}
				index++;
			}
			return result;
		}
	}
}
