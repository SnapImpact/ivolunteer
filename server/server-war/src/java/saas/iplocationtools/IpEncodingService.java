/*
 * 
 * Copyright (c) 2009 Boulder Community Foundation - iVolunteer
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */

package saas.iplocationtools;

import java.io.IOException;
import org.netbeans.saas.RestConnection;
import org.netbeans.saas.RestResponse;

/**
 * GoogleGeocodingService Service
 *
 * @author Dave Angulo <daveangulo@actionfeed.org>
 */

public class IpEncodingService {

    /** Creates a new instance of IpEncodingService */
    public IpEncodingService() {
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch(Throwable th) {}
    }

    /**
     *
     * @param q
     * @param output
     * @return an instance of RestResponse
     */
    public static RestResponse ipcode(String ip, String output) throws IOException {
        String[][] pathParams = new String[][]{};
        String[][] queryParams = new String[][]{{"ip", ip}, {"output", output}};
        RestConnection conn = new RestConnection("http://iplocationtools.com/ip_query.php", pathParams, queryParams);
        sleep(1000);
        return conn.get(null);
    }
}
