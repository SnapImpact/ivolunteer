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
import persistence.SourceInterestMap;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.ws.rs.core.UriBuilder;
import javax.persistence.EntityManager;
import persistence.Source;
import persistence.InterestArea;

/**
 * 
 * @author Dave Angulo
 */

@XmlRootElement(name = "sourceInterestMap")
public class SourceInterestMapConverter {
	private SourceInterestMap	entity;
	private URI					uri;
	private int					expandLevel;

	/** Creates a new instance of SourceInterestMapConverter */
	public SourceInterestMapConverter() {
		entity = new SourceInterestMap();
	}

	/**
	 * Creates a new instance of SourceInterestMapConverter.
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
	public SourceInterestMapConverter(SourceInterestMap entity, URI uri, int expandLevel,
			boolean isUriExtendable) {
		this.entity = entity;
		this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getId() + "/").build()
				: uri;
		this.expandLevel = expandLevel;
		getInterestAreaId();
		getSourceId();
	}

	/**
	 * Creates a new instance of SourceInterestMapConverter.
	 * 
	 * @param entity
	 *            associated entity
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public SourceInterestMapConverter(SourceInterestMap entity, URI uri, int expandLevel) {
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
	 * Getter for interestAreaId.
	 * 
	 * @return value for interestAreaId
	 */
	@XmlElement
	public InterestAreaConverter getInterestAreaId() {
		if (expandLevel > 0) {
			if (entity.getInterestAreaId() != null) {
				return new InterestAreaConverter(entity.getInterestAreaId(), uri
						.resolve("interestAreaId/"), expandLevel - 1, false);
			}
		}
		return null;
	}

	/**
	 * Setter for interestAreaId.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setInterestAreaId(InterestAreaConverter value) {
		entity.setInterestAreaId((value != null) ? value.getEntity() : null);
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
	 * Returns the SourceInterestMap entity.
	 * 
	 * @return an entity
	 */
	@XmlTransient
	public SourceInterestMap getEntity() {
		if (entity.getId() == null) {
			SourceInterestMapConverter converter = UriResolver.getInstance().resolve(
					SourceInterestMapConverter.class, uri);
			if (converter != null) {
				entity = converter.getEntity();
			}
		}
		return entity;
	}

	/**
	 * Returns the resolved SourceInterestMap entity.
	 * 
	 * @return an resolved entity
	 */
	public SourceInterestMap resolveEntity(EntityManager em) {
		InterestArea interestAreaId = entity.getInterestAreaId();
		if (interestAreaId != null) {
			entity.setInterestAreaId(em.getReference(InterestArea.class, interestAreaId.getId()));
		}
		Source sourceId = entity.getSourceId();
		if (sourceId != null) {
			entity.setSourceId(em.getReference(Source.class, sourceId.getId()));
		}
		return entity;
	}
}
