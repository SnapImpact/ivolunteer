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

import persistence.Distance;
import java.net.URI;
import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Dave Angulo
 */

@XmlRootElement(name = "distances")
public class DistanceListConverter {
	private IdListConverter				idListConverter;
	private DistanceRecordsConverter	distanceRecordsConverter;

	/** Creates a new instance of DistanceListConverter */
	public DistanceListConverter() {
	}

	/**
	 * Creates a new instance of DistanceListConverter.
	 * 
	 * @param entities
	 *            associated entities
	 * @param uri
	 *            associated uri
	 */
	public DistanceListConverter(Collection<Distance> records, URI uri, URI baseUri) {
		this.idListConverter = new IdListConverter(records);
		this.distanceRecordsConverter = new DistanceRecordsConverter(records, uri, baseUri);
	}

	@XmlElement(name = "ids")
	public Collection<String> getIdListConverter() {
		return idListConverter.getIds();
	}

	@XmlElement(name = "records")
	public Collection<DistanceRecordConverter> getEventsRecordsConverter() {
		return distanceRecordsConverter.getRecords();
	}
}
