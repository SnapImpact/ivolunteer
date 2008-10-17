/*
 *  OrganizationsConverter
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
import persistence.Organizations;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "organizations")
public class OrganizationsConverter {
    private Collection<Organizations> entities;
    private Collection<OrganizationRefConverter> references;
    private URI uri;
  
    /** Creates a new instance of OrganizationsConverter */
    public OrganizationsConverter() {
    }

    /**
     * Creates a new instance of OrganizationsConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public OrganizationsConverter(Collection<Organizations> entities, URI uri) {
        this.entities = entities;
        this.uri = uri;
    }

    /**
     * Returns a collection of OrganizationRefConverter.
     *
     * @return a collection of OrganizationRefConverter
     */
    @XmlElement(name = "organizationRef")
    public Collection<OrganizationRefConverter> getReferences() {
        references = new ArrayList<OrganizationRefConverter>();
        if (entities != null) {
            for (Organizations entity : entities) {
                references.add(new OrganizationRefConverter(entity, uri, true));
            }
        }
        return references;
    }

    /**
     * Sets a collection of OrganizationRefConverter.
     *
     * @param a collection of OrganizationRefConverter to set
     */
    public void setReferences(Collection<OrganizationRefConverter> references) {
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
     * Returns a collection Organizations entities.
     *
     * @return a collection of Organizations entities
     */
    @XmlTransient
    public Collection<Organizations> getEntities() {
        entities = new ArrayList<Organizations>();
        if (references != null) {
            for (OrganizationRefConverter ref : references) {
                entities.add(ref.getEntity());
            }
        }
        return entities;
    }
}
