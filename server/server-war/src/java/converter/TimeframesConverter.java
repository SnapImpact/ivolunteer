/*
 *  TimeframesConverter
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
import persistence.Timeframes;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "timeframes")
public class TimeframesConverter {
    private Collection<Timeframes> entities;
    private Collection<TimeframeRefConverter> references;
    private URI uri;
  
    /** Creates a new instance of TimeframesConverter */
    public TimeframesConverter() {
    }

    /**
     * Creates a new instance of TimeframesConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public TimeframesConverter(Collection<Timeframes> entities, URI uri) {
        this.entities = entities;
        this.uri = uri;
    }

    /**
     * Returns a collection of TimeframeRefConverter.
     *
     * @return a collection of TimeframeRefConverter
     */
    @XmlElement(name = "timeframeRef")
    public Collection<TimeframeRefConverter> getReferences() {
        references = new ArrayList<TimeframeRefConverter>();
        if (entities != null) {
            for (Timeframes entity : entities) {
                references.add(new TimeframeRefConverter(entity, uri, true));
            }
        }
        return references;
    }

    /**
     * Sets a collection of TimeframeRefConverter.
     *
     * @param a collection of TimeframeRefConverter to set
     */
    public void setReferences(Collection<TimeframeRefConverter> references) {
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
     * Returns a collection Timeframes entities.
     *
     * @return a collection of Timeframes entities
     */
    @XmlTransient
    public Collection<Timeframes> getEntities() {
        entities = new ArrayList<Timeframes>();
        if (references != null) {
            for (TimeframeRefConverter ref : references) {
                entities.add(ref.getEntity());
            }
        }
        return entities;
    }
}
