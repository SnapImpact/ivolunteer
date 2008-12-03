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
import persistence.Distance;
import java.net.URI;
import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author dave
 */

@XmlRootElement(name = "distances")
public class DistanceListConverter {
	private IdListConverter				idListConverter;
	private DistanceRecordsConverter	distanceRecordsConverter;

	/** Creates a new instance of OrganizationsConverter */
	public DistanceListConverter() {
	}

	/**
	 * Creates a new instance of OrganizationsConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 */
	public DistanceListConverter(Collection<Distance> records, URI uri, URI baseUri) {
		this.idListConverter = new IdListConverter(records);
		this.distanceRecordsConverter = new DistanceRecordsConverter(records, uri, baseUri);
	}

	@XmlElement(name = "ids")
	public Collection<String> getIdListConverter() {
		return idListConverter.getIds();
	}

	@XmlElement(name = "records")
	public Collection<DistanceRecordConverter> getEventsRecordsConverter() {
		return distanceRecordsConverter.getRecords();
	}
}
