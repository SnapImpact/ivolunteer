/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.Timeframes;

/**
 *
 * @author dave
 */
@Local
public interface TimeframesFacadeLocal {

    void create(Timeframes timeframes);

    void edit(Timeframes timeframes);

    void remove(Timeframes timeframes);

    Timeframes find(Object id);

    List<Timeframes> findAll();

}
