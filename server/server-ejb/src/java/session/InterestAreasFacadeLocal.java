/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.InterestAreas;

/**
 *
 * @author dave
 */
@Local
public interface InterestAreasFacadeLocal {

    void create(InterestAreas interestAreas);

    void edit(InterestAreas interestAreas);

    void remove(InterestAreas interestAreas);

    InterestAreas find(Object id);

    List<InterestAreas> findAll();

}
