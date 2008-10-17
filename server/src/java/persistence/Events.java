/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dave
 */
@Entity
@Table(name = "EVENTS")
@NamedQueries({@NamedQuery(name = "Events.findById", query = "SELECT e FROM Events e WHERE e.id = :id"), @NamedQuery(name = "Events.findByTitle", query = "SELECT e FROM Events e WHERE e.title = :title"), @NamedQuery(name = "Events.findByDescription", query = "SELECT e FROM Events e WHERE e.description = :description"), @NamedQuery(name = "Events.findByTimestamp", query = "SELECT e FROM Events e WHERE e.timestamp = :timestamp"), @NamedQuery(name = "Events.findByDuration", query = "SELECT e FROM Events e WHERE e.duration = :duration"), @NamedQuery(name = "Events.findByLocation", query = "SELECT e FROM Events e WHERE e.location = :location"), @NamedQuery(name = "Events.findByLatitude", query = "SELECT e FROM Events e WHERE e.latitude = :latitude"), @NamedQuery(name = "Events.findByLongitude", query = "SELECT e FROM Events e WHERE e.longitude = :longitude"), @NamedQuery(name = "Events.findByContact", query = "SELECT e FROM Events e WHERE e.contact = :contact"), @NamedQuery(name = "Events.findByUrl", query = "SELECT e FROM Events e WHERE e.url = :url"), @NamedQuery(name = "Events.findByStreet", query = "SELECT e FROM Events e WHERE e.street = :street"), @NamedQuery(name = "Events.findByCity", query = "SELECT e FROM Events e WHERE e.city = :city"), @NamedQuery(name = "Events.findByState", query = "SELECT e FROM Events e WHERE e.state = :state"), @NamedQuery(name = "Events.findByZip", query = "SELECT e FROM Events e WHERE e.zip = :zip"), @NamedQuery(name = "Events.findByPhone", query = "SELECT e FROM Events e WHERE e.phone = :phone"), @NamedQuery(name = "Events.findByEmail", query = "SELECT e FROM Events e WHERE e.email = :email"), @NamedQuery(name = "Events.findBySourceKey", query = "SELECT e FROM Events e WHERE e.sourceKey = :sourceKey"), @NamedQuery(name = "Events.findBySourceUrl", query = "SELECT e FROM Events e WHERE e.sourceUrl = :sourceUrl")})
public class Events implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID", nullable = false)
    private String id;
    @Column(name = "TITLE", nullable = false)
    private String title;
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
    @Column(name = "TIMESTAMP", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Column(name = "DURATION")
    private Short duration;
    @Column(name = "LOCATION")
    private String location;
    @Column(name = "LATITUDE")
    private String latitude;
    @Column(name = "LONGITUDE")
    private String longitude;
    @Column(name = "CONTACT")
    private String contact;
    @Column(name = "URL")
    private String url;
    @Column(name = "STREET")
    private String street;
    @Column(name = "CITY")
    private String city;
    @Column(name = "STATE")
    private String state;
    @Column(name = "ZIP")
    private String zip;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "SOURCE_KEY")
    private String sourceKey;
    @Column(name = "SOURCE_URL")
    private String sourceUrl;
    @JoinTable(name = "EVENT_INTEREST_AREAS", joinColumns = {@JoinColumn(name = "EVENT_ID", referencedColumnName = "ID")}, inverseJoinColumns = {@JoinColumn(name = "INTEREST_AREA_ID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<InterestAreas> interestAreaIdCollection;
    @JoinColumn(name = "ORGANIZATION_ID", referencedColumnName = "ID")
    @ManyToOne
    private Organizations organizationId;
    @JoinColumn(name = "SOURCE_ID", referencedColumnName = "ID")
    @ManyToOne
    private Sources sourceId;

    public Events() {
    }

    public Events(String id) {
        this.id = id;
    }

    public Events(String id, String title, String description, Date timestamp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Short getDuration() {
        return duration;
    }

    public void setDuration(Short duration) {
        this.duration = duration;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public Collection<InterestAreas> getInterestAreaIdCollection() {
        return interestAreaIdCollection;
    }

    public void setInterestAreaIdCollection(Collection<InterestAreas> interestAreaIdCollection) {
        this.interestAreaIdCollection = interestAreaIdCollection;
    }

    public Organizations getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Organizations organizationId) {
        this.organizationId = organizationId;
    }

    public Sources getSourceId() {
        return sourceId;
    }

    public void setSourceId(Sources sourceId) {
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
        if (!(object instanceof Events)) {
            return false;
        }
        Events other = (Events) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Events[id=" + id + "]";
    }

}
