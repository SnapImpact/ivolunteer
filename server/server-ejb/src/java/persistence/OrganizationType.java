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
@Table(name = "ORGANIZATION_TYPE")
@NamedQueries({@NamedQuery(name = "OrganizationType.findAll", query = "SELECT o FROM OrganizationType o"), @NamedQuery(name = "OrganizationType.findById", query = "SELECT o FROM OrganizationType o WHERE o.id = :id"), @NamedQuery(name = "OrganizationType.findByName", query = "SELECT o FROM OrganizationType o WHERE o.name = :name")})
public class OrganizationType implements Serializable, IdInterface {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Column(name = "NAME")
    private String name;
    @ManyToMany(mappedBy = "organizationTypeCollection")
    private Collection<Filter> filterCollection;
    @OneToMany(mappedBy = "organizationTypeId")
    private Collection<Organization> organizationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organizationTypeId")
    private Collection<SourceOrgTypeMap> sourceOrgTypeMapCollection;

    public OrganizationType() {
    }

    public OrganizationType(String id) {
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

    public Collection<Filter> getFilterCollection() {
        return filterCollection;
    }

    public void setFilterCollection(Collection<Filter> filterCollection) {
        this.filterCollection = filterCollection;
    }

    public Collection<Organization> getOrganizationCollection() {
        return organizationCollection;
    }

    public void setOrganizationCollection(Collection<Organization> organizationCollection) {
        this.organizationCollection = organizationCollection;
    }

    public Collection<SourceOrgTypeMap> getSourceOrgTypeMapCollection() {
        return sourceOrgTypeMapCollection;
    }

    public void setSourceOrgTypeMapCollection(Collection<SourceOrgTypeMap> sourceOrgTypeMapCollection) {
        this.sourceOrgTypeMapCollection = sourceOrgTypeMapCollection;
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
        if (!(object instanceof OrganizationType)) {
            return false;
        }
        OrganizationType other = (OrganizationType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.OrganizationType[id=" + id + "]";
    }

}
