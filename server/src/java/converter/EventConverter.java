/*
 *  EventConverter
 *
 * Created on October 11, 2008, 9:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package converter;

import java.net.URI;
import java.util.Date;
import persistence.Events;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "event")
public class EventConverter {
    private Events entity;
    private URI uri;
  
    /** Creates a new instance of EventConverter */
    public EventConverter() {
        entity = new Events();
    }

    /**
     * Creates a new instance of EventConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     */
    public EventConverter(Events entity, URI uri) {
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
     * Getter for title.
     *
     * @return value for title
     */
    @XmlElement
    public String getTitle() {
        return entity.getTitle();
    }

    /**
     * Setter for title.
     *
     * @param value the value to set
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
        return entity.getDescription();
    }

    /**
     * Setter for description.
     *
     * @param value the value to set
     */
    public void setDescription(String value) {
        entity.setDescription(value);
    }

    /**
     * Getter for timestamp.
     *
     * @return value for timestamp
     */
    @XmlElement
    public Date getTimestamp() {
        return entity.getTimestamp();
    }

    /**
     * Setter for timestamp.
     *
     * @param value the value to set
     */
    public void setTimestamp(Date value) {
        entity.setTimestamp(value);
    }

    /**
     * Getter for duration.
     *
     * @return value for duration
     */
    @XmlElement
    public Short getDuration() {
        return entity.getDuration();
    }

    /**
     * Setter for duration.
     *
     * @param value the value to set
     */
    public void setDuration(Short value) {
        entity.setDuration(value);
    }

    /**
     * Getter for location.
     *
     * @return value for location
     */
    @XmlElement
    public String getLocation() {
        return entity.getLocation();
    }

    /**
     * Setter for location.
     *
     * @param value the value to set
     */
    public void setLocation(String value) {
        entity.setLocation(value);
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
     * Getter for contact.
     *
     * @return value for contact
     */
    @XmlElement
    public String getContact() {
        return entity.getContact();
    }

    /**
     * Setter for contact.
     *
     * @param value the value to set
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
        return entity.getUrl();
    }

    /**
     * Setter for url.
     *
     * @param value the value to set
     */
    public void setUrl(String value) {
        entity.setUrl(value);
    }

    /**
     * Getter for street.
     *
     * @return value for street
     */
    @XmlElement
    public String getStreet() {
        return entity.getStreet();
    }

    /**
     * Setter for street.
     *
     * @param value the value to set
     */
    public void setStreet(String value) {
        entity.setStreet(value);
    }

    /**
     * Getter for city.
     *
     * @return value for city
     */
    @XmlElement
    public String getCity() {
        return entity.getCity();
    }

    /**
     * Setter for city.
     *
     * @param value the value to set
     */
    public void setCity(String value) {
        entity.setCity(value);
    }

    /**
     * Getter for state.
     *
     * @return value for state
     */
    @XmlElement
    public String getState() {
        return entity.getState();
    }

    /**
     * Setter for state.
     *
     * @param value the value to set
     */
    public void setState(String value) {
        entity.setState(value);
    }

    /**
     * Getter for zip.
     *
     * @return value for zip
     */
    @XmlElement
    public String getZip() {
        return entity.getZip();
    }

    /**
     * Setter for zip.
     *
     * @param value the value to set
     */
    public void setZip(String value) {
        entity.setZip(value);
    }

    /**
     * Getter for phone.
     *
     * @return value for phone
     */
    @XmlElement
    public String getPhone() {
        return entity.getPhone();
    }

    /**
     * Setter for phone.
     *
     * @param value the value to set
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
        return entity.getEmail();
    }

    /**
     * Setter for email.
     *
     * @param value the value to set
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
        return entity.getSourceKey();
    }

    /**
     * Setter for sourceKey.
     *
     * @param value the value to set
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
        return entity.getSourceUrl();
    }

    /**
     * Setter for sourceUrl.
     *
     * @param value the value to set
     */
    public void setSourceUrl(String value) {
        entity.setSourceUrl(value);
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
     * Getter for organizationId.
     *
     * @return value for organizationId
     */
    @XmlElement(name = "organizationRef")
    public OrganizationRefConverter getOrganizationId() {
        if (entity.getOrganizationId() != null) {
            return new OrganizationRefConverter(entity.getOrganizationId(), uri.resolve("organization/"), false);
        }
        return null;
    }

    /**
     * Setter for organizationId.
     *
     * @param value the value to set
     */
    public void setOrganizationId(OrganizationRefConverter value) {
        if (value != null) {
            entity.setOrganizationId(value.getEntity());
        }
    }

    /**
     * Getter for sourceId.
     *
     * @return value for sourceId
     */
    @XmlElement(name = "sourceRef")
    public SourceRefConverter getSourceId() {
        if (entity.getSourceId() != null) {
            return new SourceRefConverter(entity.getSourceId(), uri.resolve("source/"), false);
        }
        return null;
    }

    /**
     * Setter for sourceId.
     *
     * @param value the value to set
     */
    public void setSourceId(SourceRefConverter value) {
        if (value != null) {
            entity.setSourceId(value.getEntity());
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
     * Returns the Events entity.
     *
     * @return an entity
     */
    @XmlTransient
    public Events getEntity() {
        return entity;
    }

    /**
     * Sets the Events entity.
     *
     * @param entity to set
     */
    public void setEntity(Events entity) {
        this.entity = entity;
    }
}
