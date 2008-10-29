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
public class vomlSessionBeanTest {

    public vomlSessionBeanTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of loadVoml method, of class vomlSessionBean.
     */
    @Test
    public void testLoadVoml() {
        System.out.println("loadVoml");
        vomlSessionBean instance = new vomlSessionBean();
        instance.loadVoml();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}