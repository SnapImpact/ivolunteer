/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author dave
 */
@Entity
@Table(name = "USERS")
@NamedQueries({@NamedQuery(name = "Users.findById", query = "SELECT u FROM Users u WHERE u.id = :id"), @NamedQuery(name = "Users.findByName", query = "SELECT u FROM Users u WHERE u.name = :name"), @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"), @NamedQuery(name = "Users.findByIphoneId", query = "SELECT u FROM Users u WHERE u.iphoneId = :iphoneId")})
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID", nullable = false)
    private String id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Column(name = "IPHONE_ID")
    private String iphoneId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Filter> filterCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Integrations> integrationsCollection;

    public Users() {
    }

    public Users(String id) {
        this.id = id;
    }

    public Users(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIphoneId() {
        return iphoneId;
    }

    public void setIphoneId(String iphoneId) {
        this.iphoneId = iphoneId;
    }

    public Collection<Filter> getFilterCollection() {
        return filterCollection;
    }

    public void setFilterCollection(Collection<Filter> filterCollection) {
        this.filterCollection = filterCollection;
    }

    public Collection<Integrations> getIntegrationsCollection() {
        return integrationsCollection;
    }

    public void setIntegrationsCollection(Collection<Integrations> integrationsCollection) {
        this.integrationsCollection = integrationsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Users[id=" + id + "]";
    }

}
