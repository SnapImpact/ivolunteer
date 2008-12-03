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
import persistence.InterestArea;
import java.net.URI;
import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "interest_areas")
public class InterestAreaListConverter {
    private IdListConverter idListConverter;
    private InterestAreaRecordsConverter interestAreaRecordsConverter;
  
    /** Creates a new instance of OrganizationsConverter */
    public InterestAreaListConverter() {
    }

    /**
     * Creates a new instance of OrganizationsConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public InterestAreaListConverter(Collection<InterestArea> records, URI uri, URI baseUri) {
        this.idListConverter = new IdListConverter(records);
        this.interestAreaRecordsConverter = new InterestAreaRecordsConverter ( records, uri, baseUri);
    }
    
    @XmlElement(name = "ids")
    public Collection<String> getIdListConverter() {
        return idListConverter.getIds();
    }
    
    @XmlElement( name = "records" )
    public Collection<InterestAreaRecordConverter> getEventsRecordsConverter() {
        return interestAreaRecordsConverter.getRecords();
    }
}
