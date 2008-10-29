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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author dave
 */
@Entity
@Table(name = "ORGANIZATION_TYPES")
@NamedQueries({@NamedQuery(name = "OrganizationTypes.findById", query = "SELECT o FROM OrganizationTypes o WHERE o.id = :id"), @NamedQuery(name = "OrganizationTypes.findByName", query = "SELECT o FROM OrganizationTypes o WHERE o.name = :name")})
public class OrganizationTypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID", nullable = false)
    private String id;
    @Column(name = "NAME")
    private String name;
    @ManyToMany(mappedBy = "organizationTypeIdCollection")
    private Collection<Filter> filterIdCollection;
    @OneToMany(mappedBy = "organizationTypeId")
    private Collection<Organizations> organizationsCollection;

    public OrganizationTypes() {
    }

    public OrganizationTypes(String id) {
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

    public Collection<Filter> getFilterIdCollection() {
        return filterIdCollection;
    }

    public void setFilterIdCollection(Collection<Filter> filterIdCollection) {
        this.filterIdCollection = filterIdCollection;
    }

    public Collection<Organizations> getOrganizationsCollection() {
        return organizationsCollection;
    }

    public void setOrganizationsCollection(Collection<Organizations> organizationsCollection) {
        this.organizationsCollection = organizationsCollection;
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
        if (!(object instanceof OrganizationTypes)) {
            return false;
        }
        OrganizationTypes other = (OrganizationTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.OrganizationTypes[id=" + id + "]";
    }

}
