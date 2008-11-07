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
import persistence.Source;

/**
 *
 * @author dave
 */

@XmlRootElement(name = "sources")
public class SourcesConverter {
    private Collection<Source> entities;
    private Collection<converter.SourceConverter> items;
    private URI uri;
    private int expandLevel;
  
    /** Creates a new instance of SourcesConverter */
    public SourcesConverter() {
    }

    /**
     * Creates a new instance of SourcesConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded
     */
    public SourcesConverter(Collection<Source> entities, URI uri, int expandLevel) {
        this.entities = entities;
        this.uri = uri;
        this.expandLevel = expandLevel;
        getSource();
    }

    /**
     * Returns a collection of SourceConverter.
     *
     * @return a collection of SourceConverter
     */
    @XmlElement
    public Collection<converter.SourceConverter> getSource() {
        if (items == null) {
            items = new ArrayList<SourceConverter>();
        }
        if (entities != null) {
            for (Source entity : entities) {
                items.add(new SourceConverter(entity, uri, expandLevel, true));
            }
        }
        return items;
    }

    /**
     * Sets a collection of SourceConverter.
     *
     * @param a collection of SourceConverter to set
     */
    public void setSource(Collection<converter.SourceConverter> items) {
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
     * Returns a collection Source entities.
     *
     * @return a collection of Source entities
     */
    @XmlTransient
    public Collection<Source> getEntities() {
        entities = new ArrayList<Source>();
        if (items != null) {
            for (SourceConverter item : items) {
                entities.add(item.getEntity());
            }
        }
        return entities;
    }
}
