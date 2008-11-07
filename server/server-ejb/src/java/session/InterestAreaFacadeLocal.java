/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.InterestArea;

/**
 *
 * @author dave
 */
@Local
public interface InterestAreaFacadeLocal {

    void create(InterestArea interestArea);

    void edit(InterestArea interestArea);

    void remove(InterestArea interestArea);

    InterestArea find(Object id);

    List<InterestArea> findAll();

}
