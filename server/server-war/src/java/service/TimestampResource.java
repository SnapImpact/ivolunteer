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

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
import converter.TimestampConverter;
import persistence.Timestamp;
import session.TimestampFacadeLocal;

/**
 * 
 * @author Dave Angulo
 */

public class TimestampResource {
	@Context
	protected UriInfo			uriInfo;
	@Context
	protected ResourceContext	resourceContext;
	protected String			id;

	/** Creates a new instance of TimestampResource */
	public TimestampResource() {
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get method for retrieving an instance of Timestamp identified by id in
	 * XML format.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @return an instance of TimestampConverter
	 */
	@GET
	@Produces( { "application/xml", "application/json" })
	public TimestampConverter get(@QueryParam("expandLevel") @DefaultValue("1") int expandLevel) {
		try {
			return new TimestampConverter(getEntity(), uriInfo.getAbsolutePath(), expandLevel);
		} finally {
		}
	}

	/**
	 * Put method for updating an instance of Timestamp identified by id using
	 * XML as the input format.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @param data
	 *            an TimestampConverter entity that is deserialized from a XML
	 *            stream
	 */
	@PUT
	@Consumes( { "application/xml", "application/json" })
	public void put(TimestampConverter data) {
		try {
			lookupTimestampFacade().edit(data.getEntity());
		} finally {
		}
	}

	/**
	 * Delete method for deleting an instance of Timestamp identified by id.
	 * 
	 * @param id
	 *            identifier for the entity
	 */
	@DELETE
	public void delete() {
		try {
			deleteEntity(getEntity());
		} finally {
		}
	}

	/**
	 * Returns an instance of Timestamp identified by id.
	 * 
	 * @param id
	 *            identifier for the entity
	 * @return an instance of Timestamp
	 */
	protected Timestamp getEntity() {
		try {
			return lookupTimestampFacade().find(id);
		} catch (NoResultException ex) {
			throw new WebApplicationException(new Throwable("Resource for "
					+ uriInfo.getAbsolutePath() + " does not exist."), 404);
		}
	}

	/**
	 * Deletes the entity.
	 * 
	 * @param entity
	 *            the entity to deletle
	 */
	protected void deleteEntity(Timestamp entity) {
		lookupTimestampFacade().remove(entity);
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

	public static class EventCollectionResourceSub extends EventsResource {

		private Timestamp	parent;

		public void setParent(Timestamp parent) {
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

	private TimestampFacadeLocal lookupTimestampFacade() {
		try {
			javax.naming.Context c = new InitialContext();
			return (TimestampFacadeLocal) c.lookup("java:comp/env/TimestampFacade");
		} catch (NamingException ne) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
			throw new RuntimeException(ne);
		}
	}
}
