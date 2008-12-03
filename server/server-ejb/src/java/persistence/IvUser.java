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
@Table(name = "IV_USER")
@NamedQueries( {
		@NamedQuery(name = "IvUser.findAll", query = "SELECT i FROM IvUser i"),
		@NamedQuery(name = "IvUser.findById", query = "SELECT i FROM IvUser i WHERE i.id = :id"),
		@NamedQuery(name = "IvUser.findByName", query = "SELECT i FROM IvUser i WHERE i.name = :name"),
		@NamedQuery(name = "IvUser.findByPassword", query = "SELECT i FROM IvUser i WHERE i.password = :password"),
		@NamedQuery(name = "IvUser.findByIphoneId", query = "SELECT i FROM IvUser i WHERE i.iphoneId = :iphoneId") })
public class IvUser implements Serializable, IdInterface {
	private static final long		serialVersionUID	= 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "ID")
	private String					id;
	@Basic(optional = false)
	@Column(name = "NAME")
	private String					name;
	@Basic(optional = false)
	@Column(name = "PASSWORD")
	private String					password;
	@Column(name = "IPHONE_ID")
	private String					iphoneId;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
	private Collection<Filter>		filterCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
	private Collection<Integration>	integrationCollection;

	public IvUser() {
	}

	public IvUser(String id) {
		this.id = id;
	}

	public IvUser(String id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIphoneId() {
		return iphoneId;
	}

	public void setIphoneId(String iphoneId) {
		this.iphoneId = iphoneId;
	}

	public Collection<Filter> getFilterCollection() {
		return filterCollection;
	}

	public void setFilterCollection(Collection<Filter> filterCollection) {
		this.filterCollection = filterCollection;
	}

	public Collection<Integration> getIntegrationCollection() {
		return integrationCollection;
	}

	public void setIntegrationCollection(Collection<Integration> integrationCollection) {
		this.integrationCollection = integrationCollection;
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
		if (!(object instanceof IvUser)) {
			return false;
		}
		IvUser other = (IvUser) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "persistence.IvUser[id=" + id + "]";
	}

}
