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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author dave
 */
@Entity
@Table(name = "ORGANIZATIONS")
@NamedQueries({@NamedQuery(name = "Organizations.findById", query = "SELECT o FROM Organizations o WHERE o.id = :id"), @NamedQuery(name = "Organizations.findByName", query = "SELECT o FROM Organizations o WHERE o.name = :name"), @NamedQuery(name = "Organizations.findByDescription", query = "SELECT o FROM Organizations o WHERE o.description = :description"), @NamedQuery(name = "Organizations.findByPhone", query = "SELECT o FROM Organizations o WHERE o.phone = :phone"), @NamedQuery(name = "Organizations.findByEmail", query = "SELECT o FROM Organizations o WHERE o.email = :email"), @NamedQuery(name = "Organizations.findByUrl", query = "SELECT o FROM Organizations o WHERE o.url = :url"), @NamedQuery(name = "Organizations.findByStreet", query = "SELECT o FROM Organizations o WHERE o.street = :street"), @NamedQuery(name = "Organizations.findByCity", query = "SELECT o FROM Organizations o WHERE o.city = :city"), @NamedQuery(name = "Organizations.findByState", query = "SELECT o FROM Organizations o WHERE o.state = :state"), @NamedQuery(name = "Organizations.findByZip", query = "SELECT o FROM Organizations o WHERE o.zip = :zip"), @NamedQuery(name = "Organizations.findBySourceKey", query = "SELECT o FROM Organizations o WHERE o.sourceKey = :sourceKey"), @NamedQuery(name = "Organizations.findBySourceUrl", query = "SELECT o FROM Organizations o WHERE o.sourceUrl = :sourceUrl")})
public class Organizations implements Serializable, IdInterface {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID", nullable = false)
    private String id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "EMAIL")
    private String email;
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
    @Column(name = "SOURCE_KEY")
    private String sourceKey;
    @Column(name = "SOURCE_URL")
    private String sourceUrl;
    @ManyToMany(mappedBy = "organizationIdCollection")
    private Collection<InterestAreas> interestAreaIdCollection;
    @JoinColumn(name = "ORGANIZATION_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private OrganizationTypes organizationTypeId;
    @JoinColumn(name = "SOURCE_ID", referencedColumnName = "ID")
    @ManyToOne
    private Sources sourceId;
    @OneToMany(mappedBy = "organizationId")
    private Collection<Events> eventsCollection;

    public Organizations() {
    }

    public Organizations(String id) {
        this.id = id;
    }

    public Organizations(String id, String name) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public OrganizationTypes getOrganizationTypeId() {
        return organizationTypeId;
    }

    public void setOrganizationTypeId(OrganizationTypes organizationTypeId) {
        this.organizationTypeId = organizationTypeId;
    }

    public Sources getSourceId() {
        return sourceId;
    }

    public void setSourceId(Sources sourceId) {
        this.sourceId = sourceId;
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
        if (!(object instanceof Organizations)) {
            return false;
        }
        Organizations other = (Organizations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Organizations[id=" + id + "]";
    }

}
