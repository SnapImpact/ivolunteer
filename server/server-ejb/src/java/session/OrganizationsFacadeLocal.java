/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.Organizations;

/**
 *
 * @author dave
 */
@Local
public interface OrganizationsFacadeLocal {

    void create(Organizations organizations);

    void edit(Organizations organizations);

    void remove(Organizations organizations);

    Organizations find(Object id);

    List<Organizations> findAll();

    List<Organizations> findAll(int start, int max);

}
