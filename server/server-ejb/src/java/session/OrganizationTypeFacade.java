/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.OrganizationType;

/**
 * 
 * @author dave
 */
@Stateless
public class OrganizationTypeFacade implements OrganizationTypeFacadeLocal {
	@PersistenceContext
	private EntityManager	em;

	public void create(OrganizationType organizationType) {
		em.persist(organizationType);
	}

	public void edit(OrganizationType organizationType) {
		em.merge(organizationType);
	}

	public void remove(OrganizationType organizationType) {
		em.remove(em.merge(organizationType));
	}

	public OrganizationType find(Object id) {
		return em.find(OrganizationType.class, id);
	}

	public List<OrganizationType> findAll() {
		return em.createQuery("select object(o) from OrganizationType as o").getResultList();
	}

}
