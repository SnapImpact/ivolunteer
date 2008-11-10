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
import persistence.Event;


/**
 *
 * @author dave
 */


@XmlRootElement( name = "records" )
public class EventRecordsConverter {
    private Collection<Event> records;
    private URI uri;
    private URI baseUri;
  
    /** Creates a new instance of OrganizationsConverter */
    public EventRecordsConverter() {
    }

    /**
     * Creates a new instance of OrganizationsConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public EventRecordsConverter(Collection<Event> records, URI uri, URI baseUri) {
        this.records = records;
        this.uri = uri;
        this.baseUri = baseUri;
    }
    
    @XmlElement
    public ArrayList<EventRecordConverter> getRecords() {
        ArrayList<EventRecordConverter> ret = new ArrayList<EventRecordConverter>();
        if (records != null) {
            for (Event record : records ) {
                ret.add(new EventRecordConverter(record, baseUri.resolve( "event/" + record.getId() + "/"), 1));
            }
        }
        return ret;
    }
    

}
