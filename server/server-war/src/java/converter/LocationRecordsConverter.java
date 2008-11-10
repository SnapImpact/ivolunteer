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
import persistence.Location;


/**
 *
 * @author dave
 */


@XmlRootElement( name = "records" )
public class LocationRecordsConverter {
    private Collection<Location> records;
    private URI uri;
    private URI baseUri;
  
    /** Creates a new instance of OrganizationsConverter */
    public LocationRecordsConverter() {
    }

    /**
     * Creates a new instance of OrganizationsConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public LocationRecordsConverter(Collection<Location> records, URI uri, URI baseUri) {
        this.records = records;
        this.uri = uri;
        this.baseUri = baseUri;
    }
    
    @XmlElement
    public ArrayList<LocationRecordConverter> getRecords() {
        ArrayList<LocationRecordConverter> ret = new ArrayList<LocationRecordConverter>();
        if (records != null) {
            for (Location record : records ) {
                ret.add(new LocationRecordConverter(record, baseUri.resolve( "location/" + record.getId() + "/"), 1));
            }
        }
        return ret;
    }
    

}
