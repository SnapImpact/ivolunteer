/*
 *  FiltersConverter
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
import persistence.Filter;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "filters")
public class FiltersConverter {
    private Collection<Filter> entities;
    private Collection<FilterRefConverter> references;
    private URI uri;
  
    /** Creates a new instance of FiltersConverter */
    public FiltersConverter() {
    }

    /**
     * Creates a new instance of FiltersConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public FiltersConverter(Collection<Filter> entities, URI uri) {
        this.entities = entities;
        this.uri = uri;
    }

    /**
     * Returns a collection of FilterRefConverter.
     *
     * @return a collection of FilterRefConverter
     */
    @XmlElement(name = "filterRef")
    public Collection<FilterRefConverter> getReferences() {
        references = new ArrayList<FilterRefConverter>();
        if (entities != null) {
            for (Filter entity : entities) {
                references.add(new FilterRefConverter(entity, uri, true));
            }
        }
        return references;
    }

    /**
     * Sets a collection of FilterRefConverter.
     *
     * @param a collection of FilterRefConverter to set
     */
    public void setReferences(Collection<FilterRefConverter> references) {
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
     * Returns a collection Filter entities.
     *
     * @return a collection of Filter entities
     */
    @XmlTransient
    public Collection<Filter> getEntities() {
        entities = new ArrayList<Filter>();
        if (references != null) {
            for (FilterRefConverter ref : references) {
                entities.add(ref.getEntity());
            }
        }
        return entities;
    }
}
