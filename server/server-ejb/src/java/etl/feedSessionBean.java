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

import java.net.*;
import java.io.*;
import javax.ejb.Stateless;
//
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import persistence.Api;
//import javax.ejb.Stateless;

/**
 * @author flipper
 */
@Stateless
public class feedSessionBean implements feedSessionLocal {

    @PersistenceContext
    private EntityManager em;
    static String EOL = "\n";
    static String HANDSON_URL = "http://demo.handsonnetwork.org/voml/";
    static String VOML_START_TAG = "<VomlData xmlns=\"http://www.networkforgood.org/xml/namespaces/voml/\">";
    static String VOML_END_TAG = "</VomlData>";
    // Will be seomthing like this
    // "<?xml version="1.0" encoding="utf-8" ?>";
    static String XML_VERSION_END = "?>";

    // Get the two urls content and return as tmp files location somewhere on the server 
    public HashMap getFiles() throws Exception {
        HashMap<String,String> results = new HashMap<String,String>();


        Query apiQuery = em.createNamedQuery("Api.findAll");
        ArrayList<Api> apiList = new ArrayList(apiQuery.getResultList());

        for ( Api api : apiList) {
            // http://demo.handsonnetwork.org/voml/?feed=handsonnetwork.org
            // http://demo.handsonnetwork.org/voml/?feed=1-800-volunteer.org
            // Get location and content
            String url = api.getSourceId().getApiUrl() + api.getUrlPath();
            if ( api.getLastKey().matches("") ) {
                url += "&timestamp=" + api.getLastKey();
            }
            StringBuilder content = getHeaderFromURL(url);
            // Wrap xml with tags
            feedSessionBean.wrapXml(content, VOML_START_TAG, VOML_END_TAG);
            // Save to temp file
            File tempfile = File.createTempFile(api.getTempFileBase(), ".xml");
            results.put(api.getId(), tempfile.getCanonicalPath());
            feedSessionBean.writeXmlToFile(content.toString(), results.get(api.getId()));
        }

        // Return the tmp file locations to caller
        return results;
    }

    // Reads content from the passed URL
    private static StringBuilder getURLContent(String psURL) throws MalformedURLException, IOException {
        StringBuilder keep = new StringBuilder();
        OutputStreamWriter wr = null;
        BufferedReader rd = null;

        try {
            // Construct data
            String data = "";

            // Send data
            URL url = new URL(psURL);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                keep.append(line);
                // out.println( line );
            }
        } finally {
            try {
                if (wr != null) {
                    wr.close();
                }
                if (rd != null) {
                    rd.close();
                }
            } catch (Exception dontkare) {
            }
        }

        return keep;
    }

    // Get the text we are after from the string and token to the end of line
    public static String getTextValue(String psText, String psLookFor) throws Exception {
        int iWhere = psText.indexOf(psLookFor);
        int iEOL = 0;
        String sRet = "";

        try {
            if (iWhere > -1) {
                iEOL = psText.indexOf(EOL, iWhere);
                sRet = psText.substring(iWhere + psLookFor.length(), iEOL).trim();
            } else {
                throw new Exception("");
            }
        } catch (Exception ex) {
            throw new Exception("The text='" + psLookFor + "' was not found. Error=" + ex.getMessage());
        }

        return sRet;
    }

    // Get headers from server on the given URL
    public static StringBuilder getHeaderFromURL(String psURL) throws MalformedURLException, IOException {
        StringBuilder sbRet = new StringBuilder();
        // Create a URLConnection object for a URL
        URL url = new URL(psURL);
        URLConnection conn = url.openConnection();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                conn.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            sbRet.append(inputLine);
        }
        in.close();

        return sbRet;
    }

    // Wraps the content with the open and close tags
    public static void wrapXml(StringBuilder psbContent, String psOpenTag, String psCloseTag) {
        // Find the first line
        int iWhere = psbContent.indexOf(XML_VERSION_END) + XML_VERSION_END.length();
        // insert the open tag
        psbContent.insert(iWhere, psOpenTag + EOL);
        // insert the close tag
        psbContent.append(psCloseTag + EOL);
    }

    // Writes string content to file 
    public static void writeXmlToFile(String psText, String psFileName) throws IOException {
        Writer writer = null;

        try {
            File file = new File(psFileName);
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(psText);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception dontkare) {
            }
        }
    }
}  // EOC

