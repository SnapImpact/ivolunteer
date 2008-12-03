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
import persistence.IvUser;

/**
 * 
 * @author dave
 */

@XmlRootElement(name = "ivUsers")
public class IvUsersConverter {
	private Collection<IvUser>						entities;
	private Collection<converter.IvUserConverter>	items;
	private URI										uri;
	private int										expandLevel;

	/** Creates a new instance of IvUsersConverter */
	public IvUsersConverter() {
	}

	/**
	 * Creates a new instance of IvUsersConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public IvUsersConverter(Collection<IvUser> entities, URI uri, int expandLevel) {
		this.entities = entities;
		this.uri = uri;
		this.expandLevel = expandLevel;
		getIvUser();
	}

	/**
	 * Returns a collection of IvUserConverter.
	 * 
	 * @return a collection of IvUserConverter
	 */
	@XmlElement
	public Collection<converter.IvUserConverter> getIvUser() {
		if (items == null) {
			items = new ArrayList<IvUserConverter>();
		}
		if (entities != null) {
			for (IvUser entity : entities) {
				items.add(new IvUserConverter(entity, uri, expandLevel, true));
			}
		}
		return items;
	}

	/**
	 * Sets a collection of IvUserConverter.
	 * 
	 * @param a
	 *            collection of IvUserConverter to set
	 */
	public void setIvUser(Collection<converter.IvUserConverter> items) {
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
	 * Returns a collection IvUser entities.
	 * 
	 * @return a collection of IvUser entities
	 */
	@XmlTransient
	public Collection<IvUser> getEntities() {
		entities = new ArrayList<IvUser>();
		if (items != null) {
			for (IvUserConverter item : items) {
				entities.add(item.getEntity());
			}
		}
		return entities;
	}
}
