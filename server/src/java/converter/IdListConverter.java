/*
 *  OrganizationsListConverter
 *
 * Created on October 6, 2008, 8:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package converter;

import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import persistence.Organizations;


/**
 *
 * @author dave
 */

@XmlRootElement( name = "ids" )
public class IdListConverter {
    @XmlElement
    private Collection<String> ids = new ArrayList<String>();
  
    /** Creates a new instance of OrganizationsConverter */
    public IdListConverter() {
    }

    /**
     * Creates a new instance of OrganizationsConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public IdListConverter(Collection<Organizations> records) {
        if (this.ids == null) this.ids = new ArrayList<String>();
        if (records != null) {
            for (Organizations record : records) {
                this.ids.add(record.getId());
            }
        }
    }
    
    public Collection<String> getIds() {
        return ids;
    }
}
