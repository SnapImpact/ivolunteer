/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package etl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.NoResultException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.util.ValidationEventCollector;
import java.io.File;
import java.util.List;
import java.util.Date;
import java.util.UUID;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.networkforgood.xml.namespaces.voml.*;
import persistence.*;

/**
 *
 * @author dave
 */
@Stateless
public class vomlSessionBean implements vomlSessionLocal {
    @PersistenceContext
    private EntityManager em;

    public void loadVoml() {
        try {
            VolunteerOpportunities vo = new VolunteerOpportunities();
            JAXBContext jc = JAXBContext.newInstance( VolunteerOpportunities.class.getPackage().getName() );
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            vo = (VolunteerOpportunities) unmarshaller.unmarshal(new File("/Users/dave/Documents/iVolunteer/code/ivolunteer/test_data/handsonnetwork_restricted_mucked.xml"));
            List<VolunteerOpportunity> opps = vo.getVolunteerOpportunity();

            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Query organizationQuery = em.createNamedQuery("Organizations.findByName");
            Query eventQuery = em.createNamedQuery("Events.findByTitle");
            for ( VolunteerOpportunity opp : opps )
            {
                System.out.println(opp.getTitle());

                SponsoringOrganization sponsor = opp.getSponsoringOrganizations().getSponsoringOrganization().iterator().next();
                organizationQuery.setParameter("name", sponsor.getName());

                Organizations org;
                boolean newOrg = false;
                try {
                    org = (Organizations) organizationQuery.getSingleResult();
                } catch (NoResultException nr) {
                    newOrg = true;
                    org = new Organizations();
                    org.setId(UUID.randomUUID().toString());
                    org.setName(sponsor.getName());
                }

                String sponsorAddress = sponsor.getAddress1() + " " + sponsor.getAddress2();
                if (! org.getStreet().equalsIgnoreCase(sponsorAddress)) {
                    org.setStreet(sponsorAddress);
                }

                if (! org.getCity().equalsIgnoreCase(sponsor.getCity())) {
                    org.setCity(sponsor.getCity());
                }

                if (! org.getState().equalsIgnoreCase(sponsor.getStateOrProvince())) {
                    org.setState(sponsor.getStateOrProvince());
                }

                if (! org.getZip().equalsIgnoreCase(sponsor.getZipOrPostalCode())) {
                    org.setZip(sponsor.getZipOrPostalCode());
                }

                if (! org.getDescription().equalsIgnoreCase(sponsor.getDescription())) {
                    org.setDescription(sponsor.getDescription());
                }

                if (org.getEmail().compareTo(sponsor.getEmail()) != 0) {
                    org.setEmail(sponsor.getEmail());
                }

                if (org.getUrl().compareTo(sponsor.getURL()) != 0) {
                    org.setUrl(sponsor.getURL());
                }

                String sponsorPhone = sponsor.getPhone();
                if (sponsor.getExtension() != null) {
                    sponsorPhone = sponsorPhone + " ext " + sponsor.getExtension();
                }

                if (! org.getPhone().equalsIgnoreCase(sponsorPhone)) {
                    org.setPhone(sponsorPhone);
                }


                Events ev = new Events();
                ev.setTitle(opp.getTitle());
                ev.setDescription(opp.getDescription());
                ev.setStreet(opp.getLocations().getLocation().getAddress1() + " " + opp.getLocations().getLocation().getAddress2());
                ev.setCity(opp.getLocations().getLocation().getCity());
                ev.setState(opp.getLocations().getLocation().getStateOrProvince());
                ev.setZip(opp.getLocations().getLocation().getZipOrPostalCode());
                OpportunityDate oppDate = opp.getOpportunityDates().getOpportunityDate().iterator().next();

                try {
                    Date startDate = dateFormatter.parse(oppDate.getStartDate() + " " + oppDate.getStartTime());
                    ev.setTimestamp(startDate);
                    if ( oppDate.getDuration() != null ) {
                        String durUnits = oppDate.getDuration().getDurationUnit();


                    }
                    else
                    {
                        Date endDate = dateFormatter.parse(oppDate.getEndDate() + " " + oppDate.getEndTime());
                        long dur = (endDate.getTime() - startDate.getTime())/1000;
                        ev.setDuration((short) dur);
                    }
                }
                catch (ParseException pe) {
                    System.out.println(pe.toString());
                }

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

    public void persist(Object object) {
        em.persist(object);
    }

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")

    
 
}
