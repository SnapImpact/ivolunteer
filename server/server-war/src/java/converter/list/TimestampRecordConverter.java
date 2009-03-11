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
import java.util.Date;
import persistence.Timestamp;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.ws.rs.core.UriBuilder;
import javax.persistence.EntityManager;
import persistence.Event;
import java.util.Collection;
import java.util.HashSet;

/**
 * 
 * @author Dave Angulo
 */

@XmlRootElement(name = "timestamp")
public class TimestampRecordConverter {
	private Timestamp	entity;
	private URI			uri;
	private int			expandLevel;

	/** Creates a new instance of TimestampConverter */
	public TimestampRecordConverter() {
		entity = new Timestamp();
	}

	/**
	 * Creates a new instance of TimestampConverter.
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
	public TimestampRecordConverter(Timestamp entity, URI uri, int expandLevel,
			boolean isUriExtendable) {
		this.entity = entity;
		this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getId() + "/").build()
				: uri;
		this.expandLevel = expandLevel;
		getEventCollection();
	}

	/**
	 * Creates a new instance of TimestampConverter.
	 * 
	 * @param entity
	 *            associated entity
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public TimestampRecordConverter(Timestamp entity, URI uri, int expandLevel) {
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
	 * Getter for timestamp.
	 * 
	 * @return value for timestamp
	 */
	@XmlElement
	public Date getTimestamp() {
		return (expandLevel > 0) ? entity.getTimestamp() : null;
	}

	/**
	 * Setter for timestamp.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setTimestamp(Date value) {
		entity.setTimestamp(value);
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
	 * Returns the Timestamp entity.
	 * 
	 * @return an entity
	 */
	@XmlTransient
	public Timestamp getEntity() {
		if (entity.getId() == null) {
			TimestampRecordConverter converter = UriResolver.getInstance().resolve(
					TimestampRecordConverter.class, uri);
			if (converter != null) {
				entity = converter.getEntity();
			}
		}
		return entity;
	}

	/**
	 * Returns the resolved Timestamp entity.
	 * 
	 * @return an resolved entity
	 */
	public Timestamp resolveEntity(EntityManager em) {
		Collection<Event> eventCollection = entity.getEventCollection();
		Collection<Event> neweventCollection = new java.util.ArrayList<Event>();
		for (Event item : eventCollection) {
			neweventCollection.add(em.getReference(Event.class, item.getId()));
		}
		entity.setEventCollection(neweventCollection);
		return entity;
	}
}
