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

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

/**
 * Utility class for dealing with persistence.
 * 
 * @author Dave Angulo
 */
public class PersistenceService {
	private static String							DEFAULT_PU	= "serverPU";

	private static ThreadLocal<PersistenceService>	instance	= new ThreadLocal<PersistenceService>() {
																	@Override
																	protected PersistenceService initialValue() {
																		return new PersistenceService();
																	}
																};

	private EntityManager							em;

	private UserTransaction							utx;

	private PersistenceService() {
		try {
			this.em = (EntityManager) new InitialContext()
					.lookup("java:comp/env/persistence/EntityManager");
			this.utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
		} catch (NamingException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Returns an instance of PersistenceService.
	 * 
	 * @return an instance of PersistenceService
	 */
	public static PersistenceService getInstance() {
		return instance.get();
	}

	private static void removeInstance() {
		instance.remove();
	}

	/**
	 * Returns an instance of EntityManager.
	 * 
	 * @return an instance of EntityManager
	 */
	public EntityManager getEntityManager() {
		return em;
	}

	/**
	 * Begins a resource transaction.
	 */
	public void beginTx() {
		try {
			utx.begin();
			em.joinTransaction();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Commits a resource transaction.
	 */
	public void commitTx() {
		try {
			utx.commit();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Rolls back a resource transaction.
	 */
	public void rollbackTx() {
		try {
			utx.rollback();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Closes this instance.
	 */
	public void close() {
		removeInstance();
	}
}
