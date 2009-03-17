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
import persistence.Location;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.ws.rs.core.UriBuilder;
import javax.persistence.EntityManager;
import persistence.Event;
import java.util.Collection;
import java.util.HashSet;
import persistence.Organization;

/**
 * 
 * @author Dave Angulo
 */

@XmlRootElement(name = "location")
public class LocationRecordConverter {
	private Location	entity;
	private URI			uri;
	private int			expandLevel;

	/** Creates a new instance of LocationConverter */
	public LocationRecordConverter() {
		entity = new Location();
	}

	/**
	 * Creates a new instance of LocationConverter.
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
	public LocationRecordConverter(Location entity, URI uri, int expandLevel,
			boolean isUriExtendable) {
		this.entity = entity;
		this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getId() + "/").build()
				: uri;
		this.expandLevel = expandLevel;
		getOrganizationCollection();
		getEventCollection();
	}

	/**
	 * Creates a new instance of LocationConverter.
	 * 
	 * @param entity
	 *            associated entity
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public LocationRecordConverter(Location entity, URI uri, int expandLevel) {
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
	 * Getter for street.
	 * 
	 * @return value for street
	 */
	@XmlElement
	public String getStreet() {
		return (expandLevel > 0) ? entity.getStreet() : null;
	}

	/**
	 * Setter for street.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setStreet(String value) {
		entity.setStreet(value);
	}

	/**
	 * Getter for city.
	 * 
	 * @return value for city
	 */
	@XmlElement
	public String getCity() {
		return (expandLevel > 0) ? entity.getCity() : null;
	}

	/**
	 * Setter for city.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setCity(String value) {
		entity.setCity(value);
	}

	/**
	 * Getter for state.
	 * 
	 * @return value for state
	 */
	@XmlElement
	public String getState() {
		return (expandLevel > 0) ? entity.getState() : null;
	}

	/**
	 * Setter for state.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setState(String value) {
		entity.setState(value);
	}

	/**
	 * Getter for zip.
	 * 
	 * @return value for zip
	 */
	@XmlElement
	public String getZip() {
		return (expandLevel > 0) ? entity.getZip() : null;
	}

	/**
	 * Setter for zip.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setZip(String value) {
		entity.setZip(value);
	}

	/**
	 * Getter for location.
	 * 
	 * @return value for location
	 */
	@XmlElement
	public String getLocation() {
		return (expandLevel > 0) ? entity.getLocation() : null;
	}

	/**
	 * Setter for location.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setLocation(String value) {
		entity.setLocation(value);
	}

	/**
	 * Getter for latitude.
	 * 
	 * @return value for latitude
	 */
	@XmlElement
	public String getLatitude() {
		return (expandLevel > 0) ? entity.getLatitude() : null;
	}

	/**
	 * Setter for latitude.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setLatitude(String value) {
		entity.setLatitude(value);
	}

	/**
	 * Getter for longitude.
	 * 
	 * @return value for longitude
	 */
	@XmlElement
	public String getLongitude() {
		return (expandLevel > 0) ? entity.getLongitude() : null;
	}

	/**
	 * Setter for longitude.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setLongitude(String value) {
		entity.setLongitude(value);
	}

	/**
	 * Getter for organizationCollection.
	 * 
	 * @return value for organizationCollection
	 */
	@XmlElement
	public Collection<String> getOrganizationCollection() {
		if (expandLevel > 0) {
			if (entity.getOrganizationCollection() != null) {
				Collection<String> ret = new HashSet<String>();
				for (Organization org : entity.getOrganizationCollection()) {
					ret.add(org.getId());
				}
				return ret;
			}
		}
		return null;
	}

	/**
	 * Setter for organizationCollection.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setOrganizationCollection(OrganizationsConverter value) {
		entity.setOrganizationCollection((value != null) ? value.getEntities() : null);
	}

	/**
	 * Getter for eventCollection.
	 * 
	 * @return value for eventCollection
	 */
	@XmlElement
	public Collection<String> getEventCollection() {
		if (expandLevel > 0) {
			if (entity.getEventCollection() != null) {
				Collection<String> ret = new HashSet<String>();
				for (Event event : entity.getEventCollection()) {
					ret.add(event.getId());
				}
				return ret;
			}
		}
		return null;
	}

	/**
	 * Setter for eventCollection.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setEventCollection(EventsConverter value) {
		entity.setEventCollection((value != null) ? value.getEntities() : null);
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
	 * Returns the Location entity.
	 * 
	 * @return an entity
	 */
	@XmlTransient
	public Location getEntity() {
		if (entity.getId() == null) {
			LocationRecordConverter converter = UriResolver.getInstance().resolve(
					LocationRecordConverter.class, uri);
			if (converter != null) {
				entity = converter.getEntity();
			}
		}
		return entity;
	}

	/**
	 * Returns the resolved Location entity.
	 * 
	 * @return an resolved entity
	 */
	public Location resolveEntity(EntityManager em) {
		Collection<Organization> organizationCollection = entity.getOrganizationCollection();
		Collection<Organization> neworganizationCollection = new java.util.ArrayList<Organization>();
		for (Organization item : organizationCollection) {
			neworganizationCollection.add(em.getReference(Organization.class, item.getId()));
		}
		entity.setOrganizationCollection(neworganizationCollection);
		Collection<Event> eventCollection = entity.getEventCollection();
		Collection<Event> neweventCollection = new java.util.ArrayList<Event>();
		for (Event item : eventCollection) {
			neweventCollection.add(em.getReference(Event.class, item.getId()));
		}
		entity.setEventCollection(neweventCollection);
		return entity;
	}
}
