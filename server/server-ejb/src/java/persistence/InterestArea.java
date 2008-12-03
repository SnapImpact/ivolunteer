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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "INTEREST_AREA")
@NamedQueries( {
		@NamedQuery(name = "InterestArea.findAll", query = "SELECT i FROM InterestArea i"),
		@NamedQuery(name = "InterestArea.findById", query = "SELECT i FROM InterestArea i WHERE i.id = :id"),
		@NamedQuery(name = "InterestArea.findByName", query = "SELECT i FROM InterestArea i WHERE i.name = :name") })
public class InterestArea implements Serializable, IdInterface {
	private static final long				serialVersionUID	= 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "ID")
	private String							id;
	@Basic(optional = false)
	@Column(name = "NAME")
	private String							name;
	@ManyToMany(mappedBy = "interestAreaCollection")
	private Collection<Event>				eventCollection;
	@JoinTable(name = "ORGANIZATION_INTEREST_AREA", joinColumns = { @JoinColumn(name = "INTEREST_AREA_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "ORGANIZATION_ID", referencedColumnName = "ID") })
	@ManyToMany
	private Collection<Organization>		organizationCollection;
	@ManyToMany(mappedBy = "interestAreaCollection")
	private Collection<Filter>				filterCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "interestAreaId")
	private Collection<SourceInterestMap>	sourceInterestMapCollection;

	public InterestArea() {
	}

	public InterestArea(String id) {
		this.id = id;
	}

	public InterestArea(String id, String name) {
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

	public Collection<Event> getEventCollection() {
		return eventCollection;
	}

	public void setEventCollection(Collection<Event> eventCollection) {
		this.eventCollection = eventCollection;
	}

	public Collection<Organization> getOrganizationCollection() {
		return organizationCollection;
	}

	public void setOrganizationCollection(Collection<Organization> organizationCollection) {
		this.organizationCollection = organizationCollection;
	}

	public Collection<Filter> getFilterCollection() {
		return filterCollection;
	}

	public void setFilterCollection(Collection<Filter> filterCollection) {
		this.filterCollection = filterCollection;
	}

	public Collection<SourceInterestMap> getSourceInterestMapCollection() {
		return sourceInterestMapCollection;
	}

	public void setSourceInterestMapCollection(
			Collection<SourceInterestMap> sourceInterestMapCollection) {
		this.sourceInterestMapCollection = sourceInterestMapCollection;
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
		if (!(object instanceof InterestArea)) {
			return false;
		}
		InterestArea other = (InterestArea) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "persistence.InterestArea[id=" + id + "]";
	}

}
