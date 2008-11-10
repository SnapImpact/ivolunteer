/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dave
 */
@Entity
@Table(name = "TIMESTAMP")
@NamedQueries({@NamedQuery(name = "Timestamp.findAll", query = "SELECT t FROM Timestamp t"), @NamedQuery(name = "Timestamp.findById", query = "SELECT t FROM Timestamp t WHERE t.id = :id"), @NamedQuery(name = "Timestamp.findByTimestamp", query = "SELECT t FROM Timestamp t WHERE t.timestamp = :timestamp")})
public class Timestamp implements Serializable, IdInterface {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @Column(name = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @ManyToMany(mappedBy = "timestampCollection")
    private Collection<Event> eventCollection;

    public Timestamp() {
    }

    public Timestamp(String id) {
        this.id = id;
    }

    public Timestamp(String id, Date timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Collection<Event> getEventCollection() {
        return eventCollection;
    }

    public void setEventCollection(Collection<Event> eventCollection) {
        this.eventCollection = eventCollection;
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
        if (!(object instanceof Timestamp)) {
            return false;
        }
        Timestamp other = (Timestamp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Timestamp[id=" + id + "]";
    }

}
