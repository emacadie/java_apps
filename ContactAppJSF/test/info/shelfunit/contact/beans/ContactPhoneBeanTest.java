/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.shelfunit.contact.beans;

import info.shelfunit.contact.beans.ContactPhoneBean;
import java.util.Date;
import java.util.UUID;
import info.shelfunit.contact.hibernate.ContactPhone;
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
public class ContactPhoneBeanTest {
    
    private String className = "UserBeanTest.";
    private String methodName = "blue";
    
    public ContactPhoneBeanTest() {
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
    public void testAddContactAddress(  ) {
        methodName = className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName();
        System.out.println( "\nStarting " + methodName );
        ContactPhoneBean cA = new ContactPhoneBean();
        String result = "";
        cA.setPhoneNumber( "312-555-1212" );
        
        cA.setIsPrimary( "y" );
        
        result = cA.addContactPhone( 5 );
        System.out.println( "result: " + result );
    } // end method testAddContactAddress
    
} // end class
