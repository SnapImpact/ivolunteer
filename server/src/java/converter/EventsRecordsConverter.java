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
import persistence.Events;


/**
 *
 * @author dave
 */


@XmlRootElement( name = "records" )
public class EventsRecordsConverter {
    private Collection<Events> records;
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
    public EventsRecordsConverter(Collection<Events> records, URI uri, URI baseUri) {
        this.records = records;
        this.uri = uri;
        this.baseUri = baseUri;
    }
    
    @XmlElement
    public ArrayList<EventConverter> getRecords() {
        ArrayList<EventConverter> ret = new ArrayList<EventConverter>();
        if (records != null) {
            for (Events record : records ) {
                ret.add(new EventConverter(record, baseUri.resolve( "events/" + record.getId() + "/")));
            }
        }
        return ret;
    }
    

}
