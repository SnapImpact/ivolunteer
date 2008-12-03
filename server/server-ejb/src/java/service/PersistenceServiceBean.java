/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * 
 * @author dave
 */
@Stateless(mappedName = "session.PersistenceService")
public class PersistenceServiceBean implements PersistenceServiceLocal {
	@PersistenceContext
	private EntityManager	em;

	public void persistEntity(Object object) {
		em.persist(object);
	}

	public void refreshEntity(Object entity) {
		em.refresh(entity);
	}

	public void removeEntity(Object entity) {
		em.remove(entity);
	}

	public Query createQuery(String query) {
		return em.createQuery(query);
	}

	public Query createNamedQuery(String query) {
		return em.createNamedQuery(query);
	}

	public <T> T mergeEntity(T entity) {
		return (T) em.merge(entity);
	}

	public <T> T resolveEntity(T entity) {
		entity = mergeEntity(entity);
		em.refresh(entity);

		return entity;
	}

	// Add business logic below. (Right-click in editor and choose
	// "EJB Methods > Add Business Method" or "Web Service > Add Operation")

}
