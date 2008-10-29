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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author dave
 */
@Entity
@Table(name = "INTEREST_AREAS")
@NamedQueries({@NamedQuery(name = "InterestAreas.findById", query = "SELECT i FROM InterestAreas i WHERE i.id = :id"), @NamedQuery(name = "InterestAreas.findByName", query = "SELECT i FROM InterestAreas i WHERE i.name = :name")})
public class InterestAreas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID", nullable = false)
    private String id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @JoinTable(name = "ORGANIZATION_INTEREST_AREAS", joinColumns = {@JoinColumn(name = "INTEREST_AREA_ID", referencedColumnName = "ID")}, inverseJoinColumns = {@JoinColumn(name = "ORGANIZATION_ID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<Organizations> organizationIdCollection;
    @ManyToMany(mappedBy = "interestAreaIdCollection")
    private Collection<Events> eventIdCollection;
    @ManyToMany(mappedBy = "interestAreaIdCollection")
    private Collection<Filter> filterIdCollection;

    public InterestAreas() {
    }

    public InterestAreas(String id) {
        this.id = id;
    }

    public InterestAreas(String id, String name) {
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

    public Collection<Organizations> getOrganizationIdCollection() {
        return organizationIdCollection;
    }

    public void setOrganizationIdCollection(Collection<Organizations> organizationIdCollection) {
        this.organizationIdCollection = organizationIdCollection;
    }

    public Collection<Events> getEventIdCollection() {
        return eventIdCollection;
    }

    public void setEventIdCollection(Collection<Events> eventIdCollection) {
        this.eventIdCollection = eventIdCollection;
    }

    public Collection<Filter> getFilterIdCollection() {
        return filterIdCollection;
    }

    public void setFilterIdCollection(Collection<Filter> filterIdCollection) {
        this.filterIdCollection = filterIdCollection;
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
        if (!(object instanceof InterestAreas)) {
            return false;
        }
        InterestAreas other = (InterestAreas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.InterestAreas[id=" + id + "]";
    }

}
