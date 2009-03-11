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
import persistence.Event;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.ws.rs.core.UriBuilder;
import javax.persistence.EntityManager;
import persistence.Source;
import persistence.InterestArea;
import java.util.Collection;
import java.util.HashSet;
import persistence.Organization;
import persistence.Location;
import persistence.Timestamp;

/**
 * 
 * @author Dave Angulo
 */

@XmlRootElement(name = "event")
public class EventRecordConverter {
	private Event	entity;
	private URI		uri;
	private int		expandLevel;

	/** Creates a new instance of EventConverter */
	public EventRecordConverter() {
		entity = new Event();
	}

	/**
	 * Creates a new instance of EventConverter.
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
	public EventRecordConverter(Event entity, URI uri, int expandLevel, boolean isUriExtendable) {
		this.entity = entity;
		this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getId() + "/").build()
				: uri;
		this.expandLevel = expandLevel;
		getInterestAreaCollection();
		getTimestampCollection();
		getLocationCollection();
		getOrganizationCollection();
		getSourceId();
	}

	/**
	 * Creates a new instance of EventConverter.
	 * 
	 * @param entity
	 *            associated entity
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public EventRecordConverter(Event entity, URI uri, int expandLevel) {
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
	 * Getter for title.
	 * 
	 * @return value for title
	 */
	@XmlElement
	public String getTitle() {
		return (expandLevel > 0) ? entity.getTitle() : null;
	}

	/**
	 * Setter for title.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setTitle(String value) {
		entity.setTitle(value);
	}

	/**
	 * Getter for description.
	 * 
	 * @return value for description
	 */
	@XmlElement
	public String getDescription() {
		return (expandLevel > 0) ? entity.getDescription() : null;
	}

	/**
	 * Setter for description.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setDescription(String value) {
		entity.setDescription(value);
	}

	/**
	 * Getter for duration.
	 * 
	 * @return value for duration
	 */
	@XmlElement
	public Short getDuration() {
		return (expandLevel > 0) ? entity.getDuration() : null;
	}

	/**
	 * Setter for duration.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setDuration(Short value) {
		entity.setDuration(value);
	}

	/**
	 * Getter for contact.
	 * 
	 * @return value for contact
	 */
	@XmlElement
	public String getContact() {
		return (expandLevel > 0) ? entity.getContact() : null;
	}

	/**
	 * Setter for contact.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setContact(String value) {
		entity.setContact(value);
	}

	/**
	 * Getter for url.
	 * 
	 * @return value for url
	 */
	@XmlElement
	public String getUrl() {
		return (expandLevel > 0) ? entity.getUrl() : null;
	}

	/**
	 * Setter for url.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setUrl(String value) {
		entity.setUrl(value);
	}

	/**
	 * Getter for phone.
	 * 
	 * @return value for phone
	 */
	@XmlElement
	public String getPhone() {
		return (expandLevel > 0) ? entity.getPhone() : null;
	}

	/**
	 * Setter for phone.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setPhone(String value) {
		entity.setPhone(value);
	}

	/**
	 * Getter for email.
	 * 
	 * @return value for email
	 */
	@XmlElement
	public String getEmail() {
		return (expandLevel > 0) ? entity.getEmail() : null;
	}

	/**
	 * Setter for email.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setEmail(String value) {
		entity.setEmail(value);
	}

	/**
	 * Getter for sourceKey.
	 * 
	 * @return value for sourceKey
	 */
	@XmlElement
	public String getSourceKey() {
		return (expandLevel > 0) ? entity.getSourceKey() : null;
	}

	/**
	 * Setter for sourceKey.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setSourceKey(String value) {
		entity.setSourceKey(value);
	}

	/**
	 * Getter for sourceUrl.
	 * 
	 * @return value for sourceUrl
	 */
	@XmlElement
	public String getSourceUrl() {
		return (expandLevel > 0) ? entity.getSourceUrl() : null;
	}

	/**
	 * Setter for sourceUrl.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setSourceUrl(String value) {
		entity.setSourceUrl(value);
	}

	/**
	 * Getter for interestAreaCollection.
	 * 
	 * @return value for interestAreaCollection
	 */
	@XmlElement
	public Collection<String> getInterestAreaCollection() {
		if (expandLevel > 0) {
			if (entity.getInterestAreaCollection() != null) {
				Collection<String> ret = new HashSet<String>();
				for (InterestArea interestArea : entity.getInterestAreaCollection()) {
					ret.add(interestArea.getId());
				}
				return ret;
			}
		}
		return null;
	}

	/**
	 * Setter for interestAreaCollection.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setInterestAreaCollection(InterestAreasConverter value) {
		entity.setInterestAreaCollection((value != null) ? value.getEntities() : null);
	}

	/**
	 * Getter for timestampCollection.
	 * 
	 * @return value for timestampCollection
	 */
	@XmlElement
	public Collection<String> getTimestampCollection() {
		if (expandLevel > 0) {
			if (entity.getTimestampCollection() != null) {
				Collection<String> ret = new HashSet<String>();
				for (Timestamp timestamp : entity.getTimestampCollection()) {
					ret.add(timestamp.getId());
				}
				return ret;
			}
		}
		return null;
	}

	/**
	 * Setter for timestampCollection.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setTimestampCollection(TimestampsConverter value) {
		entity.setTimestampCollection((value != null) ? value.getEntities() : null);
	}

	/**
	 * Getter for locationCollection.
	 * 
	 * @return value for locationCollection
	 */
	@XmlElement
	public Collection<String> getLocationCollection() {
		if (expandLevel > 0) {
			if (entity.getLocationCollection() != null) {
				Collection<String> ret = new HashSet<String>();
				for (Location loc : entity.getLocationCollection()) {
					ret.add(loc.getId());
				}
				return ret;
			}
		}
		return null;
	}

	/**
	 * Setter for locationCollection.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setLocationCollection(LocationsConverter value) {
		entity.setLocationCollection((value != null) ? value.getEntities() : null);
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
	 * Getter for sourceId.
	 * 
	 * @return value for sourceId
	 */
	@XmlElement
	public SourceConverter getSourceId() {
		if (expandLevel > 0) {
			if (entity.getSourceId() != null) {
				return new SourceConverter(entity.getSourceId(), uri.resolve("sourceId/"),
						expandLevel - 1, false);
			}
		}
		return null;
	}

	/**
	 * Setter for sourceId.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setSourceId(SourceConverter value) {
		entity.setSourceId((value != null) ? value.getEntity() : null);
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
	 * Returns the Event entity.
	 * 
	 * @return an entity
	 */
	@XmlTransient
	public Event getEntity() {
		if (entity.getId() == null) {
			EventRecordConverter converter = UriResolver.getInstance().resolve(
					EventRecordConverter.class, uri);
			if (converter != null) {
				entity = converter.getEntity();
			}
		}
		return entity;
	}

	/**
	 * Returns the resolved Event entity.
	 * 
	 * @return an resolved entity
	 */
	public Event resolveEntity(EntityManager em) {
		Collection<InterestArea> interestAreaCollection = entity.getInterestAreaCollection();
		Collection<InterestArea> newinterestAreaCollection = new java.util.ArrayList<InterestArea>();
		for (InterestArea item : interestAreaCollection) {
			newinterestAreaCollection.add(em.getReference(InterestArea.class, item.getId()));
		}
		entity.setInterestAreaCollection(newinterestAreaCollection);
		Collection<Timestamp> timestampCollection = entity.getTimestampCollection();
		Collection<Timestamp> newtimestampCollection = new java.util.ArrayList<Timestamp>();
		for (Timestamp item : timestampCollection) {
			newtimestampCollection.add(em.getReference(Timestamp.class, item.getId()));
		}
		entity.setTimestampCollection(newtimestampCollection);
		Collection<Location> locationCollection = entity.getLocationCollection();
		Collection<Location> newlocationCollection = new java.util.ArrayList<Location>();
		for (Location item : locationCollection) {
			newlocationCollection.add(em.getReference(Location.class, item.getId()));
		}
		entity.setLocationCollection(newlocationCollection);
		Collection<Organization> organizationCollection = entity.getOrganizationCollection();
		Collection<Organization> neworganizationCollection = new java.util.ArrayList<Organization>();
		for (Organization item : organizationCollection) {
			neworganizationCollection.add(em.getReference(Organization.class, item.getId()));
		}
		entity.setOrganizationCollection(neworganizationCollection);
		Source sourceId = entity.getSourceId();
		if (sourceId != null) {
			entity.setSourceId(em.getReference(Source.class, sourceId.getId()));
		}
		return entity;
	}
}
