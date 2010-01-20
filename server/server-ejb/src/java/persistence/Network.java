/*
 *  Copyright (c) 2008 Boulder Community Foundation - iVolunteer
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
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
 * @author Dave Angulo
 */
@Entity
@Table(name = "NETWORK")
@NamedQueries( {
		@NamedQuery(name = "Network.findAll", query = "SELECT n FROM Network n"),
		@NamedQuery(name = "Network.findById", query = "SELECT n FROM Network n WHERE n.id = :id"),
		@NamedQuery(name = "Network.findByName", query = "SELECT n FROM Network n WHERE n.name = :name"),
		@NamedQuery(name = "Network.findByUrl", query = "SELECT n FROM Network n WHERE n.url = :url") })
public class Network implements Serializable, IdInterface {
	private static final long		serialVersionUID	= 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "id")
	private String					id;
	@Basic(optional = false)
	@Column(name = "name")
	private String					name;
	@Column(name = "url")
	private String					url;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "networkId")
	private Collection<Integration>	integrationCollection;

	public Network() {
	}

	public Network(String id) {
		this.id = id;
	}

	public Network(String id, String name) {
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
		if (!(object instanceof Network)) {
			return false;
		}
		Network other = (Network) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "persistence.Network[id=" + id + "]";
	}

}
