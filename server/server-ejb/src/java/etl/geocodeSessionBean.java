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

package etl;

import com.sun.org.apache.xpath.internal.NodeSet;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 
 * @author Mark Chance
 *
 * For reference on the Google Maps API for geocoding, see:
 * http://code.google.com/apis/maps/documentation/geocoding
 *
 */
@Stateless
public class geocodeSessionBean implements geocodeSessionLocal {
    private static final String GOOGLE_URL = "http://maps.google.com/maps/geo?";
    private static final String GOOGLE_MAPS_API_KEY = "ABQIAAAA8HXiU_-E98nF20YvZ37zAxQ3KXTeRMsCydUtpdwIbkIA5o2l6BSDT71ZHbxEWhRhZfsByNDyDiEtKA";
    private static Date LastGoogleRequest = new Date();
    private static long timeBetweenRequests = 1000L;
    /**
     * Given what is know of an address, determine the lat/lon
     * via Google Maps API
     *
     * @param loc
     */
	public void encodeAddress(persistence.Location loc) {
        // need something to go on.
        String addrToEncode = "";
        if ( !(isEmpty(loc.getStreet()) || loc.getStreet().equals("null null")))
            addrToEncode += loc.getStreet();
        if (!isEmpty(loc.getCity())) {
            if (addrToEncode.length()>0) addrToEncode += " ";
            addrToEncode += loc.getCity();
        }
        if (!isEmpty(loc.getState())) {
            if (addrToEncode.length()>0) addrToEncode += " ";
            addrToEncode += loc.getState();
        }
        if (!isEmpty(loc.getZip())) {
            if (addrToEncode.length()>0) addrToEncode += " ";
            addrToEncode += loc.getZip();
        }
        if (!isEmpty(loc.getCountry())) {
            if (addrToEncode.length()>0) addrToEncode += " ";
            addrToEncode += loc.getCountry();
        }
        if (addrToEncode.length() == 0) return;
        // now go to Google
        final String theXml = goToGoogle(addrToEncode, true);
        if (!parseXMLforLatLon(theXml, loc)) {
            // report error;
        }
    }

    public void decodeAddress(persistence.Location loc) {
        // need both lat and lon to decode
        if (isEmpty(loc.getLatitude()) || isEmpty(loc.getLongitude())) return;
        String latlon = loc.getLatitude() + "," + loc.getLongitude();
        // now go to Google.
        final String theXml = goToGoogle(latlon, false);
    }

    private boolean isEmpty(final String str) {
        return str==null || str.length() == 0 || str.equals("null");
    }
    private String goToGoogle(final String input, boolean wantLatLon) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(GOOGLE_URL).append("output=xml&oe=utf8&sensor=false&key=").append(GOOGLE_MAPS_API_KEY);
            sb.append("&q=");
            if (wantLatLon) {
                // then we have address input
                sb.append(input.replace(" ", "+"));
            } // else have lat,lon
            Date now = new Date();
            long dTime = now.getTime() - LastGoogleRequest.getTime();
            if ( dTime < timeBetweenRequests) {
                try {
                    Thread.sleep(timeBetweenRequests - dTime);
                } catch (InterruptedException ex) {
                    Logger.getLogger(geocodeSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            LastGoogleRequest = now;
            URL url = new URL(sb.toString());
            URLConnection connection = url.openConnection();
            int x;
            sb = new StringBuilder();
            while ((x = ((InputStream) connection.getContent()).read()) != -1) {
                sb.append((char) x);
            }
            return sb.toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(geocodeSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (java.io.IOException ex) {
            return null;
        }
    }
    private boolean parseXMLforLatLon(final String xml, persistence.Location loc) {
        try {
            Document xmldoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                    new InputSource(new StringReader(xml)));
            NodeList nl = xmldoc.getElementsByTagName("coordinates");
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                NodeList nlSub = node.getChildNodes();
                for (int j = 0; j < nlSub.getLength(); j++) {
                    Node subNode = nlSub.item(j);
                    if (subNode.getNodeType() == Node.TEXT_NODE) {
                        // lon/lat
                        String[] lonlat = subNode.getNodeValue().split(",");
                        loc.setLatitude(lonlat[1]);
                        loc.setLongitude(lonlat[0]);
                        return true;
                    }
                }
            }
        } catch (SAXException se) {
        } catch (IOException ioe) {
        } catch (ParserConfigurationException pce) {
        }
        return false;
    }
    private boolean parseXMLforAddress(final String xml, persistence.Location loc) {
        try {
            Document xmldoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                    new InputSource(new StringReader(xml)));
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            XPathExpression expr;
            try {
                expr = xpath.compile("/kml/Response/Placemark/AddressDetails/Country/AdministrativeArea");
                NodeSet nodeSetResult = (NodeSet) expr.evaluate(xmldoc, XPathConstants.NODESET);
            } catch (XPathExpressionException ex) {
                Logger.getLogger(geocodeSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            // addresses returned best first.
            NodeList nl = xmldoc.getElementsByTagName("Placemark");
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                // ThoroughfareName == street
                // PostalCodeNumber == zip
                // LocalityName == city
                // AdministrativeAreaName == state
                NodeList nlSub = node.getChildNodes();
                for (int j = 0; j < nlSub.getLength(); j++) {
                    Node subNode = nlSub.item(j);
                    if (subNode.getNodeType() == Node.TEXT_NODE) {
                        // lon/lat
                        String[] lonlat = subNode.getNodeValue().split(",");
                        loc.setLatitude(lonlat[0]);
                        loc.setLongitude(lonlat[1]);
                        return true;
                    }
                }
            }
        } catch (SAXException se) {
        } catch (IOException ioe) {
        } catch (ParserConfigurationException pce) {
        }
        return false;
    }
    // TODO check for status  <Status><Code>602</Code> -> unknown address

    /*
     * For future reference:
import java.lang.Math;
import java.lang.Double;

public int calcDistance(double latA, double longA, double latB, double longB)
{
  double theDistance = (Math.sin(Math.toRadians(latA)) *
                        Math.sin(Math.toRadians(latB)) +
                        Math.cos(Math.toRadians(latA)) *
                        Math.cos(Math.toRadians(latB)) *
                        Math.cos(Math.toRadians(longA - longB)));

  return = (Math.toDegrees(Math.acos(theDistance))) * 69.09;
}
     *
     * OR IN MYSql:
     * (From http://dev.mysql.com/doc/refman/5.0/en/functions-that-test-spatial-relationships-between-geometries.html)
     * Distance(g1,g2)

Returns as a double-precision number the shortest distance between any two points in the two geometries. 
     *
     */
    // TODO: if we just have ZIP Code, could resolve city, state
}
/*
 * <kml>
 *  <Response>
 *    <name>80305</name>
 *    <Status>
 *      <code>200</code>
 *      <request>geocode</request>
 *    </Status>
 *    <Placemark id="p1">
 *      <address>Boulder, CO 80305, USA</address>
 *      <AddressDetails Accuracy="5">
 *        <Country>
 *          <CountryNameCode>US</CountryNameCode>
 *          <CountryName>USA</CountryName>
 *          <AdministrativeArea>
 *            <AdministrativeAreaName>CO</AdministrativeAreaName>
 *            <Locality>
 *              <LocalityName>Boulder</LocalityName>
 *              <PostalCode>
 *                <PostalCodeNumber>80305</PostalCodeNumber>
 *              </PostalCode>
 *            </Locality>
 *          </AdministrativeArea>
 *        </Country>
 *      </AddressDetails>
 *      <ExtendedData>
 *        <LatLonBox north="40.0004470" south="39.9506840" east="-105.2211210" west="-105.2860380"/>
 *      </ExtendedData>
 *      <Point>
 *        <coordinates>-105.2487370,39.9799992,0</coordinates>
 *      </Point>
 *    </Placemark>
 *  </Response>
 * </kml>
 */