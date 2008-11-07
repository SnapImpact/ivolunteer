/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.SourceInterestMap;

/**
 *
 * @author dave
 */
@Local
public interface SourceInterestMapFacadeLocal {

    void create(SourceInterestMap sourceInterestMap);

    void edit(SourceInterestMap sourceInterestMap);

    void remove(SourceInterestMap sourceInterestMap);

    SourceInterestMap find(Object id);

    List<SourceInterestMap> findAll();

}
