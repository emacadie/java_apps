/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.shelfunit.contact.beans;

import info.shelfunit.contact.beans.ContactEmailBean;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ericm
 */
public class ContactEmailBeanTest {
    
    private String className = "UserBeanTest.";
    private String methodName = "blue";
    
    public ContactEmailBeanTest() {
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
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testAddContactEmail() {
        
        methodName = className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName();
        System.out.println( "\nStarting " + methodName );
        ContactEmailBean cE = new ContactEmailBean();
        String result = "";
        cE.setEmailAddress( "jon@john.com" );
        cE.setIsPrimary( "y" );
        
        result = cE.addContactEmail( 5 );
        System.out.println( "result: " + result );
        
    } // end method testAddContactEmail
    
} // end class
