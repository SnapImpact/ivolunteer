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
import persistence.Filter;

/**
 * 
 * @author Dave Angulo
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
