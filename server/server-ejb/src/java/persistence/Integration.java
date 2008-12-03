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
@Table(name = "INTEGRATION")
@NamedQueries( {
		@NamedQuery(name = "Integration.findAll", query = "SELECT i FROM Integration i"),
		@NamedQuery(name = "Integration.findById", query = "SELECT i FROM Integration i WHERE i.id = :id"),
		@NamedQuery(name = "Integration.findByUserName", query = "SELECT i FROM Integration i WHERE i.userName = :userName"),
		@NamedQuery(name = "Integration.findByPassword", query = "SELECT i FROM Integration i WHERE i.password = :password") })
public class Integration implements Serializable, IdInterface {
	private static final long	serialVersionUID	= 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "ID")
	private String				id;
	@Basic(optional = false)
	@Column(name = "USER_NAME")
	private String				userName;
	@Column(name = "PASSWORD")
	private String				password;
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	@ManyToOne(optional = false)
	private IvUser				userId;
	@JoinColumn(name = "NETWORK_ID", referencedColumnName = "ID")
	@ManyToOne(optional = false)
	private Network				networkId;

	public Integration() {
	}

	public Integration(String id) {
		this.id = id;
	}

	public Integration(String id, String userName) {
		this.id = id;
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public IvUser getUserId() {
		return userId;
	}

	public void setUserId(IvUser userId) {
		this.userId = userId;
	}

	public Network getNetworkId() {
		return networkId;
	}

	public void setNetworkId(Network networkId) {
		this.networkId = networkId;
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
		if (!(object instanceof Integration)) {
			return false;
		}
		Integration other = (Integration) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "persistence.Integration[id=" + id + "]";
	}

}
