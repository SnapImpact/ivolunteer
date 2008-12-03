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
import persistence.Location;

/**
 * 
 * @author dave
 */

@XmlRootElement(name = "locations")
public class LocationsConverter {
	private Collection<Location>					entities;
	private Collection<converter.LocationConverter>	items;
	private URI										uri;
	private int										expandLevel;

	/** Creates a new instance of LocationsConverter */
	public LocationsConverter() {
	}

	/**
	 * Creates a new instance of LocationsConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public LocationsConverter(Collection<Location> entities, URI uri, int expandLevel) {
		this.entities = entities;
		this.uri = uri;
		this.expandLevel = expandLevel;
		getLocation();
	}

	/**
	 * Returns a collection of LocationConverter.
	 * 
	 * @return a collection of LocationConverter
	 */
	@XmlElement
	public Collection<converter.LocationConverter> getLocation() {
		if (items == null) {
			items = new ArrayList<LocationConverter>();
		}
		if (entities != null) {
			for (Location entity : entities) {
				items.add(new LocationConverter(entity, uri, expandLevel, true));
			}
		}
		return items;
	}

	/**
	 * Sets a collection of LocationConverter.
	 * 
	 * @param a
	 *            collection of LocationConverter to set
	 */
	public void setLocation(Collection<converter.LocationConverter> items) {
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
	 * Returns a collection Location entities.
	 * 
	 * @return a collection of Location entities
	 */
	@XmlTransient
	public Collection<Location> getEntities() {
		entities = new ArrayList<Location>();
		if (items != null) {
			for (LocationConverter item : items) {
				entities.add(item.getEntity());
			}
		}
		return entities;
	}
}
