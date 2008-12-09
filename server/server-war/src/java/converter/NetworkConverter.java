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
import persistence.Network;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.ws.rs.core.UriBuilder;
import javax.persistence.EntityManager;
import java.util.Collection;
import persistence.Integration;

/**
 * 
 * @author Dave Angulo
 */

@XmlRootElement(name = "network")
public class NetworkConverter {
	private Network	entity;
	private URI		uri;
	private int		expandLevel;

	/** Creates a new instance of NetworkConverter */
	public NetworkConverter() {
		entity = new Network();
	}

	/**
	 * Creates a new instance of NetworkConverter.
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
	public NetworkConverter(Network entity, URI uri, int expandLevel, boolean isUriExtendable) {
		this.entity = entity;
		this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getId() + "/").build()
				: uri;
		this.expandLevel = expandLevel;
		getIntegrationCollection();
	}

	/**
	 * Creates a new instance of NetworkConverter.
	 * 
	 * @param entity
	 *            associated entity
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public NetworkConverter(Network entity, URI uri, int expandLevel) {
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
	 * Getter for integrationCollection.
	 * 
	 * @return value for integrationCollection
	 */
	@XmlElement
	public IntegrationsConverter getIntegrationCollection() {
		if (expandLevel > 0) {
			if (entity.getIntegrationCollection() != null) {
				return new IntegrationsConverter(entity.getIntegrationCollection(), uri
						.resolve("integrationCollection/"), expandLevel - 1);
			}
		}
		return null;
	}

	/**
	 * Setter for integrationCollection.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setIntegrationCollection(IntegrationsConverter value) {
		entity.setIntegrationCollection((value != null) ? value.getEntities() : null);
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
	 * Returns the Network entity.
	 * 
	 * @return an entity
	 */
	@XmlTransient
	public Network getEntity() {
		if (entity.getId() == null) {
			NetworkConverter converter = UriResolver.getInstance().resolve(NetworkConverter.class,
					uri);
			if (converter != null) {
				entity = converter.getEntity();
			}
		}
		return entity;
	}

	/**
	 * Returns the resolved Network entity.
	 * 
	 * @return an resolved entity
	 */
	public Network resolveEntity(EntityManager em) {
		Collection<Integration> integrationCollection = entity.getIntegrationCollection();
		Collection<Integration> newintegrationCollection = new java.util.ArrayList<Integration>();
		for (Integration item : integrationCollection) {
			newintegrationCollection.add(em.getReference(Integration.class, item.getId()));
		}
		entity.setIntegrationCollection(newintegrationCollection);
		return entity;
	}
}
