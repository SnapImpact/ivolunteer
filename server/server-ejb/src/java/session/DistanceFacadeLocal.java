/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.Distance;

/**
 *
 * @author dave
 */
@Local
public interface DistanceFacadeLocal {

    void create(Distance distance);

    void edit(Distance distance);

    void remove(Distance distance);

    Distance find(Object id);

    List<Distance> findAll(int start, int max);
}
