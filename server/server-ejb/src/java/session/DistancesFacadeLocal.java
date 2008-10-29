/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.Distances;

/**
 *
 * @author dave
 */
@Local
public interface DistancesFacadeLocal {

    void create(Distances distances);

    void edit(Distances distances);

    void remove(Distances distances);

    Distances find(Object id);

    List<Distances> findAll();

}
