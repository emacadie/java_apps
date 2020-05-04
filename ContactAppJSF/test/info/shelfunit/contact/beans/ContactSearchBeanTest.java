/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.shelfunit.contact.beans;

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
public class ContactSearchBeanTest {
    
    private String className = "ContactSearchBeanTest.";
    private String methodName = "blue";
    private String username;
    private String hibernateUserName;
    private String hibernatePassword;
    private String hibernateConfirmPassword;
    
    public ContactSearchBeanTest() {
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
    public void testSearchingByPhoneNumber() {
        methodName = className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName();
        System.out.println( "\nStarting " + methodName );
        
        ContactSearchBean csb = new ContactSearchBean();
        csb.setPhoneNumber( "555" );
        String result = csb.searchByPhoneNumber(  );
        System.out.println( "Size of the list: " + csb.getContactSearchBeanList().size() );
        for ( ContactSearchBean csBean : csb.getContactSearchBeanList() ) {
            System.out.println( "---------------------------" );
            System.out.print( csBean.getFirstName() + " " + csBean.getLastName() + " who is " + csBean.getContactCounter() + ", " );
            System.out.println( csBean.getPhoneNumber() + " which is phone number " + csBean.getPhoneCounter() );
        }
       
        System.out.println( "Result: " + result );
        
        org.junit.Assert.assertEquals( "SearchResultPhoneNumber", result );
        
    } // end method testSearchingByPhoneNumber
    
    @Test
    public void testSearchingByEmail() {
        methodName = className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName();
        System.out.println( "\nStarting " + methodName );
        
        ContactSearchBean csb = new ContactSearchBean();
        
        String result = csb.searchByEmailAddress( "m");
        System.out.println( "Size of the list: " + csb.getContactSearchBeanList().size() );
        for ( ContactSearchBean csBean : csb.getContactSearchBeanList() ) {
            System.out.println( "---------------------------" );
            System.out.print( csBean.getFirstName() + " " + csBean.getLastName() + " who is " + csBean.getContactCounter() + ", " );
            System.out.println( csBean.getEmailAddress() + " which is address number " + csBean.getEmailCounter() );
        }
       
        
        System.out.println( "Result: " + result );
        
        org.junit.Assert.assertEquals( "ContactInfo", result );
        
    } // end method testSearchingByEmail
    
} // end class ContactSearchBeanTest
