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
import persistence.Distance;

/**
 * 
 * @author dave
 */

@XmlRootElement(name = "records")
public class DistanceRecordsConverter {
	private Collection<Distance>	records;
	private URI						uri;
	private URI						baseUri;

	/** Creates a new instance of OrganizationsConverter */
	public DistanceRecordsConverter() {
	}

	/**
	 * Creates a new instance of OrganizationsConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 */
	public DistanceRecordsConverter(Collection<Distance> records, URI uri, URI baseUri) {
		this.records = records;
		this.uri = uri;
		this.baseUri = baseUri;
	}

	@XmlElement
	public ArrayList<DistanceRecordConverter> getRecords() {
		ArrayList<DistanceRecordConverter> ret = new ArrayList<DistanceRecordConverter>();
		if (records != null) {
			for (Distance record : records) {
				ret.add(new DistanceRecordConverter(record, baseUri.resolve("location/"
						+ record.getId() + "/"), 1));
			}
		}
		return ret;
	}

}
