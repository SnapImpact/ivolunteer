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

package converter.consolidated;

import java.net.URI;
import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.ArrayList;
import java.util.HashSet;
import persistence.Event;
import persistence.Organization;
import persistence.Timestamp;
import persistence.Location;
import persistence.Source;
import converter.list.EventRecordConverter;

/**
 * 
 * @author Dave Angulo
 */

@XmlRootElement(name = "events")
public class ConsolidatedEventsConverter {
	private Collection<Event>						entities;
	private Collection<EventRecordConverter>	items;
	private URI										uri;
	private int										expandLevel;
    private Collection<Organization>                orgs = new HashSet<Organization>();
    private Collection<Timestamp>                   timestamps = new HashSet<Timestamp>();
    private Collection<Location>                    locations = new HashSet<Location>();
    private Collection<Source>                      sources = new HashSet<Source>();

	/** Creates a new instance of ConsolidatedEventsConverter */
	public ConsolidatedEventsConverter() {
	}

	/**
	 * Creates a new instance of ConsolidatedEventsConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 * @param expandLevel
	 *            indicates the number of levels the entity graph should be
	 *            expanded
	 */
	public ConsolidatedEventsConverter(Collection<Event> entities, URI uri, int expandLevel) {
		this.entities = entities;
		this.uri = uri;
		this.expandLevel = expandLevel;
		getEvent();
	}

	/**
	 * Returns a collection of EventConverter.
	 * 
	 * @return a collection of EventConverter
	 */
	@XmlElement
	public Collection<EventRecordConverter> getEvent() {
		if (items == null) {
			items = new ArrayList<EventRecordConverter>();
		}
		if (entities != null) {
			for (Event entity : entities) {
				items.add(new EventRecordConverter(entity, uri, expandLevel, true));
                orgs.addAll(entity.getOrganizationCollection());
                timestamps.addAll(entity.getTimestampCollection());
                locations.addAll(entity.getLocationCollection());
                if ( entity.getSourceId() != null ) {
                    sources.add(entity.getSourceId());
                }
			}
		}
		return items;
	}

	/**
	 * Sets a collection of EventConverter.
	 * 
	 * @param a
	 *            collection of EventConverter to set
	 */
	public void setEvent(Collection<EventRecordConverter> items) {
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
	 * Returns a collection Event entities.
	 * 
	 * @return a collection of Event entities
	 */
	@XmlTransient
	public Collection<Event> getEntities() {
		entities = new ArrayList<Event>();
		if (items != null) {
			for (EventRecordConverter item : items) {
				entities.add(item.getEntity());
			}
		}
		return entities;
	}

    public Collection<Location> getLocations() {
        return locations;
    }

    public Collection<Organization> getOrgs() {
        return orgs;
    }

    public Collection<Source> getSources() {
        return sources;
    }

    public Collection<Timestamp> getTimestamps() {
        return timestamps;
    }


}
