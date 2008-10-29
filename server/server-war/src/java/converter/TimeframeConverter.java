/*
 *  TimeframeConverter
 *
 * Created on October 24, 2008, 9:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package converter;

import java.math.BigInteger;
import java.net.URI;
import persistence.Timeframes;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "timeframe")
public class TimeframeConverter {
    private Timeframes entity;
    private URI uri;
  
    /** Creates a new instance of TimeframeConverter */
    public TimeframeConverter() {
        entity = new Timeframes();
    }

    /**
     * Creates a new instance of TimeframeConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     */
    public TimeframeConverter(Timeframes entity, URI uri) {
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
    public BigInteger getBucket() {
        return entity.getBucket();
    }

    /**
     * Setter for bucket.
     *
     * @param value the value to set
     */
    public void setBucket(BigInteger value) {
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
     * Returns the Timeframes entity.
     *
     * @return an entity
     */
    @XmlTransient
    public Timeframes getEntity() {
        return entity;
    }

    /**
     * Sets the Timeframes entity.
     *
     * @param entity to set
     */
    public void setEntity(Timeframes entity) {
        this.entity = entity;
    }
}
