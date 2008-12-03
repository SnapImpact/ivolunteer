/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package converter;

import java.math.BigInteger;
import java.net.URI;
import persistence.Timeframe;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.ws.rs.core.UriBuilder;
import javax.persistence.EntityManager;
import java.util.Collection;
import persistence.Filter;

/**
 *
 * @author dave
 */

@XmlRootElement(name = "timeframe")
public class TimeframeConverter {
    private Timeframe entity;
    private URI uri;
    private int expandLevel;
  
    /** Creates a new instance of TimeframeConverter */
    public TimeframeConverter() {
        entity = new Timeframe();
    }

    /**
     * Creates a new instance of TimeframeConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded@param isUriExtendable indicates whether the uri can be extended
     */
    public TimeframeConverter(Timeframe entity, URI uri, int expandLevel, boolean isUriExtendable) {
        this.entity = entity;
        this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getId() + "/").build() : uri;
        this.expandLevel = expandLevel;
        getFilterCollection();
    }

    /**
     * Creates a new instance of TimeframeConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded
     */
    public TimeframeConverter(Timeframe entity, URI uri, int expandLevel) {
        this(entity, uri, expandLevel, false);
    }

    /**
     * Getter for id.
     *
     * @return value for id
     */
    @XmlElement
    public String getId() {
        return (expandLevel > 0) ? entity.getId() : null;
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
        return (expandLevel > 0) ? entity.getName() : null;
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
     * Getter for bucket.
     *
     * @return value for bucket
     */
    @XmlElement
    public BigInteger getBucket() {
        return (expandLevel > 0) ? entity.getBucket() : null;
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
    @XmlElement
    public FiltersConverter getFilterCollection() {
        if (expandLevel > 0) {
            if (entity.getFilterCollection() != null) {
                return new FiltersConverter(entity.getFilterCollection(), uri.resolve("filterCollection/"), expandLevel - 1);
            }
        }
        return null;
    }

    /**
     * Setter for filterCollection.
     *
     * @param value the value to set
     */
    public void setFilterCollection(FiltersConverter value) {
        entity.setFilterCollection((value != null) ? value.getEntities() : null);
    }

    /**
     * Returns the URI associated with this converter.
     *
     * @return the uri
     */
    @XmlAttribute
    public URI getUri() {
        return uri;
    }

    /**
     * Sets the URI for this reference converter.
     *
     */
    public void setUri(URI uri) {
        this.uri = uri;
    }

    /**
     * Returns the Timeframe entity.
     *
     * @return an entity
     */
    @XmlTransient
    public Timeframe getEntity() {
        if (entity.getId() == null) {
            TimeframeConverter converter = UriResolver.getInstance().resolve(TimeframeConverter.class, uri);
            if (converter != null) {
                entity = converter.getEntity();
            }
        }
        return entity;
    }

    /**
     * Returns the resolved Timeframe entity.
     *
     * @return an resolved entity
     */
    public Timeframe resolveEntity(EntityManager em) {
        Collection<Filter> filterCollection = entity.getFilterCollection();
        Collection<Filter> newfilterCollection = new java.util.ArrayList<Filter>();
        for (Filter item : filterCollection) {
            newfilterCollection.add(em.getReference(Filter.class, item.getId()));
        }
        entity.setFilterCollection(newfilterCollection);
        return entity;
    }
}
