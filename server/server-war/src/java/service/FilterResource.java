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
import persistence.Filter;
import persistence.InterestArea;
import java.util.Collection;
import persistence.OrganizationType;
import persistence.Timeframe;
import persistence.Distance;
import persistence.IvUser;
import converter.FilterConverter;

/**
 * 
 * @author Dave Angulo
 */

public class FilterResource {
	@Context
	protected UriInfo			uriInfo;
	@Context
	protected ResourceContext	resourceContext;
	protected String			id;

	/** Creates a new instance of FilterResource */
	public FilterResource() {
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get method for retrieving an instance of Filter identified by id in XML
	 * format.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @return an instance of FilterConverter
	 */
	@GET
	@Produces( { "application/xml", "application/json" })
	public FilterConverter get(@QueryParam("expandLevel") @DefaultValue("1") int expandLevel) {
		PersistenceService persistenceSvc = PersistenceService.getInstance();
		try {
			persistenceSvc.beginTx();
			return new FilterConverter(getEntity(), uriInfo.getAbsolutePath(), expandLevel);
		} finally {
			PersistenceService.getInstance().close();
		}
	}

	/**
	 * Put method for updating an instance of Filter identified by id using XML
	 * as the input format.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @param data
	 *            an FilterConverter entity that is deserialized from a XML
	 *            stream
	 */
	@PUT
	@Consumes( { "application/xml", "application/json" })
	public void put(FilterConverter data) {
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
	 * Delete method for deleting an instance of Filter identified by id.
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
	 * Returns an instance of Filter identified by id.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @return an instance of Filter
	 */
	protected Filter getEntity() {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		try {
			return (Filter) em.createQuery("SELECT e FROM Filter e where e.id = :id").setParameter(
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
	protected Filter updateEntity(Filter entity, Filter newEntity) {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		Collection<OrganizationType> organizationTypeCollection = entity
				.getOrganizationTypeCollection();
		Collection<OrganizationType> organizationTypeCollectionNew = newEntity
				.getOrganizationTypeCollection();
		Collection<InterestArea> interestAreaCollection = entity.getInterestAreaCollection();
		Collection<InterestArea> interestAreaCollectionNew = newEntity.getInterestAreaCollection();
		Distance distanceId = entity.getDistanceId();
		Distance distanceIdNew = newEntity.getDistanceId();
		IvUser userId = entity.getUserId();
		IvUser userIdNew = newEntity.getUserId();
		Timeframe timeframeId = entity.getTimeframeId();
		Timeframe timeframeIdNew = newEntity.getTimeframeId();
		entity = em.merge(newEntity);
		for (OrganizationType value : organizationTypeCollection) {
			if (!organizationTypeCollectionNew.contains(value)) {
				value.getFilterCollection().remove(entity);
			}
		}
		for (OrganizationType value : organizationTypeCollectionNew) {
			if (!organizationTypeCollection.contains(value)) {
				value.getFilterCollection().add(entity);
			}
		}
		for (InterestArea value : interestAreaCollection) {
			if (!interestAreaCollectionNew.contains(value)) {
				value.getFilterCollection().remove(entity);
			}
		}
		for (InterestArea value : interestAreaCollectionNew) {
			if (!interestAreaCollection.contains(value)) {
				value.getFilterCollection().add(entity);
			}
		}
		if (distanceId != null && !distanceId.equals(distanceIdNew)) {
			distanceId.getFilterCollection().remove(entity);
		}
		if (distanceIdNew != null && !distanceIdNew.equals(distanceId)) {
			distanceIdNew.getFilterCollection().add(entity);
		}
		if (userId != null && !userId.equals(userIdNew)) {
			userId.getFilterCollection().remove(entity);
		}
		if (userIdNew != null && !userIdNew.equals(userId)) {
			userIdNew.getFilterCollection().add(entity);
		}
		if (timeframeId != null && !timeframeId.equals(timeframeIdNew)) {
			timeframeId.getFilterCollection().remove(entity);
		}
		if (timeframeIdNew != null && !timeframeIdNew.equals(timeframeId)) {
			timeframeIdNew.getFilterCollection().add(entity);
		}
		return entity;
	}

	/**
	 * Deletes the entity.
	 * 
	 * @param entity
	 *            the entity to deletle
	 */
	protected void deleteEntity(Filter entity) {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		for (OrganizationType value : entity.getOrganizationTypeCollection()) {
			value.getFilterCollection().remove(entity);
		}
		for (InterestArea value : entity.getInterestAreaCollection()) {
			value.getFilterCollection().remove(entity);
		}
		Distance distanceId = entity.getDistanceId();
		if (distanceId != null) {
			distanceId.getFilterCollection().remove(entity);
		}
		IvUser userId = entity.getUserId();
		if (userId != null) {
			userId.getFilterCollection().remove(entity);
		}
		Timeframe timeframeId = entity.getTimeframeId();
		if (timeframeId != null) {
			timeframeId.getFilterCollection().remove(entity);
		}
		em.remove(entity);
	}

	/**
	 * Returns a dynamic instance of OrganizationTypesResource used for entity
	 * navigation.
	 * 
	 * @param id
	 *            identifier for the parent entity
	 * @return an instance of OrganizationTypesResource
	 */
	@Path("organizationTypeCollection/")
	public OrganizationTypesResource getOrganizationTypeCollectionResource() {
		OrganizationTypeCollectionResourceSub resource = resourceContext
				.getResource(OrganizationTypeCollectionResourceSub.class);
		resource.setParent(getEntity());
		return resource;
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
	 * Returns a dynamic instance of DistanceResource used for entity
	 * navigation.
	 * 
	 * @param id
	 *            identifier for the parent entity
	 * @return an instance of DistanceResource
	 */
	@Path("distanceId/")
	public service.DistanceResource getDistanceIdResource() {
		DistanceIdResourceSub resource = resourceContext.getResource(DistanceIdResourceSub.class);
		resource.setParent(getEntity());
		return resource;
	}

	/**
	 * Returns a dynamic instance of IvUserResource used for entity navigation.
	 * 
	 * @param id
	 *            identifier for the parent entity
	 * @return an instance of IvUserResource
	 */
	@Path("userId/")
	public IvUserResource getUserIdResource() {
		UserIdResourceSub resource = resourceContext.getResource(UserIdResourceSub.class);
		resource.setParent(getEntity());
		return resource;
	}

	/**
	 * Returns a dynamic instance of TimeframeResource used for entity
	 * navigation.
	 * 
	 * @param id
	 *            identifier for the parent entity
	 * @return an instance of TimeframeResource
	 */
	@Path("timeframeId/")
	public TimeframeResource getTimeframeIdResource() {
		TimeframeIdResourceSub resource = resourceContext.getResource(TimeframeIdResourceSub.class);
		resource.setParent(getEntity());
		return resource;
	}

	public static class OrganizationTypeCollectionResourceSub extends OrganizationTypesResource {

		private Filter	parent;

		public void setParent(Filter parent) {
			this.parent = parent;
		}

		@Override
		protected Collection<OrganizationType> getEntities(int start, int max, String query) {
			Collection<OrganizationType> result = new java.util.ArrayList<OrganizationType>();
			int index = 0;
			for (OrganizationType e : parent.getOrganizationTypeCollection()) {
				if (index >= start && (index - start) < max) {
					result.add(e);
				}
				index++;
			}
			return result;
		}
	}

	public static class InterestAreaCollectionResourceSub extends InterestAreasResource {

		private Filter	parent;

		public void setParent(Filter parent) {
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

	public static class DistanceIdResourceSub extends DistanceResource {

		private Filter	parent;

		public void setParent(Filter parent) {
			this.parent = parent;
		}

		@Override
		protected Distance getEntity() {
			Distance entity = parent.getDistanceId();
			if (entity == null) {
				throw new WebApplicationException(new Throwable("Resource for "
						+ uriInfo.getAbsolutePath() + " does not exist."), 404);
			}
			return entity;
		}
	}

	public static class UserIdResourceSub extends IvUserResource {

		private Filter	parent;

		public void setParent(Filter parent) {
			this.parent = parent;
		}

		@Override
		protected IvUser getEntity() {
			IvUser entity = parent.getUserId();
			if (entity == null) {
				throw new WebApplicationException(new Throwable("Resource for "
						+ uriInfo.getAbsolutePath() + " does not exist."), 404);
			}
			return entity;
		}
	}

	public static class TimeframeIdResourceSub extends TimeframeResource {

		private Filter	parent;

		public void setParent(Filter parent) {
			this.parent = parent;
		}

		@Override
		protected Timeframe getEntity() {
			Timeframe entity = parent.getTimeframeId();
			if (entity == null) {
				throw new WebApplicationException(new Throwable("Resource for "
						+ uriInfo.getAbsolutePath() + " does not exist."), 404);
			}
			return entity;
		}
	}
}
