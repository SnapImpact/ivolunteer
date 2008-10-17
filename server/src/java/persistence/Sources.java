/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.Serializable;
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
@Table(name = "SOURCES")
@NamedQueries({@NamedQuery(name = "Sources.findById", query = "SELECT s FROM Sources s WHERE s.id = :id"), @NamedQuery(name = "Sources.findByName", query = "SELECT s FROM Sources s WHERE s.name = :name"), @NamedQuery(name = "Sources.findByEtlClass", query = "SELECT s FROM Sources s WHERE s.etlClass = :etlClass")})
public class Sources implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID", nullable = false)
    private String id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ETL_CLASS")
    private String etlClass;
    @OneToMany(mappedBy = "sourceId")
    private Collection<Organizations> organizationsCollection;
    @OneToMany(mappedBy = "sourceId")
    private Collection<Events> eventsCollection;

    public Sources() {
    }

    public Sources(String id) {
        this.id = id;
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

    public String getEtlClass() {
        return etlClass;
    }

    public void setEtlClass(String etlClass) {
        this.etlClass = etlClass;
    }

    public Collection<Organizations> getOrganizationsCollection() {
        return organizationsCollection;
    }

    public void setOrganizationsCollection(Collection<Organizations> organizationsCollection) {
        this.organizationsCollection = organizationsCollection;
    }

    public Collection<Events> getEventsCollection() {
        return eventsCollection;
    }

    public void setEventsCollection(Collection<Events> eventsCollection) {
        this.eventsCollection = eventsCollection;
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
        if (!(object instanceof Sources)) {
            return false;
        }
        Sources other = (Sources) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Sources[id=" + id + "]";
    }

}
