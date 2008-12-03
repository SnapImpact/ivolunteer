/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.InterestArea;

/**
 * 
 * @author dave
 */
@Stateless
public class InterestAreaFacade implements InterestAreaFacadeLocal {
	@PersistenceContext
	private EntityManager	em;

	public void create(InterestArea interestArea) {
		em.persist(interestArea);
	}

	public void edit(InterestArea interestArea) {
		em.merge(interestArea);
	}

	public void remove(InterestArea interestArea) {
		em.remove(em.merge(interestArea));
	}

	public InterestArea find(Object id) {
		return em.find(InterestArea.class, id);
	}

	public List<InterestArea> findAll() {
		return em.createQuery("select object(o) from InterestArea as o").getResultList();
	}

}
