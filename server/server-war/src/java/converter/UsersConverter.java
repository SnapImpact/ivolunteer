/*
 *  UsersConverter
 *
 * Created on October 24, 2008, 9:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package converter;

import java.net.URI;
import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.ArrayList;
import persistence.Users;


/**
 *
 * @author dave
 */

@XmlRootElement(name = "users")
public class UsersConverter {
    private Collection<Users> entities;
    private Collection<UserRefConverter> references;
    private URI uri;
  
    /** Creates a new instance of UsersConverter */
    public UsersConverter() {
    }

    /**
     * Creates a new instance of UsersConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     */
    public UsersConverter(Collection<Users> entities, URI uri) {
        this.entities = entities;
        this.uri = uri;
    }

    /**
     * Returns a collection of UserRefConverter.
     *
     * @return a collection of UserRefConverter
     */
    @XmlElement(name = "userRef")
    public Collection<UserRefConverter> getReferences() {
        references = new ArrayList<UserRefConverter>();
        if (entities != null) {
            for (Users entity : entities) {
                references.add(new UserRefConverter(entity, uri, true));
            }
        }
        return references;
    }

    /**
     * Sets a collection of UserRefConverter.
     *
     * @param a collection of UserRefConverter to set
     */
    public void setReferences(Collection<UserRefConverter> references) {
        this.references = references;
    }

    /**
     * Returns the URI associated with this converter.
     *
     * @return the uri
     */
    @XmlAttribute(name = "uri")
    public URI getResourceUri() {
        return uri;
    }

    /**
     * Returns a collection Users entities.
     *
     * @return a collection of Users entities
     */
    @XmlTransient
    public Collection<Users> getEntities() {
        entities = new ArrayList<Users>();
        if (references != null) {
            for (UserRefConverter ref : references) {
                entities.add(ref.getEntity());
            }
        }
        return entities;
    }
}
