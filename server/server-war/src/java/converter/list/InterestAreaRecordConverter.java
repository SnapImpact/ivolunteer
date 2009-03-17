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

package converter.list;

import converter.*;
import java.net.URI;
import persistence.InterestArea;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.ws.rs.core.UriBuilder;
import javax.persistence.EntityManager;
import persistence.Event;
import java.util.Collection;
import persistence.Organization;
import persistence.Filter;

/**
 * 
 * @author Dave Angulo
 */

@XmlRootElement(name = "interestArea")
public class InterestAreaRecordConverter {
	private InterestArea	entity;
	private URI				uri;
	private int				expandLevel;

	/** Creates a new instance of InterestAreaConverter */
	public InterestAreaRecordConverter() {
		entity = new InterestArea();
	}

	/**
	 * Creates a new instance of InterestAreaConverter.
	 * 
	 * @param entity
	 *            associated entity
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded@param isUriExtendable indicates whether the uri can
	 *            be extended
	 */
	public InterestAreaRecordConverter(InterestArea entity, URI uri, int expandLevel,
			boolean isUriExtendable) {
		this.entity = entity;
		this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getId() + "/").build()
				: uri;
		this.expandLevel = expandLevel;
	}

	/**
	 * Creates a new instance of InterestAreaConverter.
	 * 
	 * @param entity
	 *            associated entity
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public InterestAreaRecordConverter(InterestArea entity, URI uri, int expandLevel) {
		this(entity, uri, expandLevel, false);
	}

	/**
	 * Getter for id.
	 * 
	 * @return value for id
	 */
	@XmlElement
	public String getId() {
		return (expandLevel > 0) ? entity.getId() : null;
	}

	/**
	 * Setter for id.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setId(String value) {
		entity.setId(value);
	}

	/**
	 * Getter for name.
	 * 
	 * @return value for name
	 */
	@XmlElement
	public String getName() {
		return (expandLevel > 0) ? entity.getName() : null;
	}

	/**
	 * Setter for name.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setName(String value) {
		entity.setName(value);
	}

	/**
	 * Returns the URI associated with this converter.
	 * 
	 * @return the uri
	 */
	@XmlAttribute
	public URI getUri() {
		return uri;
	}

	/**
	 * Sets the URI for this reference converter.
	 * 
	 */
	public void setUri(URI uri) {
		this.uri = uri;
	}

	/**
	 * Returns the InterestArea entity.
	 * 
	 * @return an entity
	 */
	@XmlTransient
	public InterestArea getEntity() {
		if (entity.getId() == null) {
			InterestAreaRecordConverter converter = UriResolver.getInstance().resolve(
					InterestAreaRecordConverter.class, uri);
			if (converter != null) {
				entity = converter.getEntity();
			}
		}
		return entity;
	}

	/**
	 * Returns the resolved InterestArea entity.
	 * 
	 * @return an resolved entity
	 */
	public InterestArea resolveEntity(EntityManager em) {
		Collection<Event> eventCollection = entity.getEventCollection();
		Collection<Event> neweventCollection = new java.util.ArrayList<Event>();
		for (Event item : eventCollection) {
			neweventCollection.add(em.getReference(Event.class, item.getId()));
		}
		entity.setEventCollection(neweventCollection);
		Collection<Organization> organizationCollection = entity.getOrganizationCollection();
		Collection<Organization> neworganizationCollection = new java.util.ArrayList<Organization>();
		for (Organization item : organizationCollection) {
			neworganizationCollection.add(em.getReference(Organization.class, item.getId()));
		}
		entity.setOrganizationCollection(neworganizationCollection);
		Collection<Filter> filterCollection = entity.getFilterCollection();
		Collection<Filter> newfilterCollection = new java.util.ArrayList<Filter>();
		for (Filter item : filterCollection) {
			newfilterCollection.add(em.getReference(Filter.class, item.getId()));
		}
		return entity;
	}
}
