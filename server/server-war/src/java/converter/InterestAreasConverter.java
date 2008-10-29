/*
 *  InterestAreasConverter
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
import persistence.InterestAreas;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "interestAreas")
public class InterestAreasConverter {
    private Collection<InterestAreas> entities;
    private Collection<InterestAreaRefConverter> references;
    private URI uri;
  
    /** Creates a new instance of InterestAreasConverter */
    public InterestAreasConverter() {
    }

    /**
     * Creates a new instance of InterestAreasConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public InterestAreasConverter(Collection<InterestAreas> entities, URI uri) {
        this.entities = entities;
        this.uri = uri;
    }

    /**
     * Returns a collection of InterestAreaRefConverter.
     *
     * @return a collection of InterestAreaRefConverter
     */
    @XmlElement(name = "interestAreaRef")
    public Collection<InterestAreaRefConverter> getReferences() {
        references = new ArrayList<InterestAreaRefConverter>();
        if (entities != null) {
            for (InterestAreas entity : entities) {
                references.add(new InterestAreaRefConverter(entity, uri, true));
            }
        }
        return references;
    }

    /**
     * Sets a collection of InterestAreaRefConverter.
     *
     * @param a collection of InterestAreaRefConverter to set
     */
    public void setReferences(Collection<InterestAreaRefConverter> references) {
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
     * Returns a collection InterestAreas entities.
     *
     * @return a collection of InterestAreas entities
     */
    @XmlTransient
    public Collection<InterestAreas> getEntities() {
        entities = new ArrayList<InterestAreas>();
        if (references != null) {
            for (InterestAreaRefConverter ref : references) {
                entities.add(ref.getEntity());
            }
        }
        return entities;
    }
}
