/*
 *  OrganizationsListConverter
 *
 * Created on October 6, 2008, 8:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package converter;


import persistence.Organizations;
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
public class OrganizationsListConverter {
    private IdListConverter idListConverter;
    private OrganizationsRecordsConverter organizationsRecordsConverter;
  
    /** Creates a new instance of OrganizationsConverter */
    public OrganizationsListConverter() {
    }

    /**
     * Creates a new instance of OrganizationsConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public OrganizationsListConverter(Collection<Organizations> records, URI uri, URI baseUri) {
        this.idListConverter = new IdListConverter(records);
        this.organizationsRecordsConverter = new OrganizationsRecordsConverter ( records, uri, baseUri);
    }
    
    @XmlElement(name = "ids")
    public Collection<String> getIdListConverter() {
        return idListConverter.getIds();
    }
    
    @XmlElement( name = "records" )
    public Collection<OrganizationConverter> getOrganizationsRecordsConverter() {
        return organizationsRecordsConverter.getRecords();
    }
}
