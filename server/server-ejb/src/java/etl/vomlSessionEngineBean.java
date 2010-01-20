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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.*;
import org.networkforgood.xml.namespaces.voml.*;
import persistence.*;

/**
 *
 * @author Dave Angulo <daveangulo@actionfeed.org>
 */
@Stateless
public class vomlSessionEngineBean implements vomlSessionEngineLocal {

    @PersistenceContext
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")
    public void writeToDb(List<VolunteerOpportunity> opps, OrganizationType orgType, Source source) {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatter.setLenient(false);

        Query organizationQuery = em.createNamedQuery("Organization.findByName");
        Query eventQuery = em.createNamedQuery("Event.findByTitle");
        Query locationFullQuery = em.createNamedQuery("Location.findByStreetZip");
        Query locationZipNullQuery = em.createNamedQuery("Location.findByZipNull");
        Query locationNullQuery = em.createNamedQuery("Location.findByNull");
        Query timestampQuery = em.createNamedQuery("Timestamp.findByTimestamp");
        Query categoryQuery = em.createNamedQuery("SourceInterestMap.findBySourceKey");

        for (VolunteerOpportunity opp : opps) {

            if (opp.getTitle() == null) {
                continue;
            }

            // SponsoringOrganization sponsor =
            // opp.getSponsoringOrganizations().getSponsoringOrganization().iterator().next();
            List<SponsoringOrganization> sponsors = opp.getSponsoringOrganizations().getSponsoringOrganization();

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
                            ((sponsorAddress == null) ? "" : (sponsorAddress + " ")) + sponsor.getAddress2();
                }

                persistence.Location loc = null;
                Boolean newLoc = false;
                try {
                    if (sponsorAddress == null && sponsor.getCity() == null && sponsor.getStateOrProvince() == null && sponsor.getZipOrPostalCode() == null) {
                        loc = (persistence.Location) locationNullQuery.getSingleResult();
                    } else if (sponsorAddress != null && sponsor.getZipOrPostalCode() != null) {
                        locationFullQuery.setParameter("street", sponsorAddress);
                        locationFullQuery.setParameter("zip", sponsor.getZipOrPostalCode());
                        loc = (persistence.Location) locationFullQuery.getSingleResult();
                    } else if (sponsor.getZipOrPostalCode() != null) {
                        locationZipNullQuery.setParameter("zip", sponsor.getZipOrPostalCode());
                        loc = (persistence.Location) locationZipNullQuery.getSingleResult();
                    } else {
                        newLoc = true;
                    }
                } catch (NoResultException nr) {
                    newLoc = true;
                }

                if (newLoc) {
                    loc = new persistence.Location();
                    loc.setId(UUID.randomUUID().toString());
                    loc.setStreet(sponsorAddress);
                    loc.setCity(sponsor.getCity());
                    loc.setState(sponsor.getStateOrProvince());
                    loc.setZip(sponsor.getZipOrPostalCode());
                    loc.setCountry(sponsor.getCountry());
                    em.persist(loc);
                }

                if (!org.getLocationCollection().contains(loc)) {
                    org.getLocationCollection().add(loc);
                }

                if (sponsor.getDescription() != null) {
                    org.setDescription(sponsor.getDescription().replaceAll("\\<.*?>", ""));
                } else {
                    org.setDescription(sponsor.getDescription());
                }

                org.setEmail(sponsor.getEmail());
                org.setUrl(sponsor.getURL());

                String sponsorPhone = sponsor.getPhone();
                if (sponsor.getExtension() != null) {
                    sponsorPhone = sponsorPhone + " ext " + sponsor.getExtension();
                }

                org.setPhone(sponsorPhone);

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

            if (opp.getDescription() != null) {
                ev.setDescription(opp.getDescription().replaceAll("\\<.*?>", ""));
            }

            List<org.networkforgood.xml.namespaces.voml.Location> locations = opp.getLocations().getLocation();

            if (locations == null) {
                if (ev.getOrganizationCollection() != null) {
                    Collection<Organization> evOrgs = ev.getOrganizationCollection();
                    HashSet<persistence.Location> evLocations = null;
                    for (Organization evOrg : evOrgs) {
                        evLocations.addAll(evOrg.getLocationCollection());
                    }
                    ev.setLocationCollection(evLocations);
                } else {
                    continue;
                }
            }
            
            for (org.networkforgood.xml.namespaces.voml.Location location : locations) {

                String locationAddress = location.getAddress1();
                if (location.getAddress2() != null) {
                    locationAddress =
                            ((locationAddress == null) ? "" : (locationAddress + " ")) + location.getAddress2();
                }

                persistence.Location loc = null;
                Boolean newLoc = false;
                try {
                    if (locationAddress == null && location.getCity() == null && location.getStateOrProvince() == null && location.getZipOrPostalCode() == null) {
                        loc = (persistence.Location) locationNullQuery.getSingleResult();
                    } else if (locationAddress != null && location.getZipOrPostalCode() != null) {
                        locationFullQuery.setParameter("street", locationAddress);
                        locationFullQuery.setParameter("zip", location.getZipOrPostalCode());
                        loc = (persistence.Location) locationFullQuery.getSingleResult();
                    } else if (location.getZipOrPostalCode() != null) {
                        locationZipNullQuery.setParameter("zip", location.getZipOrPostalCode());
                        loc = (persistence.Location) locationZipNullQuery.getSingleResult();
                    } else {
                        newLoc = true;
                    }
                } catch (NoResultException nr) {
                    newLoc = true;
                }

                if (newLoc) {
                    loc = new persistence.Location();
                    loc.setId(UUID.randomUUID().toString());
                    loc.setStreet(locationAddress);
                    loc.setCity(location.getCity());
                    loc.setState(location.getStateOrProvince());
                    loc.setZip(location.getZipOrPostalCode());
                    loc.setCountry(location.getCounty());
                    em.persist(loc);
                }

                if (!ev.getLocationCollection().contains(loc)) {
                    ev.getLocationCollection().add(loc);
                }
            }

            List<OpportunityDate> oppDates = opp.getOpportunityDates().getOpportunityDate();

            for (OpportunityDate oppDate : oppDates) {

                try {
                    String startTime = oppDate.getStartTime() == null ? "00:00:00" : oppDate.getStartTime();
                    Date startDate = dateFormatter.parse(oppDate.getStartDate() + " " + startTime);
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

                    if ( !ev.getTimestampCollection().contains(ts))
                    {
                        ev.getTimestampCollection().add(ts);
                    }
                    
                    if (oppDate.getDuration() != null) {
                        String durUnits = oppDate.getDuration().getDurationUnit();

                    } else {
                        String endTime = oppDate.getEndTime() == null ? "23:59:59" : oppDate.getEndTime();
                        Date endDate = dateFormatter.parse(oppDate.getEndDate() + " " + endTime);
                        long dur = (endDate.getTime() - startDate.getTime()) / 1000;
                        ev.setDuration((long) dur);
                    }
                } catch (ParseException pe) {
                    System.out.println(pe.toString());
                }
            }

            List<Category> oppCategories = opp.getCategories().getCategory();

            HashSet<InterestArea> currentIAs = new HashSet<InterestArea>(ev.getInterestAreaCollection());
            for (Category oppCat : oppCategories) {
                categoryQuery.setParameter("source", source);
                categoryQuery.setParameter("sourceKey", oppCat.getCategoryID().toString());
                for (Iterator it = categoryQuery.getResultList().iterator(); it.hasNext();) {
                    SourceInterestMap sim = (SourceInterestMap) it.next();
                    currentIAs.add(sim.getInterestAreaId());
                }
            }
            ev.setInterestAreaCollection(currentIAs);

            if (opp.getDetailURL() != null) {
                ev.setUrl(opp.getDetailURL());
            } else {
                for (Organization org : ev.getOrganizationCollection()) {
                    if (org.getUrl() != null) {
                        ev.setUrl(org.getUrl());
                        break;
                    }
                }
            }

            for (Organization org : ev.getOrganizationCollection()) {
                if (org.getEmail() != null && org.getPhone() != null) {
                    ev.setEmail(org.getEmail());
                    ev.setPhone(org.getPhone());
                    break;
                } else if (org.getEmail() != null) {
                    ev.setEmail(org.getEmail());
                } else if (org.getPhone() != null) {
                    ev.setPhone(org.getPhone());
                }
            }

            ev.setSourceId(source);
            ev.setSourceKey(opp.getLocalID());
            ev.setSourceUrl(opp.getDetailURL());

            em.merge(ev);
            em.flush();

        }
    }
}
