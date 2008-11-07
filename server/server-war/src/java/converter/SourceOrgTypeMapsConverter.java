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
import persistence.SourceOrgTypeMap;

/**
 *
 * @author dave
 */

@XmlRootElement(name = "sourceOrgTypeMaps")
public class SourceOrgTypeMapsConverter {
    private Collection<SourceOrgTypeMap> entities;
    private Collection<converter.SourceOrgTypeMapConverter> items;
    private URI uri;
    private int expandLevel;
  
    /** Creates a new instance of SourceOrgTypeMapsConverter */
    public SourceOrgTypeMapsConverter() {
    }

    /**
     * Creates a new instance of SourceOrgTypeMapsConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded
     */
    public SourceOrgTypeMapsConverter(Collection<SourceOrgTypeMap> entities, URI uri, int expandLevel) {
        this.entities = entities;
        this.uri = uri;
        this.expandLevel = expandLevel;
        getSourceOrgTypeMap();
    }

    /**
     * Returns a collection of SourceOrgTypeMapConverter.
     *
     * @return a collection of SourceOrgTypeMapConverter
     */
    @XmlElement
    public Collection<converter.SourceOrgTypeMapConverter> getSourceOrgTypeMap() {
        if (items == null) {
            items = new ArrayList<SourceOrgTypeMapConverter>();
        }
        if (entities != null) {
            for (SourceOrgTypeMap entity : entities) {
                items.add(new SourceOrgTypeMapConverter(entity, uri, expandLevel, true));
            }
        }
        return items;
    }

    /**
     * Sets a collection of SourceOrgTypeMapConverter.
     *
     * @param a collection of SourceOrgTypeMapConverter to set
     */
    public void setSourceOrgTypeMap(Collection<converter.SourceOrgTypeMapConverter> items) {
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
     * Returns a collection SourceOrgTypeMap entities.
     *
     * @return a collection of SourceOrgTypeMap entities
     */
    @XmlTransient
    public Collection<SourceOrgTypeMap> getEntities() {
        entities = new ArrayList<SourceOrgTypeMap>();
        if (items != null) {
            for (SourceOrgTypeMapConverter item : items) {
                entities.add(item.getEntity());
            }
        }
        return entities;
    }
}
