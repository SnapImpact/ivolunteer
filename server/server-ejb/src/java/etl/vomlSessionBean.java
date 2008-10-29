/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package etl;

import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.util.ValidationEventCollector;
import java.io.File;
import java.util.List;
import org.networkforgood.xml.namespaces.voml.*;

/**
 *
 * @author dave
 */
@Stateless
public class vomlSessionBean implements vomlSessionLocal {

    public void loadVoml() {
        try {
            VolunteerOpportunities vo = new VolunteerOpportunities();
            JAXBContext jc = JAXBContext.newInstance( VolunteerOpportunities.class.getPackage().getName() );
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            vo = (VolunteerOpportunities) unmarshaller.unmarshal(new File("/Users/dave/Documents/iVolunteer/code/ivolunteer/test_data/handsonnetwork_restricted_mucked.xml"));
            List<VolunteerOpportunity> opps = vo.getVolunteerOpportunity();

            for ( VolunteerOpportunity opp : opps )
            {
                System.out.println(opp.getTitle());
            }
        }
        catch( UnmarshalException ue ) {
            System.out.println( "Caught UnmarshalException" );
            System.out.println(ue.toString());
        }
        catch( JAXBException je ) {
            je.printStackTrace();
        }

    }

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")

    
 
}
