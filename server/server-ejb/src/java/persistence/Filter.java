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
@NamedQueries({@NamedQuery(name = "Filter.findById", query = "SELECT f FROM Filter f WHERE f.id = :id"), @NamedQuery(name = "Filter.findByLatitude", query = "SELECT f FROM Filter f WHERE f.latitude = :latitude"), @NamedQuery(name = "Filter.findByLongitude", query = "SELECT f FROM Filter f WHERE f.longitude = :longitude")})
public class Filter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID", nullable = false)
    private String id;
    @Column(name = "LATITUDE")
    private String latitude;
    @Column(name = "LONGITUDE")
    private String longitude;
    @JoinTable(name = "FILTER_ORGANIZATION_TYPES", joinColumns = {@JoinColumn(name = "FILTER_ID", referencedColumnName = "ID")}, inverseJoinColumns = {@JoinColumn(name = "ORGANIZATION_TYPE_ID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<OrganizationTypes> organizationTypeIdCollection;
    @JoinTable(name = "FILTER_INTEREST_AREAS", joinColumns = {@JoinColumn(name = "FILTER_ID", referencedColumnName = "ID")}, inverseJoinColumns = {@JoinColumn(name = "INTEREST_AREA_ID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<InterestAreas> interestAreaIdCollection;
    @JoinColumn(name = "DISTANCE_ID", referencedColumnName = "ID")
    @ManyToOne
    private Distances distanceId;
    @JoinColumn(name = "TIMEFRAME_ID", referencedColumnName = "ID")
    @ManyToOne
    private Timeframes timeframeId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne
    private Users userId;

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

    public Collection<OrganizationTypes> getOrganizationTypeIdCollection() {
        return organizationTypeIdCollection;
    }

    public void setOrganizationTypeIdCollection(Collection<OrganizationTypes> organizationTypeIdCollection) {
        this.organizationTypeIdCollection = organizationTypeIdCollection;
    }

    public Collection<InterestAreas> getInterestAreaIdCollection() {
        return interestAreaIdCollection;
    }

    public void setInterestAreaIdCollection(Collection<InterestAreas> interestAreaIdCollection) {
        this.interestAreaIdCollection = interestAreaIdCollection;
    }

    public Distances getDistanceId() {
        return distanceId;
    }

    public void setDistanceId(Distances distanceId) {
        this.distanceId = distanceId;
    }

    public Timeframes getTimeframeId() {
        return timeframeId;
    }

    public void setTimeframeId(Timeframes timeframeId) {
        this.timeframeId = timeframeId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
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
