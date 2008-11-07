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
import persistence.OrganizationType;

/**
 *
 * @author dave
 */

@XmlRootElement(name = "organizationTypes")
public class OrganizationTypesConverter {
    private Collection<OrganizationType> entities;
    private Collection<converter.OrganizationTypeConverter> items;
    private URI uri;
    private int expandLevel;
  
    /** Creates a new instance of OrganizationTypesConverter */
    public OrganizationTypesConverter() {
    }

    /**
     * Creates a new instance of OrganizationTypesConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded
     */
    public OrganizationTypesConverter(Collection<OrganizationType> entities, URI uri, int expandLevel) {
        this.entities = entities;
        this.uri = uri;
        this.expandLevel = expandLevel;
        getOrganizationType();
    }

    /**
     * Returns a collection of OrganizationTypeConverter.
     *
     * @return a collection of OrganizationTypeConverter
     */
    @XmlElement
    public Collection<converter.OrganizationTypeConverter> getOrganizationType() {
        if (items == null) {
            items = new ArrayList<OrganizationTypeConverter>();
        }
        if (entities != null) {
            for (OrganizationType entity : entities) {
                items.add(new OrganizationTypeConverter(entity, uri, expandLevel, true));
            }
        }
        return items;
    }

    /**
     * Sets a collection of OrganizationTypeConverter.
     *
     * @param a collection of OrganizationTypeConverter to set
     */
    public void setOrganizationType(Collection<converter.OrganizationTypeConverter> items) {
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
     * Returns a collection OrganizationType entities.
     *
     * @return a collection of OrganizationType entities
     */
    @XmlTransient
    public Collection<OrganizationType> getEntities() {
        entities = new ArrayList<OrganizationType>();
        if (items != null) {
            for (OrganizationTypeConverter item : items) {
                entities.add(item.getEntity());
            }
        }
        return entities;
    }
}
