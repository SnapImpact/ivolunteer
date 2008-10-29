/*
 *  OrganizationTypesConverter
 *
 * Created on October 24, 2008, 9:55 PM
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
import persistence.OrganizationTypes;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "organizationTypes")
public class OrganizationTypesConverter {
    private Collection<OrganizationTypes> entities;
    private Collection<OrganizationTypeRefConverter> references;
    private URI uri;
  
    /** Creates a new instance of OrganizationTypesConverter */
    public OrganizationTypesConverter() {
    }

    /**
     * Creates a new instance of OrganizationTypesConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public OrganizationTypesConverter(Collection<OrganizationTypes> entities, URI uri) {
        this.entities = entities;
        this.uri = uri;
    }

    /**
     * Returns a collection of OrganizationTypeRefConverter.
     *
     * @return a collection of OrganizationTypeRefConverter
     */
    @XmlElement(name = "organizationTypeRef")
    public Collection<OrganizationTypeRefConverter> getReferences() {
        references = new ArrayList<OrganizationTypeRefConverter>();
        if (entities != null) {
            for (OrganizationTypes entity : entities) {
                references.add(new OrganizationTypeRefConverter(entity, uri, true));
            }
        }
        return references;
    }

    /**
     * Sets a collection of OrganizationTypeRefConverter.
     *
     * @param a collection of OrganizationTypeRefConverter to set
     */
    public void setReferences(Collection<OrganizationTypeRefConverter> references) {
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
     * Returns a collection OrganizationTypes entities.
     *
     * @return a collection of OrganizationTypes entities
     */
    @XmlTransient
    public Collection<OrganizationTypes> getEntities() {
        entities = new ArrayList<OrganizationTypes>();
        if (references != null) {
            for (OrganizationTypeRefConverter ref : references) {
                entities.add(ref.getEntity());
            }
        }
        return entities;
    }
}
