/*
 *  DistanceConverter
 *
 * Created on October 24, 2008, 9:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package converter;

import java.net.URI;
import persistence.Distances;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "distance")
public class DistanceConverter {
    private Distances entity;
    private URI uri;
  
    /** Creates a new instance of DistanceConverter */
    public DistanceConverter() {
        entity = new Distances();
    }

    /**
     * Creates a new instance of DistanceConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     */
    public DistanceConverter(Distances entity, URI uri) {
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
     * Getter for bucket.
     *
     * @return value for bucket
     */
    @XmlElement
    public short getBucket() {
        return entity.getBucket();
    }

    /**
     * Setter for bucket.
     *
     * @param value the value to set
     */
    public void setBucket(short value) {
        entity.setBucket(value);
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
     * Returns the URI associated with this converter.
     *
     * @return the uri
     */
    @XmlAttribute(name = "uri")
    public URI getResourceUri() {
        return uri;
    }

    /**
     * Returns the Distances entity.
     *
     * @return an entity
     */
    @XmlTransient
    public Distances getEntity() {
        return entity;
    }

    /**
     * Sets the Distances entity.
     *
     * @param entity to set
     */
    public void setEntity(Distances entity) {
        this.entity = entity;
    }
}
