/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.Filter;

/**
 * 
 * @author dave
 */
@Local
public interface FilterFacadeLocal {

	void create(Filter filter);

	void edit(Filter filter);

	void remove(Filter filter);

	Filter find(Object id);

	List<Filter> findAll();

}
