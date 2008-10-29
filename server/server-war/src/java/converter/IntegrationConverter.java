/*
 *  IntegrationConverter
 *
 * Created on October 24, 2008, 9:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package converter;

import java.net.URI;
import persistence.Integrations;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "integration")
public class IntegrationConverter {
    private Integrations entity;
    private URI uri;
  
    /** Creates a new instance of IntegrationConverter */
    public IntegrationConverter() {
        entity = new Integrations();
    }

    /**
     * Creates a new instance of IntegrationConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     */
    public IntegrationConverter(Integrations entity, URI uri) {
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
     * Getter for userName.
     *
     * @return value for userName
     */
    @XmlElement
    public String getUserName() {
        return entity.getUserName();
    }

    /**
     * Setter for userName.
     *
     * @param value the value to set
     */
    public void setUserName(String value) {
        entity.setUserName(value);
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
     * Getter for networkId.
     *
     * @return value for networkId
     */
    @XmlElement(name = "networkRef")
    public NetworkRefConverter getNetworkId() {
        if (entity.getNetworkId() != null) {
            return new NetworkRefConverter(entity.getNetworkId(), uri.resolve("network/"), false);
        }
        return null;
    }

    /**
     * Setter for networkId.
     *
     * @param value the value to set
     */
    public void setNetworkId(NetworkRefConverter value) {
        if (value != null) {
            entity.setNetworkId(value.getEntity());
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
     * Returns the Integrations entity.
     *
     * @return an entity
     */
    @XmlTransient
    public Integrations getEntity() {
        return entity;
    }

    /**
     * Sets the Integrations entity.
     *
     * @param entity to set
     */
    public void setEntity(Integrations entity) {
        this.entity = entity;
    }
}
