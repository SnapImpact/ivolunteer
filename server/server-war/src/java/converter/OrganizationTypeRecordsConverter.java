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
import persistence.OrganizationType;

/**
 * 
 * @author dave
 */

@XmlRootElement(name = "records")
public class OrganizationTypeRecordsConverter {
	private Collection<OrganizationType>	records;
	private URI								uri;
	private URI								baseUri;

	/** Creates a new instance of OrganizationsConverter */
	public OrganizationTypeRecordsConverter() {
	}

	/**
	 * Creates a new instance of OrganizationsConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 */
	public OrganizationTypeRecordsConverter(Collection<OrganizationType> records, URI uri,
			URI baseUri) {
		this.records = records;
		this.uri = uri;
		this.baseUri = baseUri;
	}

	@XmlElement
	public ArrayList<OrganizationTypeRecordConverter> getRecords() {
		ArrayList<OrganizationTypeRecordConverter> ret = new ArrayList<OrganizationTypeRecordConverter>();
		if (records != null) {
			for (OrganizationType record : records) {
				ret.add(new OrganizationTypeRecordConverter(record, baseUri
						.resolve("organizationType/" + record.getId() + "/"), 1));
			}
		}
		return ret;
	}

}
