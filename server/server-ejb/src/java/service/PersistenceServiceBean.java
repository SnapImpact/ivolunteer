/*
 *  Copyright (c) 2008 Boulder Community Foundation - iVolunteer
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * 
 * @author Dave Angulo
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
