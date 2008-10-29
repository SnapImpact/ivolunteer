/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.Networks;

/**
 *
 * @author dave
 */
@Local
public interface NetworksFacadeLocal {

    void create(Networks networks);

    void edit(Networks networks);

    void remove(Networks networks);

    Networks find(Object id);

    List<Networks> findAll();

}
