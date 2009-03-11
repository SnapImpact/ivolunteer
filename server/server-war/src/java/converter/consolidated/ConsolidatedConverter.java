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

import converter.list.*;
import converter.SourcesConverter;
import converter.SourceConverter;
import persistence.Event;
import java.net.URI;
import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Dave Angulo
 */

@XmlRootElement(name = "eventsConsolidated")
public class ConsolidatedConverter {
	private IdListConverter			idListConverter;
	private ConsolidatedEventsConverter	consolidatedEventsConverter;
    private URI                     uri;
    private URI                     baseUri;
    private int                     expandLevel;

	/** Creates a new instance of EventListConverter */
	public ConsolidatedConverter() {
	}

	/**
	 * Creates a new instance of EventListConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 */
	public ConsolidatedConverter(Collection<Event> entities, URI uri, URI baseUri, int expandLevel) {
		this.consolidatedEventsConverter = new ConsolidatedEventsConverter(entities, uri, expandLevel);
        this.uri = uri;
        this.baseUri = baseUri;
        this.expandLevel = expandLevel;
	}

	@XmlElement(name = "organizations")
	public Collection<OrganizationRecordConverter> getOrganizationsConverter() {
		return new OrganizationRecordsConverter(consolidatedEventsConverter.getOrgs(), uri, baseUri ).getRecords();
	}

    @XmlElement(name = "timestamps")
    public Collection<TimestampRecordConverter> getTimestampsConverter() {
        return new TimestampRecordsConverter(consolidatedEventsConverter.getTimestamps(), uri, baseUri ).getRecords();
    }

    @XmlElement(name= "sources")
    public Collection<SourceRecordConverter> getSourcesConverter() {
        return new SourceRecordsConverter(consolidatedEventsConverter.getSources(), uri, baseUri ).getRecords();
    }

    @XmlElement(name = "locations")
    public Collection<LocationRecordConverter> getLocationsConverter() {
        return new LocationRecordsConverter(consolidatedEventsConverter.getLocations(), uri, baseUri ).getRecords();
    }

	@XmlElement(name = "events")
	public Collection<EventRecordConverter> getEventsRecordsConverter() {
		return consolidatedEventsConverter.getEvent();
	}
}
