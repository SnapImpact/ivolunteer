/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Timeframes;

/**
 *
 * @author dave
 */
@Stateless
public class TimeframesFacade implements TimeframesFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Timeframes timeframes) {
        em.persist(timeframes);
    }

    public void edit(Timeframes timeframes) {
        em.merge(timeframes);
    }

    public void remove(Timeframes timeframes) {
        em.remove(em.merge(timeframes));
    }

    public Timeframes find(Object id) {
        return em.find(persistence.Timeframes.class, id);
    }

    public List<Timeframes> findAll() {
        return em.createQuery("select object(o) from Timeframes as o").getResultList();
    }

}
