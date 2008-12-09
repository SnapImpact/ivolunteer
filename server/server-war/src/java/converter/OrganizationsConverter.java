/*
 *  Copyright (c) 2008 Boulder Community Foundation - iVolunteer
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
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
 * @author Dave Angulo
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
