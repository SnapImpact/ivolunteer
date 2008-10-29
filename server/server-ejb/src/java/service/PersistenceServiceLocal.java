/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import javax.ejb.Local;
import javax.persistence.Query;
import java.util.Collection;

/**
 *
 * @author dave
 */
@Local
public interface PersistenceServiceLocal {

    void refreshEntity(Object entity);

    <T> T mergeEntity(T entity);
    
    void persistEntity(Object entity);

    void removeEntity(Object entity);

    Query createQuery(String query);

    Query createNamedQuery(String query);

    <T> T resolveEntity(T entity);
}
