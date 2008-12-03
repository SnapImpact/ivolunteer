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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author dave
 */
@Entity
@Table(name = "FILTER")
@NamedQueries({@NamedQuery(name = "Filter.findAll", query = "SELECT f FROM Filter f"), @NamedQuery(name = "Filter.findById", query = "SELECT f FROM Filter f WHERE f.id = :id"), @NamedQuery(name = "Filter.findByLatitude", query = "SELECT f FROM Filter f WHERE f.latitude = :latitude"), @NamedQuery(name = "Filter.findByLongitude", query = "SELECT f FROM Filter f WHERE f.longitude = :longitude")})
public class Filter implements Serializable, IdInterface {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Column(name = "LATITUDE")
    private String latitude;
    @Column(name = "LONGITUDE")
    private String longitude;
    @JoinTable(name = "FILTER_ORGANIZATION_TYPE", joinColumns = {@JoinColumn(name = "FILTER_ID", referencedColumnName = "ID")}, inverseJoinColumns = {@JoinColumn(name = "ORGANIZATION_TYPE_ID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<OrganizationType> organizationTypeCollection;
    @JoinTable(name = "FILTER_INTEREST_AREA", joinColumns = {@JoinColumn(name = "FILTER_ID", referencedColumnName = "ID")}, inverseJoinColumns = {@JoinColumn(name = "INTEREST_AREA_ID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<InterestArea> interestAreaCollection;
    @JoinColumn(name = "DISTANCE_ID", referencedColumnName = "ID")
    @ManyToOne
    private Distance distanceId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IvUser userId;
    @JoinColumn(name = "TIMEFRAME_ID", referencedColumnName = "ID")
    @ManyToOne
    private Timeframe timeframeId;

    public Filter() {
    }

    public Filter(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Collection<OrganizationType> getOrganizationTypeCollection() {
        return organizationTypeCollection;
    }

    public void setOrganizationTypeCollection(Collection<OrganizationType> organizationTypeCollection) {
        this.organizationTypeCollection = organizationTypeCollection;
    }

    public Collection<InterestArea> getInterestAreaCollection() {
        return interestAreaCollection;
    }

    public void setInterestAreaCollection(Collection<InterestArea> interestAreaCollection) {
        this.interestAreaCollection = interestAreaCollection;
    }

    public Distance getDistanceId() {
        return distanceId;
    }

    public void setDistanceId(Distance distanceId) {
        this.distanceId = distanceId;
    }

    public IvUser getUserId() {
        return userId;
    }

    public void setUserId(IvUser userId) {
        this.userId = userId;
    }

    public Timeframe getTimeframeId() {
        return timeframeId;
    }

    public void setTimeframeId(Timeframe timeframeId) {
        this.timeframeId = timeframeId;
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
        if (!(object instanceof Filter)) {
            return false;
        }
        Filter other = (Filter) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Filter[id=" + id + "]";
    }

}
