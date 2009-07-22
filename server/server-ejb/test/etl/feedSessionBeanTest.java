/*
* Copyright (c) 2008 Boulder Community Foundation - iVolunteer
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
*/
package etl;
//
import junit.framework.*;
import java.io.*;


public class feedSessionBeanTest extends TestCase 
{
    
    protected void setUp() {}
    protected void tearDown() {}


    public static void test_getTextValue()
    {
        String location = feedSessionBean.getTextValue( SampleHeader, "Location:" );
        System.out.println( "*** location=" + location );
        Assert.assertTrue( location.length() > 0 );
    }

    public static void test_getUrlHeader()
    {
        try
        {
            String header = feedSessionBean.getHeaderFromURL( "http://google.com" );
            System.out.println( header );
            Assert.assertTrue( header.length() > 0 );
        }
        catch( Exception ex )
        {
            System.out.println( "Error=" + ex );
            Assert.fail( "getting header failed, error=" + ex );
        }
    }
    
    public static void test_wrapXml()
    {
        StringBuilder content = new StringBuilder( SampleFeed  );
        feedSessionBean.wrapXml( content , "<VomlData xmlns=\"http://www.networkforgood.org/xml/namespaces/voml/\">", "</VomlData>" );
        System.out.println( content );
        Assert.assertTrue( content.indexOf( "</VomlData>" ) > 0 );
    }
    

    public static void test_TmpFile()
    {
        try
        {
            StringBuilder content = new StringBuilder( SampleFeed  );
            feedSessionBean.wrapXml( content , "<VomlData xmlns=\"http://www.networkforgood.org/xml/namespaces/voml/\">", "</VomlData>" );
            System.out.println( content );
            Assert.assertTrue( content.indexOf( "</VomlData>" ) > 0 );
        
            File tempfile = File.createTempFile( "one", ".xml");
            String theFileName = tempfile.getCanonicalPath();
            System.out.println( "*** wrote content to tmp file=" + theFileName );
            Assert.assertTrue( theFileName.length() > 0 );
            
            feedSessionBean.writeXmlToFile( content.toString(), theFileName );
        }
        catch( Exception ex )
        {
            System.out.println( "Error=" + ex );
            Assert.fail( "Creating temp file failed, error=" + ex );
        }
    }

    public static void test_ContentInTmpFile()
    {
        try
        {
            File tempfile = File.createTempFile( "one", ".xml");
            String theFileName = tempfile.getCanonicalPath();
            System.out.println( "*** tmp file=" + theFileName );
            Assert.assertTrue( theFileName.length() > 0 );
        }
        catch( Exception ex )
        {
            System.out.println( "Error=" + ex );
            Assert.fail( "Creating temp file failed, error=" + ex );
        }
    }

        
    static String SampleFeed = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
        "<VolunteerOpportunities xmlns=\"http://www.networkforgood.org/xml/namespaces/voml/\">\n" +
        "<VolunteerOpportunity><LocalID>1:64:533228</LocalID>\n" +
        "  <AffiliateID>1</AffiliateID>\n" +
        "  <Categories>\n" +
        "    <Category>\n" +
        "      <CategoryID>5</CategoryID>\n" +
        "    </Category>\n" +
        "    <Category>\n" +
        "      <CategoryID>6</CategoryID>\n" +
        "    </Category>\n" +
        "  </Categories>\n" +
        "  <DateListed>2009-03-12</DateListed>\n" +
        "  <Title>Miami Rescue Mission (FX)</Title>\n" +
        "</VolunteerOpportunity>\n" +
        "</VolunteerOpportunities>\n" +
        "<Timestamp>1245273333</Timestamp>\n";

    static String SampleHeader = "< HTTP/1.1 302 Found\n" +
        " Date: Wed, 15 Jul 2009 00:59:22 GMT\n" +
        "  Server: Apache\n" +
        "  Set-Cookie2: Apache=174.129.253.132.1247619562041578; path=/;\n" +
        "  max-age=604800; domain=.demo.handsonnetwork.org; version=1\n" +
        "  Set-Cookie: PHPSESSID=928725f9aaf3126aeac56df39bc13daa; path=/\n" +
        "  Expires: Thu, 19 Nov 1981 08:52:00 GMT\n" +
        "  Cache-Control: no-store, no-cache, must-revalidate, post-check=0, pre-check=0\n" +
        "  Pragma: no-cache\n" +
        "  Location: output/VOMLj3i46b\n" +
        "  Vary: Accept-Encoding\n" +
        "  Content-Length: 0\n" +
        "  Content-Type: text/html; charset=ISO-8859-1\n";


/*

root@ip-10-251-31-54:~# curl -v
http://demo.handsonnetwork.org/voml/?feed=handsonnetwork.org
* About to connect() to demo.handsonnetwork.org port 80 (#0)
*   Trying 66.135.32.197... connected
* Connected to demo.handsonnetwork.org (66.135.32.197) port 80 (#0)
> > GET /voml/?feed=handsonnetwork.org HTTP/1.1
> > User-Agent: curl/7.18.0 (i486-pc-linux-gnu) libcurl/7.18.0 OpenSSL/0.9.8g zlib/1.2.3.3 libidn/1.1
> > Host: demo.handsonnetwork.org
> > Accept: * / *
> >
< HTTP/1.1 302 Found
< Date: Wed, 15 Jul 2009 00:59:22 GMT
< Server: Apache
< Set-Cookie2: Apache=174.129.253.132.1247619562041578; path=/;
max-age=604800; domain=.demo.handsonnetwork.org; version=1
< Set-Cookie: PHPSESSID=928725f9aaf3126aeac56df39bc13daa; path=/
< Expires: Thu, 19 Nov 1981 08:52:00 GMT
< Cache-Control: no-store, no-cache, must-revalidate, post-check=0, pre-check=0
< Pragma: no-cache
< Location: output/VOMLj3i46b
< Vary: Accept-Encoding
< Content-Length: 0
< Content-Type: text/html; charset=ISO-8859-1
<
* Connection #0 to host demo.handsonnetwork.org left intact
* Closing connection #0


Then we do:

root@ip-10-251-31-54:~# curl -v
http://demo.handsonnetwork.org/voml/output/VOMLj3i46b >
handsonnetwork_071409.xml
* About to connect() to demo.handsonnetwork.org port 80 (#0)
*   Trying 66.135.32.197... connected
* Connected to demo.handsonnetwork.org (66.135.32.197) port 80 (#0)
> > GET /voml/output/VOMLj3i46b HTTP/1.1
> > User-Agent: curl/7.18.0 (i486-pc-linux-gnu) libcurl/7.18.0 OpenSSL/0.9.8g zlib/1.2.3.3 libidn/1.1
> > Host: demo.handsonnetwork.org
> > Accept: * / *
> >
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
  0     0    0     0    0     0      0      0 --:--:--  0:00:01
--:--:--     0< HTTP/1.1 200 OK
< Date: Wed, 15 Jul 2009 01:00:58 GMT
< Server: Apache
< Set-Cookie2: Apache=174.129.253.132.1247619659120306; path=/;
max-age=604800; domain=.demo.handsonnetwork.org; version=1
< Last-Modified: Wed, 15 Jul 2009 00:59:26 GMT
< ETag: "5d18e55-fd1ccb-46eb4110bd780"
< Accept-Ranges: bytes
< Content-Length: 16587979
< Content-Type: text/xml
<
{ [data not shown]
100 15.8M  100 15.8M    0     0   623k      0  0:00:25  0:00:25
--:--:--  879k* Connection #0 to host demo.handsonnetwork.org left
intact

* Closing connection #0

We then need to modify the file so its wrapped in this tag:

<?xml version="1.0" encoding="utf-8" ?>
<VomlData xmlns="http://www.networkforgood.org/xml/namespaces/voml/">
... foo ...
</vomlData>

Then repeat with curl -v
http://demo.handsonnetwork.org/voml/?feed=1-800-volunteer.org
*/
          
} // EOC
