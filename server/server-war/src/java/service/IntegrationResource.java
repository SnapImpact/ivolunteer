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
import persistence.Integration;
import persistence.Network;
import persistence.IvUser;
import converter.IntegrationConverter;

/**
 * 
 * @author Dave Angulo
 */

public class IntegrationResource {
	@Context
	protected UriInfo			uriInfo;
	@Context
	protected ResourceContext	resourceContext;
	protected String			id;

	/** Creates a new instance of IntegrationResource */
	public IntegrationResource() {
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get method for retrieving an instance of Integration identified by id in
	 * XML format.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @return an instance of IntegrationConverter
	 */
	@GET
	@Produces( { "application/xml", "application/json" })
	public IntegrationConverter get(@QueryParam("expandLevel") @DefaultValue("1") int expandLevel) {
		PersistenceService persistenceSvc = PersistenceService.getInstance();
		try {
			persistenceSvc.beginTx();
			return new IntegrationConverter(getEntity(), uriInfo.getAbsolutePath(), expandLevel);
		} finally {
			PersistenceService.getInstance().close();
		}
	}

	/**
	 * Put method for updating an instance of Integration identified by id using
	 * XML as the input format.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @param data
	 *            an IntegrationConverter entity that is deserialized from a XML
	 *            stream
	 */
	@PUT
	@Consumes( { "application/xml", "application/json" })
	public void put(IntegrationConverter data) {
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
	 * Delete method for deleting an instance of Integration identified by id.
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
	 * Returns an instance of Integration identified by id.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @return an instance of Integration
	 */
	protected Integration getEntity() {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		try {
			return (Integration) em.createQuery("SELECT e FROM Integration e where e.id = :id")
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
	protected Integration updateEntity(Integration entity, Integration newEntity) {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		IvUser userId = entity.getUserId();
		IvUser userIdNew = newEntity.getUserId();
		Network networkId = entity.getNetworkId();
		Network networkIdNew = newEntity.getNetworkId();
		entity = em.merge(newEntity);
		if (userId != null && !userId.equals(userIdNew)) {
			userId.getIntegrationCollection().remove(entity);
		}
		if (userIdNew != null && !userIdNew.equals(userId)) {
			userIdNew.getIntegrationCollection().add(entity);
		}
		if (networkId != null && !networkId.equals(networkIdNew)) {
			networkId.getIntegrationCollection().remove(entity);
		}
		if (networkIdNew != null && !networkIdNew.equals(networkId)) {
			networkIdNew.getIntegrationCollection().add(entity);
		}
		return entity;
	}

	/**
	 * Deletes the entity.
	 * 
	 * @param entity
	 *            the entity to deletle
	 */
	protected void deleteEntity(Integration entity) {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		IvUser userId = entity.getUserId();
		if (userId != null) {
			userId.getIntegrationCollection().remove(entity);
		}
		Network networkId = entity.getNetworkId();
		if (networkId != null) {
			networkId.getIntegrationCollection().remove(entity);
		}
		em.remove(entity);
	}

	/**
	 * Returns a dynamic instance of IvUserResource used for entity navigation.
	 * 
	 * @param id
	 *            identifier for the parent entity
	 * @return an instance of IvUserResource
	 */
	@Path("userId/")
	public service.IvUserResource getUserIdResource() {
		UserIdResourceSub resource = resourceContext.getResource(UserIdResourceSub.class);
		resource.setParent(getEntity());
		return resource;
	}

	/**
	 * Returns a dynamic instance of NetworkResource used for entity navigation.
	 * 
	 * @param id
	 *            identifier for the parent entity
	 * @return an instance of NetworkResource
	 */
	@Path("networkId/")
	public service.NetworkResource getNetworkIdResource() {
		NetworkIdResourceSub resource = resourceContext.getResource(NetworkIdResourceSub.class);
		resource.setParent(getEntity());
		return resource;
	}

	public static class UserIdResourceSub extends IvUserResource {

		private Integration	parent;

		public void setParent(Integration parent) {
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

	public static class NetworkIdResourceSub extends NetworkResource {

		private Integration	parent;

		public void setParent(Integration parent) {
			this.parent = parent;
		}

		@Override
		protected Network getEntity() {
			Network entity = parent.getNetworkId();
			if (entity == null) {
				throw new WebApplicationException(new Throwable("Resource for "
						+ uriInfo.getAbsolutePath() + " does not exist."), 404);
			}
			return entity;
		}
	}
}
