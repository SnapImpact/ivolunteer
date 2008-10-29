/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Events;

/**
 *
 * @author dave
 */
@Stateless
public class EventsFacade implements EventsFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Events events) {
        em.persist(events);
    }

    public void edit(Events events) {
        em.merge(events);
    }

    public void remove(Events events) {
        em.remove(em.merge(events));
    }

    public Events find(Object id) {
        return em.find(persistence.Events.class, id);
    }

    public List<Events> findAll() {
        return findAll(0,10);
    }

    public List<Events> findAll(int start, int max) {
        return em.createQuery("select object(o) from Events as o").setFirstResult(start).setMaxResults(max).getResultList();
    }

}
