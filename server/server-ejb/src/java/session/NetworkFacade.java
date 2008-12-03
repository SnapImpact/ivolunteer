/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Network;

/**
 * 
 * @author dave
 */
@Stateless
public class NetworkFacade implements NetworkFacadeLocal {
	@PersistenceContext
	private EntityManager	em;

	public void create(Network network) {
		em.persist(network);
	}

	public void edit(Network network) {
		em.merge(network);
	}

	public void remove(Network network) {
		em.remove(em.merge(network));
	}

	public Network find(Object id) {
		return em.find(Network.class, id);
	}

	public List<Network> findAll() {
		return em.createQuery("select object(o) from Network as o").getResultList();
	}

}
