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
import persistence.Organization;

/**
 * 
 * @author dave
 */

@XmlRootElement(name = "organizations")
public class OrganizationsConverter {
	private Collection<Organization>					entities;
	private Collection<converter.OrganizationConverter>	items;
	private URI											uri;
	private int											expandLevel;

	/** Creates a new instance of OrganizationsConverter */
	public OrganizationsConverter() {
	}

	/**
	 * Creates a new instance of OrganizationsConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public OrganizationsConverter(Collection<Organization> entities, URI uri, int expandLevel) {
		this.entities = entities;
		this.uri = uri;
		this.expandLevel = expandLevel;
		getOrganization();
	}

	/**
	 * Returns a collection of OrganizationConverter.
	 * 
	 * @return a collection of OrganizationConverter
	 */
	@XmlElement
	public Collection<converter.OrganizationConverter> getOrganization() {
		if (items == null) {
			items = new ArrayList<OrganizationConverter>();
		}
		if (entities != null) {
			for (Organization entity : entities) {
				items.add(new OrganizationConverter(entity, uri, expandLevel, true));
			}
		}
		return items;
	}

	/**
	 * Sets a collection of OrganizationConverter.
	 * 
	 * @param a
	 *            collection of OrganizationConverter to set
	 */
	public void setOrganization(Collection<converter.OrganizationConverter> items) {
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
	 * Returns a collection Organization entities.
	 * 
	 * @return a collection of Organization entities
	 */
	@XmlTransient
	public Collection<Organization> getEntities() {
		entities = new ArrayList<Organization>();
		if (items != null) {
			for (OrganizationConverter item : items) {
				entities.add(item.getEntity());
			}
		}
		return entities;
	}
}
