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
import persistence.Filter;

/**
 * 
 * @author dave
 */

@XmlRootElement(name = "filters")
public class FiltersConverter {
	private Collection<Filter>						entities;
	private Collection<converter.FilterConverter>	items;
	private URI										uri;
	private int										expandLevel;

	/** Creates a new instance of FiltersConverter */
	public FiltersConverter() {
	}

	/**
	 * Creates a new instance of FiltersConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public FiltersConverter(Collection<Filter> entities, URI uri, int expandLevel) {
		this.entities = entities;
		this.uri = uri;
		this.expandLevel = expandLevel;
		getFilter();
	}

	/**
	 * Returns a collection of FilterConverter.
	 * 
	 * @return a collection of FilterConverter
	 */
	@XmlElement
	public Collection<converter.FilterConverter> getFilter() {
		if (items == null) {
			items = new ArrayList<FilterConverter>();
		}
		if (entities != null) {
			for (Filter entity : entities) {
				items.add(new FilterConverter(entity, uri, expandLevel, true));
			}
		}
		return items;
	}

	/**
	 * Sets a collection of FilterConverter.
	 * 
	 * @param a
	 *            collection of FilterConverter to set
	 */
	public void setFilter(Collection<converter.FilterConverter> items) {
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
	 * Returns a collection Filter entities.
	 * 
	 * @return a collection of Filter entities
	 */
	@XmlTransient
	public Collection<Filter> getEntities() {
		entities = new ArrayList<Filter>();
		if (items != null) {
			for (FilterConverter item : items) {
				entities.add(item.getEntity());
			}
		}
		return entities;
	}
}
