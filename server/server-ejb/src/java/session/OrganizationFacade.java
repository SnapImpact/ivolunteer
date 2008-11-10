/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Organization;

/**
 *
 * @author dave
 */
@Stateless
public class OrganizationFacade implements OrganizationFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Organization organization) {
        em.persist(organization);
    }

    public void edit(Organization organization) {
        em.merge(organization);
    }

    public void remove(Organization organization) {
        em.remove(em.merge(organization));
    }

    public Organization find(Object id) {
        return em.find(Organization.class, id);
    }

    public List<Organization> findAll(int start, int max) {
        return em.createQuery("select object(o) from Organization as o").setFirstResult(start).setMaxResults(max).getResultList();
    }

}
