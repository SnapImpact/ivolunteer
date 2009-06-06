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

/**
 *
 * @author Dave Angulo
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import core.CachingServiceLocator;
import session.PersistenceFacadeLocal;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Collection;
import persistence.IdInterface;
import etl.geocodeSessionLocal;

public class Base {
	protected CachingServiceLocator		serviceLocator	= CachingServiceLocator.getInstance();
	protected String					ejbName;
	protected PersistenceFacadeLocal	persistenceFacade;
    protected geocodeSessionLocal       geo;

	public Base() {
		try {
			javax.naming.Context c = new InitialContext();
			this.persistenceFacade = (PersistenceFacadeLocal) serviceLocator
					.getLocalHome("java:comp/env/PersistenceFacade");
            this.geo = (geocodeSessionLocal) serviceLocator
                    .getLocalHome("java:comp/env/geocodeSessionLocal");
		} catch (NamingException ne) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
			throw new RuntimeException(ne);
		}
	}

	protected Collection<? extends IdInterface> getEntities(int start, int max, String query) {
		return persistenceFacade.findAll(query, start, max);
	}

	/**
	 * Persist the given entity.
	 * 
	 * @param entity
	 *            the entity to persist
	 */
	protected void createEntity(IdInterface<?> entity) {
		persistenceFacade.create(entity);
	}

}
