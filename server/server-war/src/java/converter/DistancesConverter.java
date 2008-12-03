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
import persistence.Distance;

/**
 * 
 * @author dave
 */

@XmlRootElement(name = "distances")
public class DistancesConverter {
	private Collection<Distance>					entities;
	private Collection<converter.DistanceConverter>	items;
	private URI										uri;
	private int										expandLevel;

	/** Creates a new instance of DistancesConverter */
	public DistancesConverter() {
	}

	/**
	 * Creates a new instance of DistancesConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public DistancesConverter(Collection<Distance> entities, URI uri, int expandLevel) {
		this.entities = entities;
		this.uri = uri;
		this.expandLevel = expandLevel;
		getDistance();
	}

	/**
	 * Returns a collection of DistanceConverter.
	 * 
	 * @return a collection of DistanceConverter
	 */
	@XmlElement
	public Collection<converter.DistanceConverter> getDistance() {
		if (items == null) {
			items = new ArrayList<DistanceConverter>();
		}
		if (entities != null) {
			for (Distance entity : entities) {
				items.add(new DistanceConverter(entity, uri, expandLevel, true));
			}
		}
		return items;
	}

	/**
	 * Sets a collection of DistanceConverter.
	 * 
	 * @param a
	 *            collection of DistanceConverter to set
	 */
	public void setDistance(Collection<converter.DistanceConverter> items) {
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
	 * Returns a collection Distance entities.
	 * 
	 * @return a collection of Distance entities
	 */
	@XmlTransient
	public Collection<Distance> getEntities() {
		entities = new ArrayList<Distance>();
		if (items != null) {
			for (DistanceConverter item : items) {
				entities.add(item.getEntity());
			}
		}
		return entities;
	}
}
