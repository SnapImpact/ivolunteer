/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.SourceOrgTypeMap;

/**
 * 
 * @author dave
 */
@Stateless
public class SourceOrgTypeMapFacade implements SourceOrgTypeMapFacadeLocal {
	@PersistenceContext
	private EntityManager	em;

	public void create(SourceOrgTypeMap sourceOrgTypeMap) {
		em.persist(sourceOrgTypeMap);
	}

	public void edit(SourceOrgTypeMap sourceOrgTypeMap) {
		em.merge(sourceOrgTypeMap);
	}

	public void remove(SourceOrgTypeMap sourceOrgTypeMap) {
		em.remove(em.merge(sourceOrgTypeMap));
	}

	public SourceOrgTypeMap find(Object id) {
		return em.find(SourceOrgTypeMap.class, id);
	}

	public List<SourceOrgTypeMap> findAll() {
		return em.createQuery("select object(o) from SourceOrgTypeMap as o").getResultList();
	}

}
