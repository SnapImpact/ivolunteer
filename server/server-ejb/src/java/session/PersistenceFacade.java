/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dave
 */
@Stateless(mappedName="session.PersistenceFacade")
public class PersistenceFacade implements PersistenceFacadeRemote {
    @PersistenceContext
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    public Query createQuery(String query) {
        return em.createQuery(query);
    }

    public void persistEntity(Object entity) {
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
    
 
}
