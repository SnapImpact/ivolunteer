/*
 *  NetworksConverter
 *
 * Created on October 24, 2008, 9:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package converter;

import java.net.URI;
import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.ArrayList;
import persistence.Networks;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "networks")
public class NetworksConverter {
    private Collection<Networks> entities;
    private Collection<NetworkRefConverter> references;
    private URI uri;
  
    /** Creates a new instance of NetworksConverter */
    public NetworksConverter() {
    }

    /**
     * Creates a new instance of NetworksConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public NetworksConverter(Collection<Networks> entities, URI uri) {
        this.entities = entities;
        this.uri = uri;
    }

    /**
     * Returns a collection of NetworkRefConverter.
     *
     * @return a collection of NetworkRefConverter
     */
    @XmlElement(name = "networkRef")
    public Collection<NetworkRefConverter> getReferences() {
        references = new ArrayList<NetworkRefConverter>();
        if (entities != null) {
            for (Networks entity : entities) {
                references.add(new NetworkRefConverter(entity, uri, true));
            }
        }
        return references;
    }

    /**
     * Sets a collection of NetworkRefConverter.
     *
     * @param a collection of NetworkRefConverter to set
     */
    public void setReferences(Collection<NetworkRefConverter> references) {
        this.references = references;
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
     * Returns a collection Networks entities.
     *
     * @return a collection of Networks entities
     */
    @XmlTransient
    public Collection<Networks> getEntities() {
        entities = new ArrayList<Networks>();
        if (references != null) {
            for (NetworkRefConverter ref : references) {
                entities.add(ref.getEntity());
            }
        }
        return entities;
    }
}
