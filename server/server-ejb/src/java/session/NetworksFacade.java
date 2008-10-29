/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Networks;

/**
 *
 * @author dave
 */
@Stateless
public class NetworksFacade implements NetworksFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Networks networks) {
        em.persist(networks);
    }

    public void edit(Networks networks) {
        em.merge(networks);
    }

    public void remove(Networks networks) {
        em.remove(em.merge(networks));
    }

    public Networks find(Object id) {
        return em.find(persistence.Networks.class, id);
    }

    public List<Networks> findAll() {
        return em.createQuery("select object(o) from Networks as o").getResultList();
    }

}
