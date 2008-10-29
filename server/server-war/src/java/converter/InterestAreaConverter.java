/*
 *  InterestAreaConverter
 *
 * Created on October 24, 2008, 9:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package converter;

import java.net.URI;
import persistence.InterestAreas;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "interestArea")
public class InterestAreaConverter {
    private InterestAreas entity;
    private URI uri;
  
    /** Creates a new instance of InterestAreaConverter */
    public InterestAreaConverter() {
        entity = new InterestAreas();
    }

    /**
     * Creates a new instance of InterestAreaConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     */
    public InterestAreaConverter(InterestAreas entity, URI uri) {
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
     * Getter for organizationIdCollection.
     *
     * @return value for organizationIdCollection
     */
    @XmlElement(name = "organizations")
    public OrganizationsConverter getOrganizationIdCollection() {
        if (entity.getOrganizationIdCollection() != null) {
            return new OrganizationsConverter(entity.getOrganizationIdCollection(), uri.resolve("organizations/"));
        }
        return null;
    }

    /**
     * Setter for organizationIdCollection.
     *
     * @param value the value to set
     */
    public void setOrganizationIdCollection(OrganizationsConverter value) {
        if (value != null) {
            entity.setOrganizationIdCollection(value.getEntities());
        }
    }

    /**
     * Getter for eventIdCollection.
     *
     * @return value for eventIdCollection
     */
    @XmlElement(name = "events")
    public EventsConverter getEventIdCollection() {
        if (entity.getEventIdCollection() != null) {
            return new EventsConverter(entity.getEventIdCollection(), uri.resolve("events/"));
        }
        return null;
    }

    /**
     * Setter for eventIdCollection.
     *
     * @param value the value to set
     */
    public void setEventIdCollection(EventsConverter value) {
        if (value != null) {
            entity.setEventIdCollection(value.getEntities());
        }
    }

    /**
     * Getter for filterIdCollection.
     *
     * @return value for filterIdCollection
     */
    @XmlElement(name = "filters")
    public FiltersConverter getFilterIdCollection() {
        if (entity.getFilterIdCollection() != null) {
            return new FiltersConverter(entity.getFilterIdCollection(), uri.resolve("filters/"));
        }
        return null;
    }

    /**
     * Setter for filterIdCollection.
     *
     * @param value the value to set
     */
    public void setFilterIdCollection(FiltersConverter value) {
        if (value != null) {
            entity.setFilterIdCollection(value.getEntities());
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
     * Returns the InterestAreas entity.
     *
     * @return an entity
     */
    @XmlTransient
    public InterestAreas getEntity() {
        return entity;
    }

    /**
     * Sets the InterestAreas entity.
     *
     * @param entity to set
     */
    public void setEntity(InterestAreas entity) {
        this.entity = entity;
    }
}
