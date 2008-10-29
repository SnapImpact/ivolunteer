/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Distances;

/**
 *
 * @author dave
 */
@Stateless
public class DistancesFacade implements DistancesFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Distances distances) {
        em.persist(distances);
    }

    public void edit(Distances distances) {
        em.merge(distances);
    }

    public void remove(Distances distances) {
        em.remove(em.merge(distances));
    }

    public Distances find(Object id) {
        return em.find(persistence.Distances.class, id);
    }

    public List<Distances> findAll() {
        return em.createQuery("select object(o) from Distances as o").getResultList();
    }

}
