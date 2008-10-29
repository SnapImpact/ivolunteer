/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Users;

/**
 *
 * @author dave
 */
@Stateless
public class UsersFacade implements UsersFacadeLocal {
    @PersistenceContext
    private EntityManager em;

    public void create(Users users) {
        em.persist(users);
    }

    public void edit(Users users) {
        em.merge(users);
    }

    public void remove(Users users) {
        em.remove(em.merge(users));
    }

    public Users find(Object id) {
        return em.find(persistence.Users.class, id);
    }

    public List<Users> findAll() {
        return em.createQuery("select object(o) from Users as o").getResultList();
    }

}
