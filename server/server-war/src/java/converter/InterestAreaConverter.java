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

package converter;

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
import persistence.SourceInterestMap;

/**
 * 
 * @author Dave Angulo
 */

@XmlRootElement(name = "interestArea")
public class InterestAreaConverter {
	private InterestArea	entity;
	private URI				uri;
	private int				expandLevel;

	/** Creates a new instance of InterestAreaConverter */
	public InterestAreaConverter() {
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
	public InterestAreaConverter(InterestArea entity, URI uri, int expandLevel,
			boolean isUriExtendable) {
		this.entity = entity;
		this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getId() + "/").build()
				: uri;
		this.expandLevel = expandLevel;
		getEventCollection();
		getOrganizationCollection();
		getFilterCollection();
		getSourceInterestMapCollection();
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
	public InterestAreaConverter(InterestArea entity, URI uri, int expandLevel) {
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
	 * Getter for eventCollection.
	 * 
	 * @return value for eventCollection
	 */
	@XmlElement
	public EventsConverter getEventCollection() {
		if (expandLevel > 0) {
			if (entity.getEventCollection() != null) {
				return new EventsConverter(entity.getEventCollection(), uri
						.resolve("eventCollection/"), expandLevel - 1);
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
	 * Getter for organizationCollection.
	 * 
	 * @return value for organizationCollection
	 */
	@XmlElement
	public OrganizationsConverter getOrganizationCollection() {
		if (expandLevel > 0) {
			if (entity.getOrganizationCollection() != null) {
				return new OrganizationsConverter(entity.getOrganizationCollection(), uri
						.resolve("organizationCollection/"), expandLevel - 1);
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
	 * Getter for filterCollection.
	 * 
	 * @return value for filterCollection
	 */
	@XmlElement
	public FiltersConverter getFilterCollection() {
		if (expandLevel > 0) {
			if (entity.getFilterCollection() != null) {
				return new FiltersConverter(entity.getFilterCollection(), uri
						.resolve("filterCollection/"), expandLevel - 1);
			}
		}
		return null;
	}

	/**
	 * Setter for filterCollection.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setFilterCollection(FiltersConverter value) {
		entity.setFilterCollection((value != null) ? value.getEntities() : null);
	}

	/**
	 * Getter for sourceInterestMapCollection.
	 * 
	 * @return value for sourceInterestMapCollection
	 */
	@XmlElement
	public SourceInterestMapsConverter getSourceInterestMapCollection() {
		if (expandLevel > 0) {
			if (entity.getSourceInterestMapCollection() != null) {
				return new SourceInterestMapsConverter(entity.getSourceInterestMapCollection(), uri
						.resolve("sourceInterestMapCollection/"), expandLevel - 1);
			}
		}
		return null;
	}

	/**
	 * Setter for sourceInterestMapCollection.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setSourceInterestMapCollection(SourceInterestMapsConverter value) {
		entity.setSourceInterestMapCollection((value != null) ? value.getEntities() : null);
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
			InterestAreaConverter converter = UriResolver.getInstance().resolve(
					InterestAreaConverter.class, uri);
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
		entity.setFilterCollection(newfilterCollection);
		Collection<SourceInterestMap> sourceInterestMapCollection = entity
				.getSourceInterestMapCollection();
		Collection<SourceInterestMap> newsourceInterestMapCollection = new java.util.ArrayList<SourceInterestMap>();
		for (SourceInterestMap item : sourceInterestMapCollection) {
			newsourceInterestMapCollection.add(em.getReference(SourceInterestMap.class, item
					.getId()));
		}
		entity.setSourceInterestMapCollection(newsourceInterestMapCollection);
		return entity;
	}
}
