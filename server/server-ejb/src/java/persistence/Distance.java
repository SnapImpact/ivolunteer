/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "DISTANCE")
@NamedQueries({@NamedQuery(name = "Distance.findAll", query = "SELECT d FROM Distance d"), @NamedQuery(name = "Distance.findById", query = "SELECT d FROM Distance d WHERE d.id = :id"), @NamedQuery(name = "Distance.findByBucket", query = "SELECT d FROM Distance d WHERE d.bucket = :bucket")})
public class Distance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @Column(name = "BUCKET")
    private short bucket;
    @OneToMany(mappedBy = "distanceId")
    private Collection<Filter> filterCollection;

    public Distance() {
    }

    public Distance(String id) {
        this.id = id;
    }

    public Distance(String id, short bucket) {
        this.id = id;
        this.bucket = bucket;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public short getBucket() {
        return bucket;
    }

    public void setBucket(short bucket) {
        this.bucket = bucket;
    }

    public Collection<Filter> getFilterCollection() {
        return filterCollection;
    }

    public void setFilterCollection(Collection<Filter> filterCollection) {
        this.filterCollection = filterCollection;
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
        if (!(object instanceof Distance)) {
            return false;
        }
        Distance other = (Distance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Distance[id=" + id + "]";
    }

}
