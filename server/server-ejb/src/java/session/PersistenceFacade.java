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

package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.IdInterface;
import persistence.Location;
import java.util.List;
import javax.ejb.EJB;

/**
 * 
 * @author Dave Angulo
 */
@Stateless
public class PersistenceFacade implements PersistenceFacadeLocal {
	@PersistenceContext
	private EntityManager	em;

    @EJB
    private etl.geocodeSessionLocal geo;

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

    public List<IdInterface> findByLoc(String queryName, int start, int max, String lat, String lng, int radius) {
		String point = "POINT(" + lat + " " + lng +")";
        return em.createNamedQuery(queryName).setFirstResult(start).setMaxResults(max)
                .setParameter(1, point).setParameter(2, radius * 1609).setParameter(3, point)
                .setParameter(4, radius * 1609).setParameter(5, point).setParameter(6, radius * 1609).getResultList();
	}

    public List<IdInterface> findByLoc(String queryName, int start, int max, String street, String city, String state, String zip, int radius)
    {
        Location loc = new Location();
        loc.setStreet(street);
        loc.setCity(city);
        loc.setState(state);
        loc.setZip(zip);

        geo.encodeAddress(loc);

        return findByLoc(queryName, start, max, loc.getLatitude(), loc.getLongitude(), radius);
    }

}
