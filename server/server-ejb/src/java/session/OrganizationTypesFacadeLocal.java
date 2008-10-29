/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.OrganizationTypes;

/**
 *
 * @author dave
 */
@Local
public interface OrganizationTypesFacadeLocal {

    void create(OrganizationTypes organizationTypes);

    void edit(OrganizationTypes organizationTypes);

    void remove(OrganizationTypes organizationTypes);

    OrganizationTypes find(Object id);

    List<OrganizationTypes> findAll();

}
