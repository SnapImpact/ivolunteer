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
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;
import org.networkforgood.xml.namespaces.voml.*;
import persistence.*;

/**
 * 
 * @author Dave Angulo
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class vomlSessionBean implements vomlSessionLocal {

    @Resource
    private UserTransaction userTransaction;

    @PersistenceContext
    private EntityManager em;

    @EJB
    private vomlSessionEngineLocal engine;

    public void loadVoml() {
        try {
            VomlData vd = new VomlData();
            JAXBContext jc = JAXBContext.newInstance(VomlData.class.getPackage().getName());
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            vd = (VomlData) unmarshaller.unmarshal(new File(
                    "/Users/dave/Documents/iVolunteer/code/ivolunteer/test_data/voml_test.xml"));
            List<VolunteerOpportunity> opps = vd.getVolunteerOpportunities().getVolunteerOpportunity();

            userTransaction.begin();

            Query sourceQuery = em.createNamedQuery("Source.findByName");
            Query orgTypeQuery = em.createNamedQuery("OrganizationType.findByName");

            Source source;
            try {
                sourceQuery.setParameter("name", "Hands on Network");
                source = (Source) sourceQuery.getSingleResult();

            } catch (NoResultException nr) {
                System.out.println("Can't find source: Hands on Network");
                return;
            }

            source.setLastKey(vd.getTimestamp().toString());

            OrganizationType orgType;
            try {
                orgTypeQuery.setParameter("name", "Non-Profit");
                orgType = (OrganizationType) orgTypeQuery.getSingleResult();

            } catch (NoResultException nr) {
                System.out.println("Can't find organization type for Non-Profit");
                return;
            }

            Integer numOpps = opps.size();
            Integer oppNum = 0;

            while ( oppNum < numOpps ) {
                engine.writeToDb(opps.subList(oppNum, oppNum + 1), orgType, source);
                oppNum++;
                System.out.println(oppNum + "/" + numOpps);
                em.flush();
                userTransaction.commit();
                userTransaction.begin();
            }

            userTransaction.commit();
            
        } catch (UnmarshalException ue) {
            System.out.println("Caught UnmarshalException");
            System.out.println(ue.toString());
        } catch (JAXBException je) {
            je.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")
}
