/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Timestamp;

/**
 * 
 * @author dave
 */
@Stateless
public class TimestampFacade implements TimestampFacadeLocal {
	@PersistenceContext
	private EntityManager	em;

	public void create(Timestamp timestamp) {
		em.persist(timestamp);
	}

	public void edit(Timestamp timestamp) {
		em.merge(timestamp);
	}

	public void remove(Timestamp timestamp) {
		em.remove(em.merge(timestamp));
	}

	public Timestamp find(Object id) {
		return em.find(Timestamp.class, id);
	}

	public List<Timestamp> findAll(int start, int max) {
		return em.createQuery("select object(o) from Timestamp as o").setFirstResult(start)
				.setMaxResults(max).getResultList();
	}

}
