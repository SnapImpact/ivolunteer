/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author dave
 */
@Entity
@Table(name = "INTEGRATIONS")
@NamedQueries({@NamedQuery(name = "Integrations.findById", query = "SELECT i FROM Integrations i WHERE i.id = :id"), @NamedQuery(name = "Integrations.findByUserName", query = "SELECT i FROM Integrations i WHERE i.userName = :userName"), @NamedQuery(name = "Integrations.findByPassword", query = "SELECT i FROM Integrations i WHERE i.password = :password")})
public class Integrations implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID", nullable = false)
    private String id;
    @Column(name = "USER_NAME", nullable = false)
    private String userName;
    @Column(name = "PASSWORD")
    private String password;
    @JoinColumn(name = "NETWORK_ID", referencedColumnName = "ID")
    @ManyToOne
    private Networks networkId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne
    private Users userId;

    public Integrations() {
    }

    public Integrations(String id) {
        this.id = id;
    }

    public Integrations(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Networks getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Networks networkId) {
        this.networkId = networkId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
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
        if (!(object instanceof Integrations)) {
            return false;
        }
        Integrations other = (Integrations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Integrations[id=" + id + "]";
    }

}
