/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.IvUser;

/**
 * 
 * @author dave
 */
@Stateless
public class IvUserFacade implements IvUserFacadeLocal {
	@PersistenceContext
	private EntityManager	em;

	public void create(IvUser ivUser) {
		em.persist(ivUser);
	}

	public void edit(IvUser ivUser) {
		em.merge(ivUser);
	}

	public void remove(IvUser ivUser) {
		em.remove(em.merge(ivUser));
	}

	public IvUser find(Object id) {
		return em.find(IvUser.class, id);
	}

	public List<IvUser> findAll() {
		return em.createQuery("select object(o) from IvUser as o").getResultList();
	}

}
