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
import persistence.Timestamp;

/**
 * 
 * @author dave
 */

@XmlRootElement(name = "timestamps")
public class TimestampsConverter {
	private Collection<Timestamp>						entities;
	private Collection<converter.TimestampConverter>	items;
	private URI											uri;
	private int											expandLevel;

	/** Creates a new instance of TimestampsConverter */
	public TimestampsConverter() {
	}

	/**
	 * Creates a new instance of TimestampsConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public TimestampsConverter(Collection<Timestamp> entities, URI uri, int expandLevel) {
		this.entities = entities;
		this.uri = uri;
		this.expandLevel = expandLevel;
		getTimestamp();
	}

	/**
	 * Returns a collection of TimestampConverter.
	 * 
	 * @return a collection of TimestampConverter
	 */
	@XmlElement
	public Collection<converter.TimestampConverter> getTimestamp() {
		if (items == null) {
			items = new ArrayList<TimestampConverter>();
		}
		if (entities != null) {
			for (Timestamp entity : entities) {
				items.add(new TimestampConverter(entity, uri, expandLevel, true));
			}
		}
		return items;
	}

	/**
	 * Sets a collection of TimestampConverter.
	 * 
	 * @param a
	 *            collection of TimestampConverter to set
	 */
	public void setTimestamp(Collection<converter.TimestampConverter> items) {
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
	 * Returns a collection Timestamp entities.
	 * 
	 * @return a collection of Timestamp entities
	 */
	@XmlTransient
	public Collection<Timestamp> getEntities() {
		entities = new ArrayList<Timestamp>();
		if (items != null) {
			for (TimestampConverter item : items) {
				entities.add(item.getEntity());
			}
		}
		return entities;
	}
}
