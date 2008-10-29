/*
 *  OrganizationTypeConverter
 *
 * Created on October 24, 2008, 9:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package converter;

import java.net.URI;
import persistence.OrganizationTypes;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "organizationType")
public class OrganizationTypeConverter {
    private OrganizationTypes entity;
    private URI uri;
  
    /** Creates a new instance of OrganizationTypeConverter */
    public OrganizationTypeConverter() {
        entity = new OrganizationTypes();
    }

    /**
     * Creates a new instance of OrganizationTypeConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     */
    public OrganizationTypeConverter(OrganizationTypes entity, URI uri) {
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
     * Returns the URI associated with this converter.
     *
     * @return the uri
     */
    @XmlAttribute(name = "uri")
    public URI getResourceUri() {
        return uri;
    }

    /**
     * Returns the OrganizationTypes entity.
     *
     * @return an entity
     */
    @XmlTransient
    public OrganizationTypes getEntity() {
        return entity;
    }

    /**
     * Sets the OrganizationTypes entity.
     *
     * @param entity to set
     */
    public void setEntity(OrganizationTypes entity) {
        this.entity = entity;
    }
}
