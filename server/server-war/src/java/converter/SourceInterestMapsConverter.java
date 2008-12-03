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
import persistence.SourceInterestMap;

/**
 * 
 * @author dave
 */

@XmlRootElement(name = "sourceInterestMaps")
public class SourceInterestMapsConverter {
	private Collection<SourceInterestMap>						entities;
	private Collection<converter.SourceInterestMapConverter>	items;
	private URI													uri;
	private int													expandLevel;

	/** Creates a new instance of SourceInterestMapsConverter */
	public SourceInterestMapsConverter() {
	}

	/**
	 * Creates a new instance of SourceInterestMapsConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public SourceInterestMapsConverter(Collection<SourceInterestMap> entities, URI uri,
			int expandLevel) {
		this.entities = entities;
		this.uri = uri;
		this.expandLevel = expandLevel;
		getSourceInterestMap();
	}

	/**
	 * Returns a collection of SourceInterestMapConverter.
	 * 
	 * @return a collection of SourceInterestMapConverter
	 */
	@XmlElement
	public Collection<converter.SourceInterestMapConverter> getSourceInterestMap() {
		if (items == null) {
			items = new ArrayList<SourceInterestMapConverter>();
		}
		if (entities != null) {
			for (SourceInterestMap entity : entities) {
				items.add(new SourceInterestMapConverter(entity, uri, expandLevel, true));
			}
		}
		return items;
	}

	/**
	 * Sets a collection of SourceInterestMapConverter.
	 * 
	 * @param a
	 *            collection of SourceInterestMapConverter to set
	 */
	public void setSourceInterestMap(Collection<converter.SourceInterestMapConverter> items) {
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
	 * Returns a collection SourceInterestMap entities.
	 * 
	 * @return a collection of SourceInterestMap entities
	 */
	@XmlTransient
	public Collection<SourceInterestMap> getEntities() {
		entities = new ArrayList<SourceInterestMap>();
		if (items != null) {
			for (SourceInterestMapConverter item : items) {
				entities.add(item.getEntity());
			}
		}
		return entities;
	}
}
