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
import persistence.Timeframe;

/**
 * 
 * @author dave
 */

@XmlRootElement(name = "timeframes")
public class TimeframesConverter {
	private Collection<Timeframe>						entities;
	private Collection<converter.TimeframeConverter>	items;
	private URI											uri;
	private int											expandLevel;

	/** Creates a new instance of TimeframesConverter */
	public TimeframesConverter() {
	}

	/**
	 * Creates a new instance of TimeframesConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public TimeframesConverter(Collection<Timeframe> entities, URI uri, int expandLevel) {
		this.entities = entities;
		this.uri = uri;
		this.expandLevel = expandLevel;
		getTimeframe();
	}

	/**
	 * Returns a collection of TimeframeConverter.
	 * 
	 * @return a collection of TimeframeConverter
	 */
	@XmlElement
	public Collection<converter.TimeframeConverter> getTimeframe() {
		if (items == null) {
			items = new ArrayList<TimeframeConverter>();
		}
		if (entities != null) {
			for (Timeframe entity : entities) {
				items.add(new TimeframeConverter(entity, uri, expandLevel, true));
			}
		}
		return items;
	}

	/**
	 * Sets a collection of TimeframeConverter.
	 * 
	 * @param a
	 *            collection of TimeframeConverter to set
	 */
	public void setTimeframe(Collection<converter.TimeframeConverter> items) {
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
	 * Returns a collection Timeframe entities.
	 * 
	 * @return a collection of Timeframe entities
	 */
	@XmlTransient
	public Collection<Timeframe> getEntities() {
		entities = new ArrayList<Timeframe>();
		if (items != null) {
			for (TimeframeConverter item : items) {
				entities.add(item.getEntity());
			}
		}
		return entities;
	}
}
