/*
 *  SourcesConverter
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
import persistence.Sources;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "sources")
public class SourcesConverter {
    private Collection<Sources> entities;
    private Collection<SourceRefConverter> references;
    private URI uri;
  
    /** Creates a new instance of SourcesConverter */
    public SourcesConverter() {
    }

    /**
     * Creates a new instance of SourcesConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public SourcesConverter(Collection<Sources> entities, URI uri) {
        this.entities = entities;
        this.uri = uri;
    }

    /**
     * Returns a collection of SourceRefConverter.
     *
     * @return a collection of SourceRefConverter
     */
    @XmlElement(name = "sourceRef")
    public Collection<SourceRefConverter> getReferences() {
        references = new ArrayList<SourceRefConverter>();
        if (entities != null) {
            for (Sources entity : entities) {
                references.add(new SourceRefConverter(entity, uri, true));
            }
        }
        return references;
    }

    /**
     * Sets a collection of SourceRefConverter.
     *
     * @param a collection of SourceRefConverter to set
     */
    public void setReferences(Collection<SourceRefConverter> references) {
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
     * Returns a collection Sources entities.
     *
     * @return a collection of Sources entities
     */
    @XmlTransient
    public Collection<Sources> getEntities() {
        entities = new ArrayList<Sources>();
        if (references != null) {
            for (SourceRefConverter ref : references) {
                entities.add(ref.getEntity());
            }
        }
        return entities;
    }
}
