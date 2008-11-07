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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author dave
 */
@Entity
@Table(name = "LOCATION")
@NamedQueries({@NamedQuery(name = "Location.findAll", query = "SELECT l FROM Location l"), @NamedQuery(name = "Location.findById", query = "SELECT l FROM Location l WHERE l.id = :id"), @NamedQuery(name = "Location.findByStreet", query = "SELECT l FROM Location l WHERE l.street = :street"), @NamedQuery(name = "Location.findByCity", query = "SELECT l FROM Location l WHERE l.city = :city"), @NamedQuery(name = "Location.findByState", query = "SELECT l FROM Location l WHERE l.state = :state"), @NamedQuery(name = "Location.findByZip", query = "SELECT l FROM Location l WHERE l.zip = :zip"), @NamedQuery(name = "Location.findByLocation", query = "SELECT l FROM Location l WHERE l.location = :location"), @NamedQuery(name = "Location.findByLatitude", query = "SELECT l FROM Location l WHERE l.latitude = :latitude"), @NamedQuery(name = "Location.findByLongitude", query = "SELECT l FROM Location l WHERE l.longitude = :longitude"), @NamedQuery(name = "Location.findByStreetZip", query = "SELECT l FROM Location l WHERE l.street = :street and l.zip = :zip")})
public class Location implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Column(name = "STREET")
    private String street;
    @Column(name = "CITY")
    private String city;
    @Column(name = "STATE")
    private String state;
    @Column(name = "ZIP")
    private String zip;
    @Column(name = "LOCATION")
    private String location;
    @Column(name = "LATITUDE")
    private String latitude;
    @Column(name = "LONGITUDE")
    private String longitude;
    @JoinTable(name = "ORGANIZATION_LOCATION", joinColumns = {@JoinColumn(name = "LOCATION_ID", referencedColumnName = "ID")}, inverseJoinColumns = {@JoinColumn(name = "ORGANIZATION_ID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<Organization> organizationCollection;
    @ManyToMany(mappedBy = "locationCollection")
    private Collection<Event> eventCollection;

    public Location() {
    }

    public Location(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public Collection<Organization> getOrganizationCollection() {
        return organizationCollection;
    }

    public void setOrganizationCollection(Collection<Organization> organizationCollection) {
        this.organizationCollection = organizationCollection;
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
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Location[id=" + id + "]";
    }

}
