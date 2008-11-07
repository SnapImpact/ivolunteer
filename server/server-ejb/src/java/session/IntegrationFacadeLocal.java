/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.Integration;

/**
 *
 * @author dave
 */
@Local
public interface IntegrationFacadeLocal {

    void create(Integration integration);

    void edit(Integration integration);

    void remove(Integration integration);

    Integration find(Object id);

    List<Integration> findAll();

}
