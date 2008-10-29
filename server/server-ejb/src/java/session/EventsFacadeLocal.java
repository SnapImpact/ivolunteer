/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.Events;

/**
 *
 * @author dave
 */
@Local
public interface EventsFacadeLocal {

    void create(Events events);

    void edit(Events events);

    void remove(Events events);

    Events find(Object id);

    List<Events> findAll();

    List<Events> findAll(int start, int max);

}
