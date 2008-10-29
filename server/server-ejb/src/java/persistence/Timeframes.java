/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
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
@Table(name = "TIMEFRAMES")
@NamedQueries({@NamedQuery(name = "Timeframes.findById", query = "SELECT t FROM Timeframes t WHERE t.id = :id"), @NamedQuery(name = "Timeframes.findByBucket", query = "SELECT t FROM Timeframes t WHERE t.bucket = :bucket")})
public class Timeframes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID", nullable = false)
    private String id;
    @Column(name = "BUCKET", nullable = false)
    private BigInteger bucket;
    @OneToMany(mappedBy = "timeframeId")
    private Collection<Filter> filterCollection;

    public Timeframes() {
    }

    public Timeframes(String id) {
        this.id = id;
    }

    public Timeframes(String id, BigInteger bucket) {
        this.id = id;
        this.bucket = bucket;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigInteger getBucket() {
        return bucket;
    }

    public void setBucket(BigInteger bucket) {
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
        if (!(object instanceof Timeframes)) {
            return false;
        }
        Timeframes other = (Timeframes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Timeframes[id=" + id + "]";
    }

}
