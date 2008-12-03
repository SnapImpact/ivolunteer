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
import persistence.Timestamp;
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
public class TimestampListConverter {
	private IdListConverter				idListConverter;
	private TimestampRecordsConverter	timestampRecordsConverter;

	/** Creates a new instance of OrganizationsConverter */
	public TimestampListConverter() {
	}

	/**
	 * Creates a new instance of OrganizationsConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 */
	public TimestampListConverter(Collection<Timestamp> records, URI uri, URI baseUri) {
		this.idListConverter = new IdListConverter(records);
		this.timestampRecordsConverter = new TimestampRecordsConverter(records, uri, baseUri);
	}

	@XmlElement(name = "ids")
	public Collection<String> getIdListConverter() {
		return idListConverter.getIds();
	}

	@XmlElement(name = "records")
	public Collection<TimestampRecordConverter> getEventsRecordsConverter() {
		return timestampRecordsConverter.getRecords();
	}
}
