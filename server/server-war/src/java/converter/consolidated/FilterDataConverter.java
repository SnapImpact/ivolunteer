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
package converter.consolidated;

import converter.list.*;
import persistence.Distance;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import persistence.IdInterface;
import persistence.InterestArea;
import persistence.OrganizationType;
import persistence.Source;
import persistence.Timeframe;

/**
 * 
 * @author Dave Angulo
 */
@XmlRootElement(name = "filterDataConsolidated")
public class FilterDataConverter {

    private HashMap<String, Collection<? extends IdInterface>> data;
    private URI uri;
    private URI baseUri;
    private int expandLevel;

    /** Creates a new instance of EventListConverter */
    public FilterDataConverter() {
    }

    /**
     * Creates a new instance of EventListConverter.
     *
     * @param entities
     *            associated entities
     * @param uri
     *            associated uri
     */
    public FilterDataConverter(HashMap<String, Collection<? extends IdInterface>> data, URI uri, URI baseUri, int expandLevel) {
        this.data = data;
        this.uri = uri;
        this.baseUri = baseUri;
        this.expandLevel = expandLevel;
    }

    @XmlElement(name = "distances")
    public Collection<DistanceRecordConverter> getDistancesConverter() {
        return new DistanceRecordsConverter((Collection<Distance>) data.get("distances"), uri, baseUri).getRecords();
    }

    @XmlElement(name = "interestAreas")
    public Collection<InterestAreaRecordConverter> getInteerestAreasConverter() {
        return new InterestAreaRecordsConverter((Collection<InterestArea>) data.get("interestAreas"), uri, baseUri).getRecords();
    }

    @XmlElement(name = "organizationTypes")
    public Collection<OrganizationTypeRecordConverter> getOrganizationTypesConverter() {
        return new OrganizationTypeRecordsConverter((Collection<OrganizationType>) data.get("organizationTypes"), uri, baseUri).getRecords();
    }

    @XmlElement(name = "sources")
    public Collection<SourceRecordConverter> getSourcesConverter() {
        return new SourceRecordsConverter((Collection<Source>) data.get("sources"), uri, baseUri).getRecords();
    }

    @XmlElement(name = "timeframes")
    public Collection<TimeframeRecordConverter> getTimeframesRecordsConverter() {
        return new TimeframeRecordsConverter((Collection<Timeframe>) data.get("timeFrames"), uri, baseUri).getRecords();
    }
}
