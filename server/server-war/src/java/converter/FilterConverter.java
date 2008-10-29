/*
 *  FilterConverter
 *
 * Created on October 24, 2008, 9:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package converter;

import java.net.URI;
import persistence.Filter;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "filter")
public class FilterConverter {
    private Filter entity;
    private URI uri;
  
    /** Creates a new instance of FilterConverter */
    public FilterConverter() {
        entity = new Filter();
    }

    /**
     * Creates a new instance of FilterConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     */
    public FilterConverter(Filter entity, URI uri) {
        this.entity = entity;
        this.uri = uri;
    }

    /**
     * Getter for id.
     *
     * @return value for id
     */
    @XmlElement
    public String getId() {
        return entity.getId();
    }

    /**
     * Setter for id.
     *
     * @param value the value to set
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
        return entity.getLatitude();
    }

    /**
     * Setter for latitude.
     *
     * @param value the value to set
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
        return entity.getLongitude();
    }

    /**
     * Setter for longitude.
     *
     * @param value the value to set
     */
    public void setLongitude(String value) {
        entity.setLongitude(value);
    }

    /**
     * Getter for organizationTypeIdCollection.
     *
     * @return value for organizationTypeIdCollection
     */
    @XmlElement(name = "organizationTypes")
    public OrganizationTypesConverter getOrganizationTypeIdCollection() {
        if (entity.getOrganizationTypeIdCollection() != null) {
            return new OrganizationTypesConverter(entity.getOrganizationTypeIdCollection(), uri.resolve("organizationTypes/"));
        }
        return null;
    }

    /**
     * Setter for organizationTypeIdCollection.
     *
     * @param value the value to set
     */
    public void setOrganizationTypeIdCollection(OrganizationTypesConverter value) {
        if (value != null) {
            entity.setOrganizationTypeIdCollection(value.getEntities());
        }
    }

    /**
     * Getter for interestAreaIdCollection.
     *
     * @return value for interestAreaIdCollection
     */
    @XmlElement(name = "interestAreas")
    public InterestAreasConverter getInterestAreaIdCollection() {
        if (entity.getInterestAreaIdCollection() != null) {
            return new InterestAreasConverter(entity.getInterestAreaIdCollection(), uri.resolve("interestAreas/"));
        }
        return null;
    }

    /**
     * Setter for interestAreaIdCollection.
     *
     * @param value the value to set
     */
    public void setInterestAreaIdCollection(InterestAreasConverter value) {
        if (value != null) {
            entity.setInterestAreaIdCollection(value.getEntities());
        }
    }

    /**
     * Getter for distanceId.
     *
     * @return value for distanceId
     */
    @XmlElement(name = "distanceRef")
    public DistanceRefConverter getDistanceId() {
        if (entity.getDistanceId() != null) {
            return new DistanceRefConverter(entity.getDistanceId(), uri.resolve("distance/"), false);
        }
        return null;
    }

    /**
     * Setter for distanceId.
     *
     * @param value the value to set
     */
    public void setDistanceId(DistanceRefConverter value) {
        if (value != null) {
            entity.setDistanceId(value.getEntity());
        }
    }

    /**
     * Getter for timeframeId.
     *
     * @return value for timeframeId
     */
    @XmlElement(name = "timeframeRef")
    public TimeframeRefConverter getTimeframeId() {
        if (entity.getTimeframeId() != null) {
            return new TimeframeRefConverter(entity.getTimeframeId(), uri.resolve("timeframe/"), false);
        }
        return null;
    }

    /**
     * Setter for timeframeId.
     *
     * @param value the value to set
     */
    public void setTimeframeId(TimeframeRefConverter value) {
        if (value != null) {
            entity.setTimeframeId(value.getEntity());
        }
    }

    /**
     * Getter for userId.
     *
     * @return value for userId
     */
    @XmlElement(name = "userRef")
    public UserRefConverter getUserId() {
        if (entity.getUserId() != null) {
            return new UserRefConverter(entity.getUserId(), uri.resolve("user/"), false);
        }
        return null;
    }

    /**
     * Setter for userId.
     *
     * @param value the value to set
     */
    public void setUserId(UserRefConverter value) {
        if (value != null) {
            entity.setUserId(value.getEntity());
        }
    }

    /**
     * Returns the URI associated with this converter.
     *
     * @return the uri
     */
    @XmlAttribute(name = "uri")
    public URI getResourceUri() {
        return uri;
    }

    /**
     * Returns the Filter entity.
     *
     * @return an entity
     */
    @XmlTransient
    public Filter getEntity() {
        return entity;
    }

    /**
     * Sets the Filter entity.
     *
     * @param entity to set
     */
    public void setEntity(Filter entity) {
        this.entity = entity;
    }
}
