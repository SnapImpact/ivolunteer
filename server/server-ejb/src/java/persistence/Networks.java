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
@Table(name = "NETWORKS")
@NamedQueries({@NamedQuery(name = "Networks.findById", query = "SELECT n FROM Networks n WHERE n.id = :id"), @NamedQuery(name = "Networks.findByName", query = "SELECT n FROM Networks n WHERE n.name = :name"), @NamedQuery(name = "Networks.findByUrl", query = "SELECT n FROM Networks n WHERE n.url = :url")})
public class Networks implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID", nullable = false)
    private String id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "URL")
    private String url;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "networkId")
    private Collection<Integrations> integrationsCollection;

    public Networks() {
    }

    public Networks(String id) {
        this.id = id;
    }

    public Networks(String id, String name) {
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
        if (!(object instanceof Networks)) {
            return false;
        }
        Networks other = (Networks) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Networks[id=" + id + "]";
    }

}
