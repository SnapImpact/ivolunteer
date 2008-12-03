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
import persistence.OrganizationType;
import java.net.URI;
import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author dave
 */

@XmlRootElement(name = "timeframes")
public class OrganizationTypeListConverter {
	private IdListConverter						idListConverter;
	private OrganizationTypeRecordsConverter	recordsConverter;

	/** Creates a new instance of OrganizationsConverter */
	public OrganizationTypeListConverter() {
	}

	/**
	 * Creates a new instance of OrganizationsConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 */
	public OrganizationTypeListConverter(Collection<OrganizationType> records, URI uri, URI baseUri) {
		this.idListConverter = new IdListConverter(records);
		this.recordsConverter = new OrganizationTypeRecordsConverter(records, uri, baseUri);
	}

	@XmlElement(name = "ids")
	public Collection<String> getIdListConverter() {
		return idListConverter.getIds();
	}

	@XmlElement(name = "records")
	public Collection<OrganizationTypeRecordConverter> getRecordsConverter() {
		return recordsConverter.getRecords();
	}
}
