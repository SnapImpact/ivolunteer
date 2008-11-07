/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Filter;

/**
 *
 * @author dave
 */
@Stateless
public class FilterFacade implements FilterFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Filter filter) {
        em.persist(filter);
    }

    public void edit(Filter filter) {
        em.merge(filter);
    }

    public void remove(Filter filter) {
        em.remove(em.merge(filter));
    }

    public Filter find(Object id) {
        return em.find(Filter.class, id);
    }

    public List<Filter> findAll() {
        return em.createQuery("select object(o) from Filter as o").getResultList();
    }

}
