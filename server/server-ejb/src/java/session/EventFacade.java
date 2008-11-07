/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Event;

/**
 *
 * @author dave
 */
@Stateless
public class EventFacade implements EventFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Event event) {
        em.persist(event);
    }

    public void edit(Event event) {
        em.merge(event);
    }

    public void remove(Event event) {
        em.remove(em.merge(event));
    }

    public Event find(Object id) {
        return em.find(Event.class, id);
    }

    public List<Event> findAll() {
        return em.createQuery("select object(o) from Event as o").getResultList();
    }

}
