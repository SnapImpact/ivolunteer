/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Integration;

/**
 *
 * @author dave
 */
@Stateless
public class IntegrationFacade implements IntegrationFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Integration integration) {
        em.persist(integration);
    }

    public void edit(Integration integration) {
        em.merge(integration);
    }

    public void remove(Integration integration) {
        em.remove(em.merge(integration));
    }

    public Integration find(Object id) {
        return em.find(Integration.class, id);
    }

    public List<Integration> findAll() {
        return em.createQuery("select object(o) from Integration as o").getResultList();
    }

}
