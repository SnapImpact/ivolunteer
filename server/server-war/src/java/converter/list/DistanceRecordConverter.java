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
import persistence.Distance;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.ws.rs.core.UriBuilder;
import javax.persistence.EntityManager;
import java.util.Collection;
import persistence.Filter;

/**
 * 
 * @author Dave Angulo
 */

@XmlRootElement(name = "distance")
public class DistanceRecordConverter {
	private Distance	entity;
	private URI			uri;
	private int			expandLevel;

	/** Creates a new instance of DistanceConverter */
	public DistanceRecordConverter() {
		entity = new Distance();
	}

	/**
	 * Creates a new instance of DistanceConverter.
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
	public DistanceRecordConverter(Distance entity, URI uri, int expandLevel,
			boolean isUriExtendable) {
		this.entity = entity;
		this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getId() + "/").build()
				: uri;
		this.expandLevel = expandLevel;
	}

	/**
	 * Creates a new instance of DistanceConverter.
	 * 
	 * @param entity
	 *            associated entity
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public DistanceRecordConverter(Distance entity, URI uri, int expandLevel) {
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
	 * Getter for bucket.
	 * 
	 * @return value for bucket
	 */
	@XmlElement
	public Short getBucket() {
		return (expandLevel > 0) ? entity.getBucket() : null;
	}

	/**
	 * Setter for bucket.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setBucket(Short value) {
		entity.setBucket(value);
	}

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
	 * Returns the Distance entity.
	 * 
	 * @return an entity
	 */
	@XmlTransient
	public Distance getEntity() {
		if (entity.getId() == null) {
			DistanceRecordConverter converter = UriResolver.getInstance().resolve(
					DistanceRecordConverter.class, uri);
			if (converter != null) {
				entity = converter.getEntity();
			}
		}
		return entity;
	}

	/**
	 * Returns the resolved Distance entity.
	 * 
	 * @return an resolved entity
	 */
	public Distance resolveEntity(EntityManager em) {
		Collection<Filter> filterCollection = entity.getFilterCollection();
		Collection<Filter> newfilterCollection = new java.util.ArrayList<Filter>();
		for (Filter item : filterCollection) {
			newfilterCollection.add(em.getReference(Filter.class, item.getId()));
		}
		entity.setFilterCollection(newfilterCollection);
		return entity;
	}
}
