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
package service;

import java.util.Collection;
import java.util.HashMap;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import com.sun.jersey.api.core.ResourceContext;
import persistence.IdInterface;
import converter.consolidated.FilterDataConverter;
import persistence.Distance;
import persistence.InterestArea;
import persistence.OrganizationType;
import persistence.Source;
import persistence.Timeframe;

/**
 * 
 * @author Dave Angulo
 */
@Path("/filterData/")
public class FilterDataResource extends Base {

    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;

    /** Creates a new instance of LocationsResource */
    public FilterDataResource() {
    }

    /**
     * Get method for retrieving a collection of Location instance in XML
     * format.
     *
     * @return an instance of LocationsConverter
     */
    @GET
    @Produces({"application/json", "application/xml"})
    public FilterDataConverter get(@QueryParam("start") @DefaultValue("0") int start,
            @QueryParam("max") @DefaultValue("10") int max,
            @QueryParam("expandLevel") @DefaultValue("1") int expandLevel) {
        return new FilterDataConverter(getAllEntities(start, max), uriInfo.getAbsolutePath(),
                uriInfo.getBaseUri(), expandLevel);
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Location instances
     */
    protected HashMap<String, Collection<? extends IdInterface>> getAllEntities(int start, int max) {
        HashMap<String, Collection<? extends IdInterface>> ret = new HashMap<String, Collection<? extends IdInterface>>();
        ret.put("distances", (Collection<Distance>) super.getEntities(start, max, "SELECT e FROM Distance e"));
        ret.put("interestAreas", (Collection<InterestArea>) super.getEntities(start, max, "SELECT e FROM InterestArea e"));
        ret.put("organizationTypes", (Collection<OrganizationType>) super.getEntities(start, max, "SELECT e FROM OrganizationType e"));
        ret.put("sources", (Collection<Source>) super.getEntities(start, max, "SELECT e FROM Source e"));
        ret.put("timeframes", (Collection<Timeframe>) super.getEntities(start, max, "SELECT e FROM Timeframe e"));
        return ret;
    }
}
