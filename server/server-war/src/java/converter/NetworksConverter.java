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
import persistence.Network;

/**
 * 
 * @author Dave Angulo
 */

@XmlRootElement(name = "networks")
public class NetworksConverter {
	private Collection<Network>						entities;
	private Collection<converter.NetworkConverter>	items;
	private URI										uri;
	private int										expandLevel;

	/** Creates a new instance of NetworksConverter */
	public NetworksConverter() {
	}

	/**
	 * Creates a new instance of NetworksConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public NetworksConverter(Collection<Network> entities, URI uri, int expandLevel) {
		this.entities = entities;
		this.uri = uri;
		this.expandLevel = expandLevel;
		getNetwork();
	}

	/**
	 * Returns a collection of NetworkConverter.
	 * 
	 * @return a collection of NetworkConverter
	 */
	@XmlElement
	public Collection<converter.NetworkConverter> getNetwork() {
		if (items == null) {
			items = new ArrayList<NetworkConverter>();
		}
		if (entities != null) {
			for (Network entity : entities) {
				items.add(new NetworkConverter(entity, uri, expandLevel, true));
			}
		}
		return items;
	}

	/**
	 * Sets a collection of NetworkConverter.
	 * 
	 * @param a
	 *            collection of NetworkConverter to set
	 */
	public void setNetwork(Collection<converter.NetworkConverter> items) {
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
	 * Returns a collection Network entities.
	 * 
	 * @return a collection of Network entities
	 */
	@XmlTransient
	public Collection<Network> getEntities() {
		entities = new ArrayList<Network>();
		if (items != null) {
			for (NetworkConverter item : items) {
				entities.add(item.getEntity());
			}
		}
		return entities;
	}
}
