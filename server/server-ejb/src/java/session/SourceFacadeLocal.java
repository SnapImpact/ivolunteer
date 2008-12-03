/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.Source;

/**
 * 
 * @author dave
 */
@Local
public interface SourceFacadeLocal {

	void create(Source source);

	void edit(Source source);

	void remove(Source source);

	Source find(Object id);

	List<Source> findAll();

}
