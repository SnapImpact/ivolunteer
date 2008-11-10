/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Location;

/**
 *
 * @author dave
 */
@Stateless
public class LocationFacade implements LocationFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Location location) {
        em.persist(location);
    }

    public void edit(Location location) {
        em.merge(location);
    }

    public void remove(Location location) {
        em.remove(em.merge(location));
    }

    public Location find(Object id) {
        return em.find(Location.class, id);
    }

    public List<Location> findAll(int start, int max) {
        return em.createQuery("select object(o) from Location as o").setFirstResult(start).setMaxResults(max).getResultList();
    }

}
