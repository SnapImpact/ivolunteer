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
import persistence.Timeframe;


/**
 *
 * @author dave
 */


@XmlRootElement( name = "records" )
public class TimeframeRecordsConverter {
    private Collection<Timeframe> records;
    private URI uri;
    private URI baseUri;
  
    /** Creates a new instance of OrganizationsConverter */
    public TimeframeRecordsConverter() {
    }

    /**
     * Creates a new instance of OrganizationsConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public TimeframeRecordsConverter(Collection<Timeframe> records, URI uri, URI baseUri) {
        this.records = records;
        this.uri = uri;
        this.baseUri = baseUri;
    }
    
    @XmlElement
    public ArrayList<TimeframeRecordConverter> getRecords() {
        ArrayList<TimeframeRecordConverter> ret = new ArrayList<TimeframeRecordConverter>();
        if (records != null) {
            for (Timeframe record : records ) {
                ret.add(new TimeframeRecordConverter(record, baseUri.resolve( "location/" + record.getId() + "/"), 1));
            }
        }
        return ret;
    }
    

}
