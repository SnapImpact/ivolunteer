/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.Timestamp;

/**
 * 
 * @author dave
 */
@Local
public interface TimestampFacadeLocal {

	void create(Timestamp timestamp);

	void edit(Timestamp timestamp);

	void remove(Timestamp timestamp);

	Timestamp find(Object id);

	List<Timestamp> findAll(int start, int max);

}
