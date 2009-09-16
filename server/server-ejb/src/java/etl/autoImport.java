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
package etl;

import javax.ejb.Stateless;
import java.util.logging.Level;
import java.util.logging.Logger;
//
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import javax.ejb.EJB;

/**
 *
 * @author Orn Kristjansson
 */
@Stateless
public class autoImport
{

    @PersistenceContext
    private static EntityManager entManager;

    @EJB
    private locationencoderSessionLocal locationEncoder;

    @EJB
    private vomlSessionLocal voml;

    @EJB
    private feedSessionBean feed;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")
    public void runMe()
    {
            try
            {
                // Get the files to process
                String[] files = feed.getFiles();


                // Process the files
                for( String fileName : files)
                {
                    // Load the data from file
                    voml.loadVoml( fileName );
                }


                // Geocode the locations
                locationEncoder.updateLocationTableLatLon();


                // Create Geometry types out of the points
               // this.usrTrans.begin();
                //this.entManager.joinTransaction();
                //
                Query theQuery = entManager.createNamedQuery("Location.updateGeom");
                theQuery.executeUpdate();
                //
                //this.usrTrans.commit();



                // Update the location index
                //this.usrTrans.begin();
                //this.entManager.joinTransaction();
                //
                theQuery = entManager.createNamedQuery("Location.updateIndex");
                theQuery.executeUpdate();
                //
                //this.usrTrans.commit();


            }
            catch( Exception ex )
            {
                System.out.println("Error=" + ex ) ;
                Logger.getLogger(autoImport.class.getName()).log(Level.SEVERE, ex.toString(), ex);
            }
    }


 }  // EOC
