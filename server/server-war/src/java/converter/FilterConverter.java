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
import persistence.Filter;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.ws.rs.core.UriBuilder;
import javax.persistence.EntityManager;
import persistence.InterestArea;
import java.util.Collection;
import persistence.OrganizationType;
import persistence.Timeframe;
import persistence.Distance;
import persistence.IvUser;

/**
 * 
 * @author Dave Angulo
 */

@XmlRootElement(name = "filter")
public class FilterConverter {
	private Filter	entity;
	private URI		uri;
	private int		expandLevel;

	/** Creates a new instance of FilterConverter */
	public FilterConverter() {
		entity = new Filter();
	}

	/**
	 * Creates a new instance of FilterConverter.
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
	public FilterConverter(Filter entity, URI uri, int expandLevel, boolean isUriExtendable) {
		this.entity = entity;
		this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getId() + "/").build()
				: uri;
		this.expandLevel = expandLevel;
		getOrganizationTypeCollection();
		getInterestAreaCollection();
		getDistanceId();
		getUserId();
		getTimeframeId();
	}

	/**
	 * Creates a new instance of FilterConverter.
	 * 
	 * @param entity
	 *            associated entity
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public FilterConverter(Filter entity, URI uri, int expandLevel) {
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
	 * Getter for organizationTypeCollection.
	 * 
	 * @return value for organizationTypeCollection
	 */
	@XmlElement
	public OrganizationTypesConverter getOrganizationTypeCollection() {
		if (expandLevel > 0) {
			if (entity.getOrganizationTypeCollection() != null) {
				return new OrganizationTypesConverter(entity.getOrganizationTypeCollection(), uri
						.resolve("organizationTypeCollection/"), expandLevel - 1);
			}
		}
		return null;
	}

	/**
	 * Setter for organizationTypeCollection.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setOrganizationTypeCollection(OrganizationTypesConverter value) {
		entity.setOrganizationTypeCollection((value != null) ? value.getEntities() : null);
	}

	/**
	 * Getter for interestAreaCollection.
	 * 
	 * @return value for interestAreaCollection
	 */
	@XmlElement
	public InterestAreasConverter getInterestAreaCollection() {
		if (expandLevel > 0) {
			if (entity.getInterestAreaCollection() != null) {
				return new InterestAreasConverter(entity.getInterestAreaCollection(), uri
						.resolve("interestAreaCollection/"), expandLevel - 1);
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
	 * Getter for distanceId.
	 * 
	 * @return value for distanceId
	 */
	@XmlElement
	public DistanceConverter getDistanceId() {
		if (expandLevel > 0) {
			if (entity.getDistanceId() != null) {
				return new DistanceConverter(entity.getDistanceId(), uri.resolve("distanceId/"),
						expandLevel - 1, false);
			}
		}
		return null;
	}

	/**
	 * Setter for distanceId.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setDistanceId(DistanceConverter value) {
		entity.setDistanceId((value != null) ? value.getEntity() : null);
	}

	/**
	 * Getter for userId.
	 * 
	 * @return value for userId
	 */
	@XmlElement
	public IvUserConverter getUserId() {
		if (expandLevel > 0) {
			if (entity.getUserId() != null) {
				return new IvUserConverter(entity.getUserId(), uri.resolve("userId/"),
						expandLevel - 1, false);
			}
		}
		return null;
	}

	/**
	 * Setter for userId.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setUserId(IvUserConverter value) {
		entity.setUserId((value != null) ? value.getEntity() : null);
	}

	/**
	 * Getter for timeframeId.
	 * 
	 * @return value for timeframeId
	 */
	@XmlElement
	public TimeframeConverter getTimeframeId() {
		if (expandLevel > 0) {
			if (entity.getTimeframeId() != null) {
				return new TimeframeConverter(entity.getTimeframeId(), uri.resolve("timeframeId/"),
						expandLevel - 1, false);
			}
		}
		return null;
	}

	/**
	 * Setter for timeframeId.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setTimeframeId(TimeframeConverter value) {
		entity.setTimeframeId((value != null) ? value.getEntity() : null);
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
	 * Returns the Filter entity.
	 * 
	 * @return an entity
	 */
	@XmlTransient
	public Filter getEntity() {
		if (entity.getId() == null) {
			FilterConverter converter = UriResolver.getInstance().resolve(FilterConverter.class,
					uri);
			if (converter != null) {
				entity = converter.getEntity();
			}
		}
		return entity;
	}

	/**
	 * Returns the resolved Filter entity.
	 * 
	 * @return an resolved entity
	 */
	public Filter resolveEntity(EntityManager em) {
		Collection<OrganizationType> organizationTypeCollection = entity
				.getOrganizationTypeCollection();
		Collection<OrganizationType> neworganizationTypeCollection = new java.util.ArrayList<OrganizationType>();
		for (OrganizationType item : organizationTypeCollection) {
			neworganizationTypeCollection
					.add(em.getReference(OrganizationType.class, item.getId()));
		}
		entity.setOrganizationTypeCollection(neworganizationTypeCollection);
		Collection<InterestArea> interestAreaCollection = entity.getInterestAreaCollection();
		Collection<InterestArea> newinterestAreaCollection = new java.util.ArrayList<InterestArea>();
		for (InterestArea item : interestAreaCollection) {
			newinterestAreaCollection.add(em.getReference(InterestArea.class, item.getId()));
		}
		entity.setInterestAreaCollection(newinterestAreaCollection);
		Distance distanceId = entity.getDistanceId();
		if (distanceId != null) {
			entity.setDistanceId(em.getReference(Distance.class, distanceId.getId()));
		}
		IvUser userId = entity.getUserId();
		if (userId != null) {
			entity.setUserId(em.getReference(IvUser.class, userId.getId()));
		}
		Timeframe timeframeId = entity.getTimeframeId();
		if (timeframeId != null) {
			entity.setTimeframeId(em.getReference(Timeframe.class, timeframeId.getId()));
		}
		return entity;
	}
}
