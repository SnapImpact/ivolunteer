/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "TIMEFRAME")
@NamedQueries( {
		@NamedQuery(name = "Timeframe.findAll", query = "SELECT t FROM Timeframe t"),
		@NamedQuery(name = "Timeframe.findById", query = "SELECT t FROM Timeframe t WHERE t.id = :id"),
		@NamedQuery(name = "Timeframe.findByBucket", query = "SELECT t FROM Timeframe t WHERE t.bucket = :bucket") })
public class Timeframe implements Serializable, IdInterface {
	private static final long	serialVersionUID	= 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "ID")
	private String				id;
	@Basic(optional = false)
	@Column(name = "NAME")
	private String				name;
	@Basic(optional = false)
	@Column(name = "BUCKET")
	private BigInteger			bucket;
	@OneToMany(mappedBy = "timeframeId")
	private Collection<Filter>	filterCollection;

	public Timeframe() {
	}

	public Timeframe(String id) {
		this.id = id;
	}

	public Timeframe(String id, String name, BigInteger bucket) {
		this.id = id;
		this.name = name;
		this.bucket = bucket;
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

	public BigInteger getBucket() {
		return bucket;
	}

	public void setBucket(BigInteger bucket) {
		this.bucket = bucket;
	}

	public Collection<Filter> getFilterCollection() {
		return filterCollection;
	}

	public void setFilterCollection(Collection<Filter> filterCollection) {
		this.filterCollection = filterCollection;
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
		if (!(object instanceof Timeframe)) {
			return false;
		}
		Timeframe other = (Timeframe) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "persistence.Timeframe[id=" + id + "]";
	}

}
