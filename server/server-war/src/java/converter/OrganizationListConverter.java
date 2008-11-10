/*
 *  OrganizationListConverter
 *
 * Created on October 6, 2008, 8:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package converter;


import persistence.Organization;
import java.net.URI;
import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "organizations")
public class OrganizationListConverter {
    private IdListConverter idListConverter;
    private OrganizationRecordsConverter organizationsRecordsConverter;
  
    /** Creates a new instance of OrganizationsConverter */
    public OrganizationListConverter() {
    }

    /**
     * Creates a new instance of OrganizationsConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public OrganizationListConverter(Collection<Organization> records, URI uri, URI baseUri) {
        this.idListConverter = new IdListConverter(records);
        this.organizationsRecordsConverter = new OrganizationRecordsConverter ( records, uri, baseUri);
    }
    
    @XmlElement(name = "ids")
    public Collection<String> getIdListConverter() {
        return idListConverter.getIds();
    }
    
    @XmlElement( name = "records" )
    public Collection<OrganizationRecordConverter> getOrganizationsRecordsConverter() {
        return organizationsRecordsConverter.getRecords();
    }
}
