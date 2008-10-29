/*
 *  DistancesConverter
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
import persistence.Distances;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "distances")
public class DistancesConverter {
    private Collection<Distances> entities;
    private Collection<DistanceRefConverter> references;
    private URI uri;
  
    /** Creates a new instance of DistancesConverter */
    public DistancesConverter() {
    }

    /**
     * Creates a new instance of DistancesConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public DistancesConverter(Collection<Distances> entities, URI uri) {
        this.entities = entities;
        this.uri = uri;
    }

    /**
     * Returns a collection of DistanceRefConverter.
     *
     * @return a collection of DistanceRefConverter
     */
    @XmlElement(name = "distanceRef")
    public Collection<DistanceRefConverter> getReferences() {
        references = new ArrayList<DistanceRefConverter>();
        if (entities != null) {
            for (Distances entity : entities) {
                references.add(new DistanceRefConverter(entity, uri, true));
            }
        }
        return references;
    }

    /**
     * Sets a collection of DistanceRefConverter.
     *
     * @param a collection of DistanceRefConverter to set
     */
    public void setReferences(Collection<DistanceRefConverter> references) {
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
     * Returns a collection Distances entities.
     *
     * @return a collection of Distances entities
     */
    @XmlTransient
    public Collection<Distances> getEntities() {
        entities = new ArrayList<Distances>();
        if (references != null) {
            for (DistanceRefConverter ref : references) {
                entities.add(ref.getEntity());
            }
        }
        return entities;
    }
}
