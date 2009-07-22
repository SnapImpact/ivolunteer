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
 *
 *  Sample code from: http://groups.google.com/group/oop_programming/browse_thread/thread/f2df17c480e45369
 */
package helpers;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import persistence.Event;
import service.Base;

/**
 *
 * @author markchance
 */
public class EmailSender extends Base {
    static final String FROM_ADDRESS = "info@actionfeed.org";

//    @PersistenceContext
//    private EntityManager em;
    @Resource(name = "mail/MailSession")
    javax.mail.Session mailSession;
    final static String msgIWillAttend = "";
    public void sendAttendanceEmail(final String userEmail, String userName, final String eventId) {
//        EntityManager em = PersistenceService.getInstance().getEntityManager();
        try {
            javax.naming.InitialContext ctx = new javax.naming.InitialContext();
            mailSession = (javax.mail.Session) ctx.lookup("java:comp/env/mail/MailSession");
        } catch (NamingException ne) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }

        Event theEvent = (Event)persistenceFacade.find(eventId, Event.class);
        if (theEvent == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        String eventContactEmail = theEvent.getEmail();
        if (eventContactEmail == null) {
            Iterator iter = theEvent.getOrganizationCollection().iterator();
            while (iter.hasNext()) {
                persistence.Organization org = (persistence.Organization)iter.next();
                eventContactEmail = org.getEmail();
                if (eventContactEmail != null) break;
            }
        }
//                eventContactEmail = "mark@the-chances.net"; // to get to the next problem
//                theEvent = new Event("6d719d7f-eceb-4855-9694-d79313f61af8", "stub volunteer event", "stub description");
//            mailSession.
        MimeMessage msg = new MimeMessage(mailSession);
        try {
            // Enable testing - if userEmail is in actionfeed.org, don't sen the
            // contact email
            if (userEmail.contains("actionfeed.org")) {
                msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(userEmail));
            } else {
                msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(eventContactEmail));
                msg.setRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(userEmail));
            }
            msg.setFrom(new InternetAddress(FROM_ADDRESS));
            msg.setSubject("I want to attend this event");
            msg.setText(buildMessage(theEvent, userEmail, userName, eventContactEmail));
            msg.saveChanges();
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Transport transport = null;
        try {
             /*
              * n.b. I didn't try this bit:
              String username = (String) props.get("mail.smtps.user");
             String password = (String) props.get("mail.smtps.password");
              */
            /* This is the section I used for testing:
              String username = "mark.chance@gmail.com";
              String password = "mygmailpwd";
              transport = mailSession.getTransport("smtps");
              transport.connect(username, password);
              transport.sendMessage(msg, msg.getAllRecipients());
             */
            Transport.send(msg);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.SERVICE_UNAVAILABLE);
        } finally {
            if (transport != null) try {
                transport.close();
            } catch (MessagingException ex) {
                Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /*
         *  Message msg = new MimeMessage(mailSession);
         msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(recipient, false));
         msg.setSubject(subject);
         msg.setText(text);
         msg.saveChanges();

         //Transport.send(msg);
         transport = mailSession.getTransport("smtps");
         transport.connect(username, password);
         transport.sendMessage(msg, msg.getAllRecipients());
         */
    }

    String buildMessage(final Event event, final String userEmail, final String userName, final String contactEmail) {
        StringBuilder sb = new StringBuilder();
        // TODO include ics file for adding to calendar?
        sb.append("Dear "+((event.getContact()==null)?"organizer":event.getContact())+",\n");
        sb.append("Great news!  iVolunteer would like to connect you with someone to volunteer for " + event.getTitle()+ ". This person's contact info is:\n\n");
        if ( userName != null ) {
            sb.append("Name: " + userName + "\n");
        }
        sb.append("Email: " + userEmail + "\n\n");
        sb.append("Using the provided information, please contact this volunteer. This volunteer is awaiting further instruction from you to complete event registration!\n");
        sb.append("\n\nThanks for using iVolunteer!");
        sb.append("\n\nPlease DO NOT RESPOND to this email address; this is an auto-generated email.\n");
        if (userEmail.contains("actionfeed.org")) {
            sb.append("\nTHIS EMAIL IS FOR TESTING- not sent to "+contactEmail+"\n");
        }
        return sb.toString();
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")
 
}

/**
 * Example ics file:
 * BEGIN:VCALENDAR
PRODID:-//Google Inc//Google Calendar 70.9054//EN
VERSION:2.0
CALSCALE:GREGORIAN
METHOD:REQUEST
BEGIN:VEVENT
DTSTART:20090513T000000Z
DTEND:20090513T030000Z
DTSTAMP:20090513T015805Z
ORGANIZER;CN=mark chance:mailto:mark.chance@gmail.com
UID:4o59tmbqdqhstnm8j4uj0547i8@google.com
ATTENDEE;CUTYPE=INDIVIDUAL;ROLE=REQ-PARTICIPANT;PARTSTAT=ACCEPTED;RSVP=TRUE
 ;CN=mark chance;X-NUM-GUESTS=0:mailto:mark.chance@gmail.com
ATTENDEE;CUTYPE=INDIVIDUAL;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=
 TRUE;CN=Mark Chance;X-NUM-GUESTS=0:mailto:markc@actionfeed.org
CLASS:PRIVATE
CREATED:20090508T151147Z
DESCRIPTION:View your event at http://www.google.com/calendar/hosted/action
 feed.org/event?action=VIEW&eid=NG81OXRtYnFkcWhzdG5tOGo0dWowNTQ3aTggbWFya2NA
 YWN0aW9uZmVlZC5vcmc&tok=MjEjbWFyay5jaGFuY2VAZ21haWwuY29tMzUzOWYxZDc4MjY2MzE
 yODc0MzA0MmEyZWFhZTE0YThjNGMxOGQ0Mw&ctz=America%2FDenver&hl=en.
LAST-MODIFIED:20090513T015805Z
LOCATION:Van Heyst
SEQUENCE:0
STATUS:CONFIRMED
SUMMARY:iVolunteer
TRANSP:TRANSPARENT
END:VEVENT
END:VCALENDAR
*/
