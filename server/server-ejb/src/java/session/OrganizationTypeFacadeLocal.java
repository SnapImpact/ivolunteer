/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.OrganizationType;

/**
 * 
 * @author dave
 */
@Local
public interface OrganizationTypeFacadeLocal {

	void create(OrganizationType organizationType);

	void edit(OrganizationType organizationType);

	void remove(OrganizationType organizationType);

	OrganizationType find(Object id);

	List<OrganizationType> findAll();

}
