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
public class EventsRecordsConverter {
    private Collection<Event> records;
    private URI uri;
    private URI baseUri;
  
    /** Creates a new instance of OrganizationsConverter */
    public EventsRecordsConverter() {
    }

    /**
     * Creates a new instance of OrganizationsConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public EventsRecordsConverter(Collection<Event> records, URI uri, URI baseUri) {
        this.records = records;
        this.uri = uri;
        this.baseUri = baseUri;
    }
    
    @XmlElement
    public ArrayList<EventConverter> getRecords() {
        ArrayList<EventConverter> ret = new ArrayList<EventConverter>();
        if (records != null) {
            for (Event record : records ) {
                ret.add(new EventConverter(record, baseUri.resolve( "event/" + record.getId() + "/"), 1));
            }
        }
        return ret;
    }
    

}
