/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.Integrations;

/**
 *
 * @author dave
 */
@Local
public interface IntegrationsFacadeLocal {

    void create(Integrations integrations);

    void edit(Integrations integrations);

    void remove(Integrations integrations);

    Integrations find(Object id);

    List<Integrations> findAll();

}
