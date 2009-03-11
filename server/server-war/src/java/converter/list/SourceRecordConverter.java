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
import persistence.Source;
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
import persistence.SourceOrgTypeMap;
import persistence.SourceInterestMap;

/**
 * 
 * @author Dave Angulo
 */

@XmlRootElement(name = "source")
public class SourceRecordConverter {
	private Source	entity;
	private URI		uri;
	private int		expandLevel;

	/** Creates a new instance of SourceConverter */
	public SourceRecordConverter() {
		entity = new Source();
	}

	/**
	 * Creates a new instance of SourceConverter.
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
	public SourceRecordConverter(Source entity, URI uri, int expandLevel, boolean isUriExtendable) {
		this.entity = entity;
		this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getId() + "/").build()
				: uri;
		this.expandLevel = expandLevel;
		getOrganizationCollection();
		getSourceInterestMapCollection();
		getEventCollection();
		getSourceOrgTypeMapCollection();
	}

	/**
	 * Creates a new instance of SourceConverter.
	 * 
	 * @param entity
	 *            associated entity
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public SourceRecordConverter(Source entity, URI uri, int expandLevel) {
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
	 * Getter for etlClass.
	 * 
	 * @return value for etlClass
	 */
	@XmlElement
	public String getEtlClass() {
		return (expandLevel > 0) ? entity.getEtlClass() : null;
	}

	/**
	 * Setter for etlClass.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setEtlClass(String value) {
		entity.setEtlClass(value);
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
	 * Getter for sourceInterestMapCollection.
	 * 
	 * @return value for sourceInterestMapCollection
	 */
	@XmlElement
	public Collection<String> getSourceInterestMapCollection() {
		if (expandLevel > 0) {
			if (entity.getSourceInterestMapCollection() != null) {
				Collection<String> ret = new HashSet<String>();
				for (SourceInterestMap sim : entity.getSourceInterestMapCollection()) {
					ret.add(sim.getId());
				}
                return ret;
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
	 * Getter for sourceOrgTypeMapCollection.
	 * 
	 * @return value for sourceOrgTypeMapCollection
	 */
	@XmlElement
	public Collection<String> getSourceOrgTypeMapCollection() {
		if (expandLevel > 0) {
			if (entity.getSourceOrgTypeMapCollection() != null) {
				Collection<String> ret = new HashSet<String>();
				for (SourceOrgTypeMap sotm : entity.getSourceOrgTypeMapCollection()) {
					ret.add(sotm.getId());
				}
                return ret;
			}
		}
		return null;
	}

	/**
	 * Setter for sourceOrgTypeMapCollection.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setSourceOrgTypeMapCollection(SourceOrgTypeMapsConverter value) {
		entity.setSourceOrgTypeMapCollection((value != null) ? value.getEntities() : null);
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
	 * Returns the Source entity.
	 * 
	 * @return an entity
	 */
	@XmlTransient
	public Source getEntity() {
		if (entity.getId() == null) {
			SourceRecordConverter converter = UriResolver.getInstance().resolve(SourceRecordConverter.class,
					uri);
			if (converter != null) {
				entity = converter.getEntity();
			}
		}
		return entity;
	}

	/**
	 * Returns the resolved Source entity.
	 * 
	 * @return an resolved entity
	 */
	public Source resolveEntity(EntityManager em) {
		Collection<Organization> organizationCollection = entity.getOrganizationCollection();
		Collection<Organization> neworganizationCollection = new java.util.ArrayList<Organization>();
		for (Organization item : organizationCollection) {
			neworganizationCollection.add(em.getReference(Organization.class, item.getId()));
		}
		entity.setOrganizationCollection(neworganizationCollection);
		Collection<SourceInterestMap> sourceInterestMapCollection = entity
				.getSourceInterestMapCollection();
		Collection<SourceInterestMap> newsourceInterestMapCollection = new java.util.ArrayList<SourceInterestMap>();
		for (SourceInterestMap item : sourceInterestMapCollection) {
			newsourceInterestMapCollection.add(em.getReference(SourceInterestMap.class, item
					.getId()));
		}
		entity.setSourceInterestMapCollection(newsourceInterestMapCollection);
		Collection<Event> eventCollection = entity.getEventCollection();
		Collection<Event> neweventCollection = new java.util.ArrayList<Event>();
		for (Event item : eventCollection) {
			neweventCollection.add(em.getReference(Event.class, item.getId()));
		}
		entity.setEventCollection(neweventCollection);
		Collection<SourceOrgTypeMap> sourceOrgTypeMapCollection = entity
				.getSourceOrgTypeMapCollection();
		Collection<SourceOrgTypeMap> newsourceOrgTypeMapCollection = new java.util.ArrayList<SourceOrgTypeMap>();
		for (SourceOrgTypeMap item : sourceOrgTypeMapCollection) {
			newsourceOrgTypeMapCollection
					.add(em.getReference(SourceOrgTypeMap.class, item.getId()));
		}
		entity.setSourceOrgTypeMapCollection(newsourceOrgTypeMapCollection);
		return entity;
	}
}
