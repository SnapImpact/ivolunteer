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
import persistence.InterestArea;


/**
 *
 * @author dave
 */


@XmlRootElement( name = "records" )
public class InterestAreaRecordsConverter {
    private Collection<InterestArea> records;
    private URI uri;
    private URI baseUri;
  
    /** Creates a new instance of OrganizationsConverter */
    public InterestAreaRecordsConverter() {
    }

    /**
     * Creates a new instance of OrganizationsConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public InterestAreaRecordsConverter(Collection<InterestArea> records, URI uri, URI baseUri) {
        this.records = records;
        this.uri = uri;
        this.baseUri = baseUri;
    }
    
    @XmlElement
    public ArrayList<InterestAreaRecordConverter> getRecords() {
        ArrayList<InterestAreaRecordConverter> ret = new ArrayList<InterestAreaRecordConverter>();
        if (records != null) {
            for (InterestArea record : records ) {
                ret.add(new InterestAreaRecordConverter(record, baseUri.resolve( "location/" + record.getId() + "/"), 1));
            }
        }
        return ret;
    }
    

}
