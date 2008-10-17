/*
 *  EventsConverter
 *
 * Created on October 11, 2008, 9:18 PM
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
import persistence.Events;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "events")
public class EventsConverter {
    private Collection<Events> entities;
    private Collection<EventRefConverter> references;
    private URI uri;
  
    /** Creates a new instance of EventsConverter */
    public EventsConverter() {
    }

    /**
     * Creates a new instance of EventsConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public EventsConverter(Collection<Events> entities, URI uri) {
        this.entities = entities;
        this.uri = uri;
    }

    /**
     * Returns a collection of EventRefConverter.
     *
     * @return a collection of EventRefConverter
     */
    @XmlElement(name = "eventRef")
    public Collection<EventRefConverter> getReferences() {
        references = new ArrayList<EventRefConverter>();
        if (entities != null) {
            for (Events entity : entities) {
                references.add(new EventRefConverter(entity, uri, true));
            }
        }
        return references;
    }

    /**
     * Sets a collection of EventRefConverter.
     *
     * @param a collection of EventRefConverter to set
     */
    public void setReferences(Collection<EventRefConverter> references) {
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
     * Returns a collection Events entities.
     *
     * @return a collection of Events entities
     */
    @XmlTransient
    public Collection<Events> getEntities() {
        entities = new ArrayList<Events>();
        if (references != null) {
            for (EventRefConverter ref : references) {
                entities.add(ref.getEntity());
            }
        }
        return entities;
    }
}
