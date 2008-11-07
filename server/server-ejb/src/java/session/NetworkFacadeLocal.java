/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.Network;

/**
 *
 * @author dave
 */
@Local
public interface NetworkFacadeLocal {

    void create(Network network);

    void edit(Network network);

    void remove(Network network);

    Network find(Object id);

    List<Network> findAll();

}
