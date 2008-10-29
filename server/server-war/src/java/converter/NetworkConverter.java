/*
 *  NetworkConverter
 *
 * Created on October 24, 2008, 9:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package converter;

import java.net.URI;
import persistence.Networks;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "network")
public class NetworkConverter {
    private Networks entity;
    private URI uri;
  
    /** Creates a new instance of NetworkConverter */
    public NetworkConverter() {
        entity = new Networks();
    }

    /**
     * Creates a new instance of NetworkConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     */
    public NetworkConverter(Networks entity, URI uri) {
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
     * Returns the Networks entity.
     *
     * @return an entity
     */
    @XmlTransient
    public Networks getEntity() {
        return entity;
    }

    /**
     * Sets the Networks entity.
     *
     * @param entity to set
     */
    public void setEntity(Networks entity) {
        this.entity = entity;
    }
}
