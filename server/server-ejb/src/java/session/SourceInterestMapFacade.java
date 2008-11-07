/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.SourceInterestMap;

/**
 *
 * @author dave
 */
@Stateless
public class SourceInterestMapFacade implements SourceInterestMapFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(SourceInterestMap sourceInterestMap) {
        em.persist(sourceInterestMap);
    }

    public void edit(SourceInterestMap sourceInterestMap) {
        em.merge(sourceInterestMap);
    }

    public void remove(SourceInterestMap sourceInterestMap) {
        em.remove(em.merge(sourceInterestMap));
    }

    public SourceInterestMap find(Object id) {
        return em.find(SourceInterestMap.class, id);
    }

    public List<SourceInterestMap> findAll() {
        return em.createQuery("select object(o) from SourceInterestMap as o").getResultList();
    }

}
