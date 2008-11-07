/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.IvUser;

/**
 *
 * @author dave
 */
@Local
public interface IvUserFacadeLocal {

    void create(IvUser ivUser);

    void edit(IvUser ivUser);

    void remove(IvUser ivUser);

    IvUser find(Object id);

    List<IvUser> findAll();

}
