/*
 *  IntegrationsConverter
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
import persistence.Integrations;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "integrations")
public class IntegrationsConverter {
    private Collection<Integrations> entities;
    private Collection<IntegrationRefConverter> references;
    private URI uri;
  
    /** Creates a new instance of IntegrationsConverter */
    public IntegrationsConverter() {
    }

    /**
     * Creates a new instance of IntegrationsConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public IntegrationsConverter(Collection<Integrations> entities, URI uri) {
        this.entities = entities;
        this.uri = uri;
    }

    /**
     * Returns a collection of IntegrationRefConverter.
     *
     * @return a collection of IntegrationRefConverter
     */
    @XmlElement(name = "integrationRef")
    public Collection<IntegrationRefConverter> getReferences() {
        references = new ArrayList<IntegrationRefConverter>();
        if (entities != null) {
            for (Integrations entity : entities) {
                references.add(new IntegrationRefConverter(entity, uri, true));
            }
        }
        return references;
    }

    /**
     * Sets a collection of IntegrationRefConverter.
     *
     * @param a collection of IntegrationRefConverter to set
     */
    public void setReferences(Collection<IntegrationRefConverter> references) {
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
     * Returns a collection Integrations entities.
     *
     * @return a collection of Integrations entities
     */
    @XmlTransient
    public Collection<Integrations> getEntities() {
        entities = new ArrayList<Integrations>();
        if (references != null) {
            for (IntegrationRefConverter ref : references) {
                entities.add(ref.getEntity());
            }
        }
        return entities;
    }
}
