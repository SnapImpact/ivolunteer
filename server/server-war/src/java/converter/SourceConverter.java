/*
 *  SourceConverter
 *
 * Created on October 24, 2008, 9:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package converter;

import java.net.URI;
import persistence.Sources;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "source")
public class SourceConverter {
    private Sources entity;
    private URI uri;
  
    /** Creates a new instance of SourceConverter */
    public SourceConverter() {
        entity = new Sources();
    }

    /**
     * Creates a new instance of SourceConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     */
    public SourceConverter(Sources entity, URI uri) {
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
     * Getter for etlClass.
     *
     * @return value for etlClass
     */
    @XmlElement
    public String getEtlClass() {
        return entity.getEtlClass();
    }

    /**
     * Setter for etlClass.
     *
     * @param value the value to set
     */
    public void setEtlClass(String value) {
        entity.setEtlClass(value);
    }

    /**
     * Getter for organizationsCollection.
     *
     * @return value for organizationsCollection
     */
    @XmlElement(name = "organizations")
    public OrganizationsConverter getOrganizationsCollection() {
        if (entity.getOrganizationsCollection() != null) {
            return new OrganizationsConverter(entity.getOrganizationsCollection(), uri.resolve("organizations/"));
        }
        return null;
    }

    /**
     * Setter for organizationsCollection.
     *
     * @param value the value to set
     */
    public void setOrganizationsCollection(OrganizationsConverter value) {
        if (value != null) {
            entity.setOrganizationsCollection(value.getEntities());
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
     * Returns the Sources entity.
     *
     * @return an entity
     */
    @XmlTransient
    public Sources getEntity() {
        return entity;
    }

    /**
     * Sets the Sources entity.
     *
     * @param entity to set
     */
    public void setEntity(Sources entity) {
        this.entity = entity;
    }
}
