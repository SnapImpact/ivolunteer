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
import persistence.Source;
import persistence.OrganizationType;
import converter.SourceOrgTypeMapConverter;
import persistence.SourceOrgTypeMap;

/**
 * 
 * @author Dave Angulo
 */

public class SourceOrgTypeMapResource {
	@Context
	protected UriInfo			uriInfo;
	@Context
	protected ResourceContext	resourceContext;
	protected String			id;

	/** Creates a new instance of SourceOrgTypeMapResource */
	public SourceOrgTypeMapResource() {
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get method for retrieving an instance of SourceOrgTypeMap identified by
	 * id in XML format.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @return an instance of SourceOrgTypeMapConverter
	 */
	@GET
	@Produces( { "application/xml", "application/json" })
	public SourceOrgTypeMapConverter get(
			@QueryParam("expandLevel") @DefaultValue("1") int expandLevel) {
		PersistenceService persistenceSvc = PersistenceService.getInstance();
		try {
			persistenceSvc.beginTx();
			return new SourceOrgTypeMapConverter(getEntity(), uriInfo.getAbsolutePath(),
					expandLevel);
		} finally {
			PersistenceService.getInstance().close();
		}
	}

	/**
	 * Put method for updating an instance of SourceOrgTypeMap identified by id
	 * using XML as the input format.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @param data
	 *            an SourceOrgTypeMapConverter entity that is deserialized from
	 *            a XML stream
	 */
	@PUT
	@Consumes( { "application/xml", "application/json" })
	public void put(SourceOrgTypeMapConverter data) {
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
	 * Delete method for deleting an instance of SourceOrgTypeMap identified by
	 * id.
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
	 * Returns an instance of SourceOrgTypeMap identified by id.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @return an instance of SourceOrgTypeMap
	 */
	protected SourceOrgTypeMap getEntity() {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		try {
			return (SourceOrgTypeMap) em.createQuery(
					"SELECT e FROM SourceOrgTypeMap e where e.id = :id").setParameter("id", id)
					.getSingleResult();
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
	protected SourceOrgTypeMap updateEntity(SourceOrgTypeMap entity, SourceOrgTypeMap newEntity) {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		OrganizationType organizationTypeId = entity.getOrganizationTypeId();
		OrganizationType organizationTypeIdNew = newEntity.getOrganizationTypeId();
		Source sourceId = entity.getSourceId();
		Source sourceIdNew = newEntity.getSourceId();
		entity = em.merge(newEntity);
		if (organizationTypeId != null && !organizationTypeId.equals(organizationTypeIdNew)) {
			organizationTypeId.getSourceOrgTypeMapCollection().remove(entity);
		}
		if (organizationTypeIdNew != null && !organizationTypeIdNew.equals(organizationTypeId)) {
			organizationTypeIdNew.getSourceOrgTypeMapCollection().add(entity);
		}
		if (sourceId != null && !sourceId.equals(sourceIdNew)) {
			sourceId.getSourceOrgTypeMapCollection().remove(entity);
		}
		if (sourceIdNew != null && !sourceIdNew.equals(sourceId)) {
			sourceIdNew.getSourceOrgTypeMapCollection().add(entity);
		}
		return entity;
	}

	/**
	 * Deletes the entity.
	 * 
	 * @param entity
	 *            the entity to deletle
	 */
	protected void deleteEntity(SourceOrgTypeMap entity) {
		EntityManager em = PersistenceService.getInstance().getEntityManager();
		OrganizationType organizationTypeId = entity.getOrganizationTypeId();
		if (organizationTypeId != null) {
			organizationTypeId.getSourceOrgTypeMapCollection().remove(entity);
		}
		Source sourceId = entity.getSourceId();
		if (sourceId != null) {
			sourceId.getSourceOrgTypeMapCollection().remove(entity);
		}
		em.remove(entity);
	}

	/**
	 * Returns a dynamic instance of OrganizationTypeResource used for entity
	 * navigation.
	 * 
	 * @param id
	 *            identifier for the parent entity
	 * @return an instance of OrganizationTypeResource
	 */
	@Path("organizationTypeId/")
	public OrganizationTypeResource getOrganizationTypeIdResource() {
		OrganizationTypeIdResourceSub resource = resourceContext
				.getResource(OrganizationTypeIdResourceSub.class);
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

	public static class OrganizationTypeIdResourceSub extends OrganizationTypeResource {

		private SourceOrgTypeMap	parent;

		public void setParent(SourceOrgTypeMap parent) {
			this.parent = parent;
		}

		@Override
		protected OrganizationType getEntity() {
			OrganizationType entity = parent.getOrganizationTypeId();
			if (entity == null) {
				throw new WebApplicationException(new Throwable("Resource for "
						+ uriInfo.getAbsolutePath() + " does not exist."), 404);
			}
			return entity;
		}
	}

	public static class SourceIdResourceSub extends SourceResource {

		private SourceOrgTypeMap	parent;

		public void setParent(SourceOrgTypeMap parent) {
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
