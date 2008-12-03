/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

/**
 *
 * @author dave
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import core.CachingServiceLocator;
import session.PersistenceFacadeLocal;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Collection;
import persistence.IdInterface;


public class Base {
    protected CachingServiceLocator serviceLocator = CachingServiceLocator.getInstance();
    protected String ejbName;
    protected PersistenceFacadeLocal persistenceFacade;

    public Base() {
        try {
            javax.naming.Context c = new InitialContext();
            this.persistenceFacade = (PersistenceFacadeLocal) serviceLocator.getLocalHome("java:comp/env/PersistenceFacade");
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
     * @param entity the entity to persist
     */
    protected void createEntity(IdInterface<?> entity) {
        persistenceFacade.create(entity);
    }



}
