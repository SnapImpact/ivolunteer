/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.Sources;

/**
 *
 * @author dave
 */
@Local
public interface SourcesFacadeLocal {

    void create(Sources sources);

    void edit(Sources sources);

    void remove(Sources sources);

    Sources find(Object id);

    List<Sources> findAll();

}
