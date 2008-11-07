/*
 * To change this template, choose Tools | Templates
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
import persistence.InterestArea;

/**
 *
 * @author dave
 */

@XmlRootElement(name = "interestAreas")
public class InterestAreasConverter {
    private Collection<InterestArea> entities;
    private Collection<converter.InterestAreaConverter> items;
    private URI uri;
    private int expandLevel;
  
    /** Creates a new instance of InterestAreasConverter */
    public InterestAreasConverter() {
    }

    /**
     * Creates a new instance of InterestAreasConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded
     */
    public InterestAreasConverter(Collection<InterestArea> entities, URI uri, int expandLevel) {
        this.entities = entities;
        this.uri = uri;
        this.expandLevel = expandLevel;
        getInterestArea();
    }

    /**
     * Returns a collection of InterestAreaConverter.
     *
     * @return a collection of InterestAreaConverter
     */
    @XmlElement
    public Collection<converter.InterestAreaConverter> getInterestArea() {
        if (items == null) {
            items = new ArrayList<InterestAreaConverter>();
        }
        if (entities != null) {
            for (InterestArea entity : entities) {
                items.add(new InterestAreaConverter(entity, uri, expandLevel, true));
            }
        }
        return items;
    }

    /**
     * Sets a collection of InterestAreaConverter.
     *
     * @param a collection of InterestAreaConverter to set
     */
    public void setInterestArea(Collection<converter.InterestAreaConverter> items) {
        this.items = items;
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
     * Returns a collection InterestArea entities.
     *
     * @return a collection of InterestArea entities
     */
    @XmlTransient
    public Collection<InterestArea> getEntities() {
        entities = new ArrayList<InterestArea>();
        if (items != null) {
            for (InterestAreaConverter item : items) {
                entities.add(item.getEntity());
            }
        }
        return entities;
    }
}
