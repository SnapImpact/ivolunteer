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
import persistence.Organization;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.ws.rs.core.UriBuilder;
import javax.persistence.EntityManager;
import persistence.Source;
import persistence.InterestArea;
import persistence.Event;
import java.util.Collection;
import java.util.HashSet;
import persistence.OrganizationType;
import persistence.Location;

/**
 * 
 * @author Dave Angulo
 */

@XmlRootElement(name = "organization")
public class OrganizationRecordConverter {
	private Organization	entity;
	private URI				uri;
	private int				expandLevel;

	/** Creates a new instance of OrganizationConverter */
	public OrganizationRecordConverter() {
		entity = new Organization();
	}

	/**
	 * Creates a new instance of OrganizationConverter.
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
	public OrganizationRecordConverter(Organization entity, URI uri, int expandLevel,
			boolean isUriExtendable) {
		this.entity = entity;
		this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getId() + "/").build()
				: uri;
		this.expandLevel = expandLevel;
		getLocationCollection();
		getInterestAreaCollection();
		getEventCollection();
		getOrganizationTypeId();
		getSourceId();
	}

	/**
	 * Creates a new instance of OrganizationConverter.
	 * 
	 * @param entity
	 *            associated entity
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public OrganizationRecordConverter(Organization entity, URI uri, int expandLevel) {
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
	 * Getter for organizationTypeId.
	 * 
	 * @return value for organizationTypeId
	 */
	@XmlElement
	public String getOrganizationTypeId() {
		if (expandLevel > 0) {
			if (entity.getOrganizationTypeId() != null) {
				return entity.getOrganizationTypeId().getId();
			}
		}
		return null;
	}

	/**
	 * Setter for organizationTypeId.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setOrganizationTypeId(OrganizationTypeConverter value) {
		entity.setOrganizationTypeId((value != null) ? value.getEntity() : null);
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
	 * Returns the Organization entity.
	 * 
	 * @return an entity
	 */
	@XmlTransient
	public Organization getEntity() {
		if (entity.getId() == null) {
			OrganizationRecordConverter converter = UriResolver.getInstance().resolve(
					OrganizationRecordConverter.class, uri);
			if (converter != null) {
				entity = converter.getEntity();
			}
		}
		return entity;
	}

	/**
	 * Returns the resolved Organization entity.
	 * 
	 * @return an resolved entity
	 */
	public Organization resolveEntity(EntityManager em) {
		Collection<Location> locationCollection = entity.getLocationCollection();
		Collection<Location> newlocationCollection = new java.util.ArrayList<Location>();
		for (Location item : locationCollection) {
			newlocationCollection.add(em.getReference(Location.class, item.getId()));
		}
		entity.setLocationCollection(newlocationCollection);
		Collection<InterestArea> interestAreaCollection = entity.getInterestAreaCollection();
		Collection<InterestArea> newinterestAreaCollection = new java.util.ArrayList<InterestArea>();
		for (InterestArea item : interestAreaCollection) {
			newinterestAreaCollection.add(em.getReference(InterestArea.class, item.getId()));
		}
		entity.setInterestAreaCollection(newinterestAreaCollection);
		Collection<Event> eventCollection = entity.getEventCollection();
		Collection<Event> neweventCollection = new java.util.ArrayList<Event>();
		for (Event item : eventCollection) {
			neweventCollection.add(em.getReference(Event.class, item.getId()));
		}
		entity.setEventCollection(neweventCollection);
		OrganizationType organizationTypeId = entity.getOrganizationTypeId();
		if (organizationTypeId != null) {
			entity.setOrganizationTypeId(em.getReference(OrganizationType.class, organizationTypeId
					.getId()));
		}
		Source sourceId = entity.getSourceId();
		if (sourceId != null) {
			entity.setSourceId(em.getReference(Source.class, sourceId.getId()));
		}
		return entity;
	}
}
