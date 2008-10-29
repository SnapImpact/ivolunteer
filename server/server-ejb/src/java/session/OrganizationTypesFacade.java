/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.OrganizationTypes;

/**
 *
 * @author dave
 */
@Stateless
public class OrganizationTypesFacade implements OrganizationTypesFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(OrganizationTypes organizationTypes) {
        em.persist(organizationTypes);
    }

    public void edit(OrganizationTypes organizationTypes) {
        em.merge(organizationTypes);
    }

    public void remove(OrganizationTypes organizationTypes) {
        em.remove(em.merge(organizationTypes));
    }

    public OrganizationTypes find(Object id) {
        return em.find(persistence.OrganizationTypes.class, id);
    }

    public List<OrganizationTypes> findAll() {
        return em.createQuery("select object(o) from OrganizationTypes as o").getResultList();
    }

}
