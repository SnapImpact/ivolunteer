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
@Table(name = "ORGANIZATION")
@NamedQueries( {
		@NamedQuery(name = "Organization.findAll", query = "SELECT o FROM Organization o"),
		@NamedQuery(name = "Organization.findById", query = "SELECT o FROM Organization o WHERE o.id = :id"),
		@NamedQuery(name = "Organization.findByName", query = "SELECT o FROM Organization o WHERE o.name = :name"),
		@NamedQuery(name = "Organization.findByDescription", query = "SELECT o FROM Organization o WHERE o.description = :description"),
		@NamedQuery(name = "Organization.findByPhone", query = "SELECT o FROM Organization o WHERE o.phone = :phone"),
		@NamedQuery(name = "Organization.findByEmail", query = "SELECT o FROM Organization o WHERE o.email = :email"),
		@NamedQuery(name = "Organization.findByUrl", query = "SELECT o FROM Organization o WHERE o.url = :url"),
		@NamedQuery(name = "Organization.findBySourceKey", query = "SELECT o FROM Organization o WHERE o.sourceKey = :sourceKey"),
		@NamedQuery(name = "Organization.findBySourceUrl", query = "SELECT o FROM Organization o WHERE o.sourceUrl = :sourceUrl") })
public class Organization implements Serializable, IdInterface {
	private static final long			serialVersionUID	= 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "ID")
	private String						id;
	@Basic(optional = false)
	@Column(name = "NAME")
	private String						name;
	@Column(name = "DESCRIPTION")
	private String						description;
	@Column(name = "PHONE")
	private String						phone;
	@Column(name = "EMAIL")
	private String						email;
	@Column(name = "URL")
	private String						url;
	@Column(name = "SOURCE_KEY")
	private String						sourceKey;
	@Column(name = "SOURCE_URL")
	private String						sourceUrl;
	@ManyToMany(mappedBy = "organizationCollection")
	private Collection<Location>		locationCollection;
	@ManyToMany(mappedBy = "organizationCollection")
	private Collection<InterestArea>	interestAreaCollection;
	@ManyToMany(mappedBy = "organizationCollection")
	private Collection<Event>			eventCollection;
	@JoinColumn(name = "ORGANIZATION_TYPE_ID", referencedColumnName = "ID")
	@ManyToOne
	private OrganizationType			organizationTypeId;
	@JoinColumn(name = "SOURCE_ID", referencedColumnName = "ID")
	@ManyToOne
	private Source						sourceId;

	public Organization() {
	}

	public Organization(String id) {
		this.id = id;
	}

	public Organization(String id, String name) {
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

	public Collection<Location> getLocationCollection() {
		return locationCollection;
	}

	public void setLocationCollection(Collection<Location> locationCollection) {
		this.locationCollection = locationCollection;
	}

	public Collection<InterestArea> getInterestAreaCollection() {
		return interestAreaCollection;
	}

	public void setInterestAreaCollection(Collection<InterestArea> interestAreaCollection) {
		this.interestAreaCollection = interestAreaCollection;
	}

	public Collection<Event> getEventCollection() {
		return eventCollection;
	}

	public void setEventCollection(Collection<Event> eventCollection) {
		this.eventCollection = eventCollection;
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
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Organization)) {
			return false;
		}
		Organization other = (Organization) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "persistence.Organization[id=" + id + "]";
	}

}
