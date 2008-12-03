/*
 *  OrganizationsRecordsConverter
 *
 * Created on October 6, 2008, 8:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package converter;

import java.net.URI;
import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import persistence.Timestamp;

/**
 * 
 * @author dave
 */

@XmlRootElement(name = "records")
public class TimestampRecordsConverter {
	private Collection<Timestamp>	records;
	private URI						uri;
	private URI						baseUri;

	/** Creates a new instance of OrganizationsConverter */
	public TimestampRecordsConverter() {
	}

	/**
	 * Creates a new instance of OrganizationsConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 */
	public TimestampRecordsConverter(Collection<Timestamp> records, URI uri, URI baseUri) {
		this.records = records;
		this.uri = uri;
		this.baseUri = baseUri;
	}

	@XmlElement
	public ArrayList<TimestampRecordConverter> getRecords() {
		ArrayList<TimestampRecordConverter> ret = new ArrayList<TimestampRecordConverter>();
		if (records != null) {
			for (Timestamp record : records) {
				ret.add(new TimestampRecordConverter(record, baseUri.resolve("timestamp/"
						+ record.getId() + "/"), 1));
			}
		}
		return ret;
	}

}
