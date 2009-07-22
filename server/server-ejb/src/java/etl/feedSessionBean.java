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

import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.ejb.Stateless;

/**
 * @author flipper
 */
@Stateless
public class feedSessionBean 
{
    static String EOL               = "\n";
    static String HANDSON_URL       = "http://demo.handsonnetwork.org/voml/";
    static String VOML_START_TAG    = "<VomlData xmlns=\"http://www.networkforgood.org/xml/namespaces/voml/\">";
    static String VOML_END_TAG      = "</VomlData>";
    
    
    // Get the two urls content and return as tmp files location somewhere on the server 
    public String[] getFiles() throws IOException
    {
        // Temp file names holder 
        String[] sRetTempFiles = {"",""};
        
        
        // http://demo.handsonnetwork.org/voml/?feed=handsonnetwork.org
        // Get location and content 
        String header = getHeaderFromURL( HANDSON_URL + "?feed=handsonnetwork.org" );
        String location = getTextValue( header, "Location:" );
        StringBuilder content  = getURLContent( HANDSON_URL + location );
        // Wrap xml with tags
        feedSessionBean.wrapXml( content , VOML_START_TAG, VOML_END_TAG );
        // Save to temp file 
        File tempfile = File.createTempFile( "handson", ".xml");
        sRetTempFiles[0] = tempfile.getCanonicalPath();
        feedSessionBean.writeXmlToFile( content.toString(), sRetTempFiles[0] );
        
        
        
        // http://demo.handsonnetwork.org/voml/?feed=1-800-volunteer.org
        // Get location and content 
        header = getHeaderFromURL( HANDSON_URL + "?feed=1-800-volunteer.org" );
        location = getTextValue( header, "Location:" );
        content  = getURLContent( HANDSON_URL + location );
        // Wrap xml with tags
        feedSessionBean.wrapXml( content , VOML_START_TAG, VOML_END_TAG );
        // Save to temp file 
        tempfile = File.createTempFile( "800vol", ".xml");
        sRetTempFiles[1] = tempfile.getCanonicalPath();
        feedSessionBean.writeXmlToFile( content.toString(), sRetTempFiles[1] );

    
        // Return the tmp file locations to caller
        return sRetTempFiles;
    }

    
    // Reads content from the passed URL
    private static StringBuilder getURLContent( String psURL ) throws MalformedURLException, IOException
    {
        StringBuilder keep = new StringBuilder();
        OutputStreamWriter wr = null;
        BufferedReader rd = null;
                    
        try 
        {
            // Construct data
            String data = "";

            // Send data
            URL url = new URL( psURL );
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while ((line = rd.readLine()) != null) 
            {
                keep.append(  line );
               // out.println( line ); 
            }
        } 
        finally
        {
            try
            {
              if( wr != null ) wr.close();
              if( rd != null ) rd.close();
            }
            catch( Exception dontkare){}
        }
          
        return keep;
    }

    // Get the text we are after from the string and token to the end of line
    public static String getTextValue( String psText, String psLookFor )
    {
        int iWhere = psText.indexOf( psLookFor );
        int iEOL = 0;
        String sRet = "";
        
        try
        {
            iEOL = psText.indexOf( EOL, iWhere )  ;
            sRet = psText.substring( iWhere + psLookFor.length(), iEOL ).trim();
        }
        catch( Exception ex )
        {
            Logger.getLogger(feedSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sRet;
    }
        
    // Get headers from server on the given URL
    public static String getHeaderFromURL( String psURL ) throws MalformedURLException, IOException
    {
        StringBuilder sbRet = new StringBuilder();
        // Create a URLConnection object for a URL
        URL url = new URL( psURL );
        URLConnection conn = url.openConnection();

        // List all the response headers from the server.
        // Note: The first call to getHeaderFieldKey() will implicit send
        // the HTTP request to the server.
        for (int i=0; ; i++) 
        {
            String headerName = conn.getHeaderFieldKey(i);
            String headerValue = conn.getHeaderField(i);

            if (headerName == null && headerValue == null) 
            {
                // No more headers
                break;
            }

            // The header value contains the server's HTTP version
            if (headerName == null) 
                sbRet.append( "N/A" + ": " + headerValue + "\n");
            else
                sbRet.append( headerName + ": " + headerValue + "\n" );
        }

        
        return sbRet.toString();
    }
    
    
    // Wraps the content with the open and close tags
    public static void wrapXml( StringBuilder psbContent, String psOpenTag, String psCloseTag )
    {
        // Find the first line
        int iWhere = psbContent.indexOf( EOL );
        // insert the open tag
        psbContent.insert( iWhere + 1, psOpenTag + EOL );
        // insert the close tag
        psbContent.append( psCloseTag  + EOL );
    }
    
    // Writes string content to file 
    public static void writeXmlToFile( String psText, String psFileName ) throws IOException 
    {
        Writer writer = null;
 
        try
        {
            File file = new File( psFileName );
            writer = new BufferedWriter(new FileWriter(file));
            writer.write( psText );
        } 
        finally
        {
            try
            {
                if (writer != null)
                    writer.close();
            } 
            catch(Exception dontkare){}
        }
    }
    
    
}  // EOC
