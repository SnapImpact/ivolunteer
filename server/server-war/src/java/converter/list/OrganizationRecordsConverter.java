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

package converter.list;

import java.net.URI;
import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import persistence.Organization;

/**
 * 
 * @author Dave Angulo
 */

@XmlRootElement(name = "records")
public class OrganizationRecordsConverter {
	private Collection<Organization>	records;
	private URI							uri;
	private URI							baseUri;

	/** Creates a new instance of OrganizationRecordsConverter */
	public OrganizationRecordsConverter() {
	}

	/**
	 * Creates a new instance of OrganizationRecordsConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 */
	public OrganizationRecordsConverter(Collection<Organization> records, URI uri, URI baseUri) {
		this.records = records;
		this.uri = uri;
		this.baseUri = baseUri;
	}

	@XmlElement
	public ArrayList<OrganizationRecordConverter> getRecords() {
		ArrayList<OrganizationRecordConverter> ret = new ArrayList<OrganizationRecordConverter>();
		if (records != null) {
			for (Organization record : records) {
				ret.add(new OrganizationRecordConverter(record, baseUri.resolve("organizations/"
						+ record.getId() + "/"), 1));
			}
		}
		return ret;
	}

}
