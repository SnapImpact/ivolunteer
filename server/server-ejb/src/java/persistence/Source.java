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
@Table(name = "SOURCE")
@NamedQueries({@NamedQuery(name = "Source.findAll", query = "SELECT s FROM Source s"), @NamedQuery(name = "Source.findById", query = "SELECT s FROM Source s WHERE s.id = :id"), @NamedQuery(name = "Source.findByName", query = "SELECT s FROM Source s WHERE s.name = :name"), @NamedQuery(name = "Source.findByEtlClass", query = "SELECT s FROM Source s WHERE s.etlClass = :etlClass")})
public class Source implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ETL_CLASS")
    private String etlClass;
    @OneToMany(mappedBy = "sourceId")
    private Collection<Organization> organizationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sourceId")
    private Collection<SourceInterestMap> sourceInterestMapCollection;
    @OneToMany(mappedBy = "sourceId")
    private Collection<Event> eventCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sourceId")
    private Collection<SourceOrgTypeMap> sourceOrgTypeMapCollection;

    public Source() {
    }

    public Source(String id) {
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

    public Collection<Organization> getOrganizationCollection() {
        return organizationCollection;
    }

    public void setOrganizationCollection(Collection<Organization> organizationCollection) {
        this.organizationCollection = organizationCollection;
    }

    public Collection<SourceInterestMap> getSourceInterestMapCollection() {
        return sourceInterestMapCollection;
    }

    public void setSourceInterestMapCollection(Collection<SourceInterestMap> sourceInterestMapCollection) {
        this.sourceInterestMapCollection = sourceInterestMapCollection;
    }

    public Collection<Event> getEventCollection() {
        return eventCollection;
    }

    public void setEventCollection(Collection<Event> eventCollection) {
        this.eventCollection = eventCollection;
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
        if (!(object instanceof Source)) {
            return false;
        }
        Source other = (Source) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Source[id=" + id + "]";
    }

}
