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

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.NoResultException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;
import java.util.HashSet;
import java.util.Date;
import java.util.UUID;
import java.util.Iterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.networkforgood.xml.namespaces.voml.*;
import persistence.*;

/**
 * 
 * @author Dave Angulo
 */
@Stateless
public class vomlSessionBean implements vomlSessionLocal {
	@PersistenceContext
	private EntityManager	em;

	public void loadVoml() {
		try {
			VolunteerOpportunities vo = new VolunteerOpportunities();
			JAXBContext jc = JAXBContext.newInstance(VolunteerOpportunities.class.getPackage()
					.getName());
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			vo = (VolunteerOpportunities) unmarshaller
					.unmarshal(new File(
							"/Users/dave/Documents/iVolunteer/code/ivolunteer/test_data/handsonnetwork.xml"));
			List<VolunteerOpportunity> opps = vo.getVolunteerOpportunity();

			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Query sourceQuery = em.createNamedQuery("Source.findByName");
			Query orgTypeQuery = em.createNamedQuery("OrganizationType.findByName");
			Query organizationQuery = em.createNamedQuery("Organization.findByName");
			Query eventQuery = em.createNamedQuery("Event.findByTitle");
			Query locationQuery = em.createNamedQuery("Location.findByStreetZip");
			Query timestampQuery = em.createNamedQuery("Timestamp.findByTimestamp");
			Query categoryQuery = em.createNamedQuery("SourceInterestMap.findBySourceKey");

			Source source;
			try {
				sourceQuery.setParameter("name", "Hands on Network");
				source = (Source) sourceQuery.getSingleResult();

			} catch (NoResultException nr) {
				System.out.println("Can't find source: Hands on Network");
				return;
			}

			OrganizationType orgType;
			try {
				orgTypeQuery.setParameter("name", "Non-Profit");
				orgType = (OrganizationType) orgTypeQuery.getSingleResult();

			} catch (NoResultException nr) {
				System.out.println("Can't find organization type for Non-Profit");
				return;
			}

			for (VolunteerOpportunity opp : opps) {

                if ( opp.getTitle() == null ) {
                    continue;
                }
                
                System.out.println(opp.getTitle());

				// SponsoringOrganization sponsor =
				// opp.getSponsoringOrganizations().getSponsoringOrganization().iterator().next();
				List<SponsoringOrganization> sponsors = opp.getSponsoringOrganizations()
						.getSponsoringOrganization();

				HashSet<Organization> orgs = new HashSet<Organization>();
				for (SponsoringOrganization sponsor : sponsors) {

					Organization org;
					boolean newOrg = false;
					try {
						organizationQuery.setParameter("name", sponsor.getName());
						org = (Organization) organizationQuery.getSingleResult();
					} catch (NoResultException nr) {
						newOrg = true;
						org = new Organization();
						org.setId(UUID.randomUUID().toString());
						org.setName(sponsor.getName());
						org.setOrganizationTypeId(orgType);
						em.persist(org);
					}

                    String sponsorAddress = sponsor.getAddress1();
                    if (sponsor.getAddress2() != null) {
                        sponsorAddress =
                            ((sponsorAddress==null)?"":(sponsorAddress + " "))
                            + sponsor.getAddress2();
                    }
					persistence.Location loc;
					boolean newLoc = false;
					try {
						locationQuery.setParameter("street", sponsorAddress);
						locationQuery.setParameter("zip", sponsor.getZipOrPostalCode());
						loc = (persistence.Location) locationQuery.getSingleResult();
					} catch (NoResultException nr) {
						newLoc = true;
						loc = new persistence.Location();
						loc.setId(UUID.randomUUID().toString());
						loc.setStreet(sponsorAddress);
						loc.setCity(sponsor.getCity());
						loc.setState(sponsor.getStateOrProvince());
						loc.setZip(sponsor.getZipOrPostalCode());
						em.persist(loc);
					}

					if (!org.getLocationCollection().contains(loc)) {
						org.getLocationCollection().add(loc);
					}

					org.setDescription(sponsor.getDescription());
					org.setEmail(sponsor.getEmail());
					org.setUrl(sponsor.getURL());

					String sponsorPhone = sponsor.getPhone();
					if (sponsor.getExtension() != null) {
						sponsorPhone = sponsorPhone + " ext " + sponsor.getExtension();
					}

					org.setPhone(sponsorPhone);

					em.merge(org);

					orgs.add(org);
				}

				Event ev = null;
				eventQuery.setParameter("title", opp.getTitle());
				List<Event> events = eventQuery.getResultList();
				for (Event event : events) {
					if (event.getOrganizationCollection().containsAll(orgs)) {
						ev = event;
						break;
					}
				}

				if (ev == null) {
					ev = new Event();
					ev.setId(UUID.randomUUID().toString());
					ev.setTitle(opp.getTitle());
					ev.setOrganizationCollection(orgs);
					em.persist(ev);
				} else {
					orgs.addAll(ev.getOrganizationCollection());
					ev.setOrganizationCollection(orgs);
				}

				ev.setDescription(opp.getDescription());

				List<OpportunityDate> oppDates = opp.getOpportunityDates().getOpportunityDate();

				for (OpportunityDate oppDate : oppDates) {

					try {
						Date startDate = dateFormatter.parse(oppDate.getStartDate() + " "
								+ oppDate.getStartTime());
						Timestamp ts;
						try {
							timestampQuery.setParameter("timestamp", startDate);
							ts = (Timestamp) timestampQuery.getSingleResult();
						} catch (NoResultException nr) {
							ts = new Timestamp();
							ts.setId(UUID.randomUUID().toString());
							ts.setTimestamp(startDate);
							em.persist(ts);
						}

						ev.getTimestampCollection().add(ts);

						if (oppDate.getDuration() != null) {
							String durUnits = oppDate.getDuration().getDurationUnit();

						} else {
							Date endDate = dateFormatter.parse(oppDate.getEndDate() + " "
									+ oppDate.getEndTime());
							long dur = (endDate.getTime() - startDate.getTime()) / 1000;
							ev.setDuration((short) dur);
						}
					} catch (ParseException pe) {
						System.out.println(pe.toString());
					}
				}

				List<Category> oppCategories = opp.getCategories().getCategory();

				HashSet<InterestArea> currentIAs = new HashSet<InterestArea>(ev
						.getInterestAreaCollection());
				for (Category oppCat : oppCategories) {
					categoryQuery.setParameter("source", source);
					categoryQuery.setParameter("sourceKey", oppCat.getCategoryID().toString());
					for (Iterator it = categoryQuery.getResultList().iterator(); it.hasNext();) {
						SourceInterestMap sim = (SourceInterestMap) it.next();
						currentIAs.add(sim.getInterestAreaId());
					}
				}
				ev.setInterestAreaCollection(currentIAs);

                org.networkforgood.xml.namespaces.voml.Location location = opp.getLocations().getLocation();

                String locationAddress = location.getAddress1();
                if (location.getAddress2() != null) {
                    locationAddress = 
                        ((locationAddress==null)?"":(locationAddress + " "))
                        + location.getAddress2();
                }
                
                persistence.Location loc;
                boolean newLoc = false;
                try {
                    locationQuery.setParameter("street", locationAddress);
                    locationQuery.setParameter("zip", location.getZipOrPostalCode());
                    loc = (persistence.Location) locationQuery.getSingleResult();
                } catch (NoResultException nr) {
                    newLoc = true;
                    loc = new persistence.Location();
                    loc.setId(UUID.randomUUID().toString());
                    loc.setStreet(locationAddress);
                    loc.setCity(location.getCity());
                    loc.setState(location.getStateOrProvince());
                    loc.setZip(location.getZipOrPostalCode());
                    em.persist(loc);
                }

                if (newLoc) {
                    ev.getLocationCollection().add(loc);
                }

				em.merge(ev);
				em.flush();

			}
		} catch (UnmarshalException ue) {
			System.out.println("Caught UnmarshalException");
			System.out.println(ue.toString());
		} catch (JAXBException je) {
			je.printStackTrace();
		}

	}

	public void persist(Object object) {
		em.persist(object);
	}

	// Add business logic below. (Right-click in editor and choose
	// "Insert Code > Add Business Method" or "Web Service > Add Operation")

}
