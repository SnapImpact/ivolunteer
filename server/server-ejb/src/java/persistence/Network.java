/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "NETWORK")
@NamedQueries({@NamedQuery(name = "Network.findAll", query = "SELECT n FROM Network n"), @NamedQuery(name = "Network.findById", query = "SELECT n FROM Network n WHERE n.id = :id"), @NamedQuery(name = "Network.findByName", query = "SELECT n FROM Network n WHERE n.name = :name"), @NamedQuery(name = "Network.findByUrl", query = "SELECT n FROM Network n WHERE n.url = :url")})
public class Network implements Serializable, IdInterface {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    @Column(name = "URL")
    private String url;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "networkId")
    private Collection<Integration> integrationCollection;

    public Network() {
    }

    public Network(String id) {
        this.id = id;
    }

    public Network(String id, String name) {
        this.id = id;
        this.name = name;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Collection<Integration> getIntegrationCollection() {
        return integrationCollection;
    }

    public void setIntegrationCollection(Collection<Integration> integrationCollection) {
        this.integrationCollection = integrationCollection;
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
        if (!(object instanceof Network)) {
            return false;
        }
        Network other = (Network) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Network[id=" + id + "]";
    }

}
