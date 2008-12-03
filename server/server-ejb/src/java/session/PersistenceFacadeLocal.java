/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

/**
 *
 * @author dave
 */

import javax.ejb.Local;
import persistence.*;
import java.util.List;

@Local
public interface PersistenceFacadeLocal {

	void create(IdInterface<?> idInt);

	void edit(IdInterface<?> idInt);

	void remove(IdInterface<?> idInt);

	IdInterface find(Object id, Class claz);

	List<? extends IdInterface> findAll(String query, int start, int max);

}
