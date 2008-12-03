/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.Organization;

/**
 * 
 * @author dave
 */
@Local
public interface OrganizationFacadeLocal {

	void create(Organization organization);

	void edit(Organization organization);

	void remove(Organization organization);

	Organization find(Object id);

	List<Organization> findAll(int start, int max);

}
