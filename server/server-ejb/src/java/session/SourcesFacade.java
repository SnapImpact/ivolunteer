/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Sources;

/**
 *
 * @author dave
 */
@Stateless
public class SourcesFacade implements SourcesFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Sources sources) {
        em.persist(sources);
    }

    public void edit(Sources sources) {
        em.merge(sources);
    }

    public void remove(Sources sources) {
        em.remove(em.merge(sources));
    }

    public Sources find(Object id) {
        return em.find(persistence.Sources.class, id);
    }

    public List<Sources> findAll() {
        return em.createQuery("select object(o) from Sources as o").getResultList();
    }

}
