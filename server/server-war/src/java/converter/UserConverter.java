/*
 *  UserConverter
 *
 * Created on October 24, 2008, 9:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package converter;

import java.net.URI;
import persistence.Users;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "user")
public class UserConverter {
    private Users entity;
    private URI uri;
  
    /** Creates a new instance of UserConverter */
    public UserConverter() {
        entity = new Users();
    }

    /**
     * Creates a new instance of UserConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     */
    public UserConverter(Users entity, URI uri) {
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
     * Getter for password.
     *
     * @return value for password
     */
    @XmlElement
    public String getPassword() {
        return entity.getPassword();
    }

    /**
     * Setter for password.
     *
     * @param value the value to set
     */
    public void setPassword(String value) {
        entity.setPassword(value);
    }

    /**
     * Getter for iphoneId.
     *
     * @return value for iphoneId
     */
    @XmlElement
    public String getIphoneId() {
        return entity.getIphoneId();
    }

    /**
     * Setter for iphoneId.
     *
     * @param value the value to set
     */
    public void setIphoneId(String value) {
        entity.setIphoneId(value);
    }

    /**
     * Getter for filterCollection.
     *
     * @return value for filterCollection
     */
    @XmlElement(name = "filters")
    public FiltersConverter getFilterCollection() {
        if (entity.getFilterCollection() != null) {
            return new FiltersConverter(entity.getFilterCollection(), uri.resolve("filters/"));
        }
        return null;
    }

    /**
     * Setter for filterCollection.
     *
     * @param value the value to set
     */
    public void setFilterCollection(FiltersConverter value) {
        if (value != null) {
            entity.setFilterCollection(value.getEntities());
        }
    }

    /**
     * Getter for integrationsCollection.
     *
     * @return value for integrationsCollection
     */
    @XmlElement(name = "integrations")
    public IntegrationsConverter getIntegrationsCollection() {
        if (entity.getIntegrationsCollection() != null) {
            return new IntegrationsConverter(entity.getIntegrationsCollection(), uri.resolve("integrations/"));
        }
        return null;
    }

    /**
     * Setter for integrationsCollection.
     *
     * @param value the value to set
     */
    public void setIntegrationsCollection(IntegrationsConverter value) {
        if (value != null) {
            entity.setIntegrationsCollection(value.getEntities());
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
     * Returns the Users entity.
     *
     * @return an entity
     */
    @XmlTransient
    public Users getEntity() {
        return entity;
    }

    /**
     * Sets the Users entity.
     *
     * @param entity to set
     */
    public void setEntity(Users entity) {
        this.entity = entity;
    }
}
