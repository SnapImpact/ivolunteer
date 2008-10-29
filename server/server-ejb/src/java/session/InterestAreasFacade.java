/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.InterestAreas;

/**
 *
 * @author dave
 */
@Stateless
public class InterestAreasFacade implements InterestAreasFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(InterestAreas interestAreas) {
        em.persist(interestAreas);
    }

    public void edit(InterestAreas interestAreas) {
        em.merge(interestAreas);
    }

    public void remove(InterestAreas interestAreas) {
        em.remove(em.merge(interestAreas));
    }

    public InterestAreas find(Object id) {
        return em.find(persistence.InterestAreas.class, id);
    }

    public List<InterestAreas> findAll() {
        return em.createQuery("select object(o) from InterestAreas as o").getResultList();
    }

}
