package info.shelfunit.contact.beans;

import info.shelfunit.contact.beans.UserBean;
import info.shelfunit.contact.hibernate.Contact;
import org.hibernate.Query;
import java.util.ArrayList;
import java.util.List;
import info.shelfunit.contact.hibernate.User;
import info.shelfunit.contact.NewHibernateUtil;
import org.hibernate.Session;
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
public class UserBeanTest {
    
    private String className = "UserBeanTest.";
    private String methodName = "blue";
    private String username;
    private String hibernateUserName;
    private String hibernatePassword;
    private String hibernateConfirmPassword;
    
    
    public UserBeanTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        UUID uuid = UUID.randomUUID();
        this.setUsername( uuid.toString() );
        UUID hibernateUuid = UUID.randomUUID();
        this.setHibernateUserName( hibernateUuid.toString() );
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
    public void testAddingUser() {
        methodName = className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName();
        System.out.println( "\nStarting " + methodName );
        UserBean userB = new UserBean();
        userB.setUserName( username.substring( 0, 20 ) );
        String hashBuff = Integer.toString( this.hashCode() );
        userB.setPassword( hashBuff );
        userB.setConfirmPassword( hashBuff );
        String result = userB.addUser();
        System.out.println( "Result: " + result );
        System.out.println( userB.getStatusMessage() );
        org.junit.Assert.assertEquals( "UserMainPage", result );
        
        // try adding user with same name
        UserBean userC = new UserBean();
        userC.setUserName( username.substring( 0, 20 ) );
        userC.setPassword( hashBuff );
        userC.setConfirmPassword( hashBuff );
        result = userC.addUser();
        System.out.println( "Result: " + result );
        System.out.println( userC.getStatusMessage() );
        org.junit.Assert.assertEquals( "UserErrorPage", result );
        
        // try again with different passwords
        UserBean userD = new UserBean();
        userD.setUserName( UUID.randomUUID().toString().substring( 0, 20 ) );
        userD.setPassword( UUID.randomUUID().toString().substring( 0, 10 ) );
        userD.setConfirmPassword( UUID.randomUUID().toString().substring( 0, 10 ) );
        result = userD.addUser();
        System.out.println( "Result: " + result );
        System.out.println( userC.getStatusMessage() );
        org.junit.Assert.assertEquals( "UserErrorPage", result );
        
    } // end method testAddingUser
    
    @Test
    public void testLogin() {
        methodName = className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName();
        System.out.println( "\nStarting " + methodName );
        
        String result = "UserMainPage";
        
            try {
                
                // @SuppressWarnings( "unchecked" ) // I hate this - perhaps it is time for Spring?

                Session session = NewHibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();

                String queryString = "from User";
                Query q = session.createQuery( queryString );
                List< User > userList = q.list();
                
                User user = userList.get( 0 );
                
                UserBean userL = new UserBean();
                userL.setUserName( user.getUserName() );
                userL.setPassword( user.getPassword() );
                userL.setConfirmPassword( user.getPassword() );
                userL.setEmailAddress( user.getEmailAddress() );
                userL.setIsAdmin( user.getIsAdmin() );
                
                result = userL.loginAction();
                System.out.println( "result: " + result );
                System.out.println( userL.getStatusMessage() );
                
                userL.setPassword( UUID.randomUUID().toString().substring( 0, 10 ) );
                result = userL.loginAction();
                System.out.println( "result: " + result );
                System.out.println( userL.getStatusMessage() );
                session.close();       
            } catch ( NullPointerException npEx ) {
                
                npEx.printStackTrace( System.out );
            } //  
        
                ////////////////////////////////////////////////
    } // end method testLogin
    
    
    @Test
    public void testGetListOfContacts() {
        methodName = className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName();
        System.out.println( "\nStarting " + methodName );
        
        String result = "UserMainPage";
        
            try {
                
                // @SuppressWarnings( "unchecked" ) // I hate this - perhaps it is time for Spring?

                UserBean userL = new UserBean();
                
                result = userL.getListOfContacts();
                System.out.println( "result: " + result );
                System.out.println( userL.getStatusMessage() );
                List< Contact > contactList = userL.getContactList();
                
                for ( int i = 0; i < contactList.size(); i++ ) {
                    Contact nextC = ( Contact ) contactList.get( i );
                    System.out.println( "Contact: " + nextC.getFirstName() + " " +
                        nextC.getLastName()
                    );
                } // int i = 0; i < getContactList().size(); i++ )
                      
            } catch ( NullPointerException npEx ) {
                
                npEx.printStackTrace( System.out );
            } // ///////////////////////
    } // end method testGetListOfContacts

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHibernateUserName() {
        return hibernateUserName;
    }

    public void setHibernateUserName(String hibernateUserName) {
        this.hibernateUserName = hibernateUserName;
    }

    public String getHibernatePassword() {
        return hibernatePassword;
    }

    public void setHibernatePassword(String hibernatePassword) {
        this.hibernatePassword = hibernatePassword;
    }
    
} // end class UserBeanTest
