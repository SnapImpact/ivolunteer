/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Integrations;

/**
 *
 * @author dave
 */
@Stateless
public class IntegrationsFacade implements IntegrationsFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Integrations integrations) {
        em.persist(integrations);
    }

    public void edit(Integrations integrations) {
        em.merge(integrations);
    }

    public void remove(Integrations integrations) {
        em.remove(em.merge(integrations));
    }

    public Integrations find(Object id) {
        return em.find(persistence.Integrations.class, id);
    }

    public List<Integrations> findAll() {
        return em.createQuery("select object(o) from Integrations as o").getResultList();
    }

}
