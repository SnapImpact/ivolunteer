/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Organizations;

/**
 *
 * @author dave
 */
@Stateless
public class OrganizationsFacade implements OrganizationsFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Organizations organizations) {
        em.persist(organizations);
    }

    public void edit(Organizations organizations) {
        em.merge(organizations);
    }

    public void remove(Organizations organizations) {
        em.remove(em.merge(organizations));
    }

    public Organizations find(Object id) {
        return em.find(persistence.Organizations.class, id);
    }

    public List<Organizations> findAll() {
        return findAll(0,10);
    }
    
    public List<Organizations> findAll(int start, int max) {
        return em.createQuery("select object(o) from Organizations as o").setFirstResult(start).setMaxResults(max).getResultList();
    }

}
