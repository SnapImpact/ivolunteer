/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import javax.ejb.Remote;
import javax.persistence.Query;

/**
 *
 * @author dave
 */

@Remote
public interface PersistenceFacadeRemote {

    Query createQuery(String query);

    void persistEntity(Object entity);
    
}
