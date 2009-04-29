/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package etl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dave
 */
public class geocodeSessionBeanTest {
    geocodeSessionBean instance;

    public geocodeSessionBeanTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        instance = new geocodeSessionBean();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of encodeAddress method, of class geocodeSessionBean.
     */
    @Test
    public void testEncodeGoodAddress() {
        persistence.Location loc = new persistence.Location();
        loc.setStreet("2060 Broadway");
        loc.setState("CO");
        loc.setCity("Boulder");
        instance.encodeAddress(loc);
        assertNotNull(loc.getLatitude());
        assertNotNull(loc.getLongitude());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testEncodeZipCode() {
        persistence.Location loc = new persistence.Location();
        loc.setZip("80305");
        instance.encodeAddress(loc);
        assertNotNull(loc.getLatitude());
        assertNotNull(loc.getLongitude());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testOddResult() {
        persistence.Location loc = new persistence.Location();
        loc.setCity("Saint Paul");
        loc.setState("MN");
        loc.setZip("55114");
        instance.encodeAddress(loc);
        assertNotNull(loc.getLatitude());
        assertNotNull(loc.getLongitude());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testEncodeBadAddr() {
        persistence.Location loc = new persistence.Location();
        loc.setStreet("2060 Longway");
        loc.setState("CO");
        loc.setCity("Rock");
        instance.encodeAddress(loc);
        // TODO this test fails because the locator is too forgiving :(
        assertNull(loc.getLatitude());
        assertNull(loc.getLongitude());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}