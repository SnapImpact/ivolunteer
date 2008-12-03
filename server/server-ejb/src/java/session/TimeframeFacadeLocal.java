/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.Timeframe;

/**
 * 
 * @author dave
 */
@Local
public interface TimeframeFacadeLocal {

	void create(Timeframe timeframe);

	void edit(Timeframe timeframe);

	void remove(Timeframe timeframe);

	Timeframe find(Object id);

	List<Timeframe> findAll();

}
