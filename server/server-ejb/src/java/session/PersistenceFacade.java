/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.IdInterface;
import java.util.List;

/**
 * 
 * @author dave
 */
@Stateless
public class PersistenceFacade implements PersistenceFacadeLocal {
	@PersistenceContext
	private EntityManager	em;

	public void create(IdInterface<?> entity) {
		em.persist(entity);
	}

	public void edit(IdInterface<?> entity) {
		em.merge(entity);
	}

	public void remove(IdInterface<?> entity) {
		em.remove(em.merge(entity));
	}

	public IdInterface find(Object id, Class claz) {
		return (IdInterface) em.find(claz, id);
	}

	public List<IdInterface> findAll(String query, int start, int max) {
		return em.createQuery(query).setFirstResult(start).setMaxResults(max).getResultList();
	}

}
