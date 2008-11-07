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
import persistence.Organization;


/**
 *
 * @author dave
 */


@XmlRootElement( name = "records" )
public class OrganizationsRecordsConverter {
    private Collection<Organization> records;
    private URI uri;
    private URI baseUri;
  
    /** Creates a new instance of OrganizationsConverter */
    public OrganizationsRecordsConverter() {
    }

    /**
     * Creates a new instance of OrganizationsConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public OrganizationsRecordsConverter(Collection<Organization> records, URI uri, URI baseUri) {
        this.records = records;
        this.uri = uri;
        this.baseUri = baseUri;
    }
    
    @XmlElement
    public ArrayList<OrganizationConverter> getRecords() {
        ArrayList<OrganizationConverter> ret = new ArrayList<OrganizationConverter>();
        if (records != null) {
            for (Organization record : records ) {
                ret.add(new OrganizationConverter(record, baseUri.resolve( "organizations/" + record.getId() + "/"), 1));
            }
        }
        return ret;
    }
    

}
