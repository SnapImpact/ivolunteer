/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author dave
 */
@Entity
@Table(name = "SOURCE_ORG_TYPE_MAP")
@NamedQueries({@NamedQuery(name = "SourceOrgTypeMap.findAll", query = "SELECT s FROM SourceOrgTypeMap s"), @NamedQuery(name = "SourceOrgTypeMap.findById", query = "SELECT s FROM SourceOrgTypeMap s WHERE s.id = :id"), @NamedQuery(name = "SourceOrgTypeMap.findBySourceKey", query = "SELECT s FROM SourceOrgTypeMap s WHERE s.sourceKey = :sourceKey")})
public class SourceOrgTypeMap implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @Column(name = "SOURCE_KEY")
    private String sourceKey;
    @JoinColumn(name = "ORGANIZATION_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private OrganizationType organizationTypeId;
    @JoinColumn(name = "SOURCE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Source sourceId;

    public SourceOrgTypeMap() {
    }

    public SourceOrgTypeMap(String id) {
        this.id = id;
    }

    public SourceOrgTypeMap(String id, String sourceKey) {
        this.id = id;
        this.sourceKey = sourceKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public OrganizationType getOrganizationTypeId() {
        return organizationTypeId;
    }

    public void setOrganizationTypeId(OrganizationType organizationTypeId) {
        this.organizationTypeId = organizationTypeId;
    }

    public Source getSourceId() {
        return sourceId;
    }

    public void setSourceId(Source sourceId) {
        this.sourceId = sourceId;
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
        if (!(object instanceof SourceOrgTypeMap)) {
            return false;
        }
        SourceOrgTypeMap other = (SourceOrgTypeMap) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.SourceOrgTypeMap[id=" + id + "]";
    }

}
