/*
 *  OrganizationsListConverter
 *
 * Created on October 6, 2008, 8:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package converter;

import persistence.IdInterface;
import persistence.Event;
import java.net.URI;
import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author dave
 */

@XmlRootElement(name = "events")
public class EventListConverter {
	private IdListConverter			idListConverter;
	private EventRecordsConverter	eventsRecordsConverter;

	/** Creates a new instance of OrganizationsConverter */
	public EventListConverter() {
	}

	/**
	 * Creates a new instance of OrganizationsConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 */
	public EventListConverter(Collection<Event> records, URI uri, URI baseUri) {
		this.idListConverter = new IdListConverter(records);
		this.eventsRecordsConverter = new EventRecordsConverter(records, uri, baseUri);
	}

	@XmlElement(name = "ids")
	public Collection<String> getIdListConverter() {
		return idListConverter.getIds();
	}

	@XmlElement(name = "records")
	public Collection<EventRecordConverter> getEventsRecordsConverter() {
		return eventsRecordsConverter.getRecords();
	}
}
