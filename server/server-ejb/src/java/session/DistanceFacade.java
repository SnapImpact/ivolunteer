/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Distance;

/**
 *
 * @author dave
 */
@Stateless
public class DistanceFacade implements DistanceFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Distance distance) {
        em.persist(distance);
    }

    public void edit(Distance distance) {
        em.merge(distance);
    }

    public void remove(Distance distance) {
        em.remove(em.merge(distance));
    }

    public Distance find(Object id) {
        return em.find(Distance.class, id);
    }

    public List<Distance> findAll() {
        return em.createQuery("select object(o) from Distance as o").getResultList();
    }

}
