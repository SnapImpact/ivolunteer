/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Timeframe;

/**
 * 
 * @author dave
 */
@Stateless
public class TimeframeFacade implements TimeframeFacadeLocal {
	@PersistenceContext
	private EntityManager	em;

	public void create(Timeframe timeframe) {
		em.persist(timeframe);
	}

	public void edit(Timeframe timeframe) {
		em.merge(timeframe);
	}

	public void remove(Timeframe timeframe) {
		em.remove(em.merge(timeframe));
	}

	public Timeframe find(Object id) {
		return em.find(Timeframe.class, id);
	}

	public List<Timeframe> findAll() {
		return em.createQuery("select object(o) from Timeframe as o").getResultList();
	}

}
