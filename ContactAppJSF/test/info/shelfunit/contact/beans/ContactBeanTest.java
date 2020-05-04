/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.shelfunit.contact.beans;

import info.shelfunit.contact.beans.ContactBean;
import java.util.List;
import info.shelfunit.contact.hibernate.Contact;
import info.shelfunit.contact.NewHibernateUtil;
import org.hibernate.Session;
import java.util.Date;
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
public class ContactBeanTest {
    
    private String className = "UserBeanTest.";
    private String methodName = "blue";
    private String username;
    private String hibernateUserName;
    private String hibernatePassword;
    private String hibernateConfirmPassword;
    
    public ContactBeanTest() {
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
    
    @Test
    public void testAddingContact() {
        methodName = className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName();
        System.out.println( "\nStarting " + methodName );
        
        ContactBean contactB = new ContactBean();
        
        contactB.setFirstName( UUID.randomUUID().toString().substring( 0, 20 ) );
        contactB.setLastName( UUID.randomUUID().toString().substring( 0, 20 ) );
        contactB.setDateStamp( new Date() );
        
        String result = contactB.addContact();
        System.out.println( "Date: " + contactB.getDateStamp() );
        System.out.println( "Result: " + result );
        System.out.println( contactB.getStatusMessage() );
        System.out.println( contactB.getStatus() );
        
        org.junit.Assert.assertEquals( "StatusPage", result );
 
    } // end method testAddingUser
    
    @Test
    public void testTimeStamps() {
        methodName = className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName();
        System.out.println( "\nStarting " + methodName );
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        
        session.beginTransaction();

        @SuppressWarnings( "unchecked" )
        List< Contact > tempList = session.createQuery( "from Contact order by lastName, firstName" ).list();
        Contact contactA = tempList.get( 0 );
        System.out.println( "Date for A: " + contactA.getDatestamp() );
        contactA.setFirstName( UUID.randomUUID().toString().substring( 0, 20 ) );
        contactA.setLastName( UUID.randomUUID().toString().substring( 0, 20 ) );
        contactA.setDatestamp( new Date() );
        
        session.update( contactA );
        session.getTransaction().commit();
        System.out.println( "Date for A: " + contactA.getDatestamp() );
        
        
        Contact contactB = tempList.get( 0 );
        contactB.setFirstName( UUID.randomUUID().toString().substring( 0, 20 ) );
        contactB.setLastName( UUID.randomUUID().toString().substring( 0, 20 ) );
        contactB.setDatestamp( new Date() );
        System.out.println( "Date for B: " + contactB.getDatestamp() );
        session.update( contactB );
        
    } // end method testTimeStamps
    
    @Test
    public void testListContact() {
        methodName = className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName();
        System.out.println( "\nStarting " + methodName );
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        
        session.beginTransaction();

        @SuppressWarnings( "unchecked" )
        List< Contact > tempList = session.createQuery( "from Contact order by lastName, firstName" ).list();
        Contact contactA = tempList.get( 0 );
        ContactBean cB = new ContactBean();
        cB.setContactCounter( contactA.getContactCounter() );
        String result = cB.listContact( contactA.getContactCounter() );
        
        System.out.println( "Result: " + result );
        
        
        
    } // end method testListContact

} // end class
