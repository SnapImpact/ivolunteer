/*
 *  OrganizationConverter
 *
 * Created on October 11, 2008, 9:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package converter;

import java.net.URI;
import persistence.Organizations;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "organization")
public class OrganizationConverter {
    private Organizations entity;
    private URI uri;
  
    /** Creates a new instance of OrganizationConverter */
    public OrganizationConverter() {
        entity = new Organizations();
    }

    /**
     * Creates a new instance of OrganizationConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     */
    public OrganizationConverter(Organizations entity, URI uri) {
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
     * Getter for name.
     *
     * @return value for name
     */
    @XmlElement
    public String getName() {
        return entity.getName();
    }

    /**
     * Setter for name.
     *
     * @param value the value to set
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
     * Getter for organizationTypeId.
     *
     * @return value for organizationTypeId
     */
    @XmlElement(name = "organizationTypeRef")
    public OrganizationTypeRefConverter getOrganizationTypeId() {
        if (entity.getOrganizationTypeId() != null) {
            return new OrganizationTypeRefConverter(entity.getOrganizationTypeId(), uri.resolve("organizationType/"), false);
        }
        return null;
    }

    /**
     * Setter for organizationTypeId.
     *
     * @param value the value to set
     */
    public void setOrganizationTypeId(OrganizationTypeRefConverter value) {
        if (value != null) {
            entity.setOrganizationTypeId(value.getEntity());
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
     * Getter for eventsCollection.
     *
     * @return value for eventsCollection
     */
    @XmlElement(name = "events")
    public EventsConverter getEventsCollection() {
        if (entity.getEventsCollection() != null) {
            return new EventsConverter(entity.getEventsCollection(), uri.resolve("events/"));
        }
        return null;
    }

    /**
     * Setter for eventsCollection.
     *
     * @param value the value to set
     */
    public void setEventsCollection(EventsConverter value) {
        if (value != null) {
            entity.setEventsCollection(value.getEntities());
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
     * Returns the Organizations entity.
     *
     * @return an entity
     */
    @XmlTransient
    public Organizations getEntity() {
        return entity;
    }

    /**
     * Sets the Organizations entity.
     *
     * @param entity to set
     */
    public void setEntity(Organizations entity) {
        this.entity = entity;
    }
}
