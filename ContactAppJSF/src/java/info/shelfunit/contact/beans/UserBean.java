package info.shelfunit.contact.beans;

import info.shelfunit.contact.NewHibernateUtil;
import info.shelfunit.contact.hibernate.Contact;
import info.shelfunit.contact.hibernate.User;
import info.shelfunit.contact.util.AppHelper;
import info.shelfunit.contact.util.ContactLogger;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;


@ManagedBean
@SessionScoped
public class UserBean {
    
    private int loggedIn = 0;
    private String userName;
    private String password;
    private String isAdmin;
    private String confirmPassword;
    private String statusMessage;
    private String emailAddress;
    private int version;
    private List< Contact > contactList;
    private ContactLogger contactLogger;
    private Logger logger;
    private static final String GOOD_RESULT = "UserMainPage";
    private static final String ERROR_RESULT = "UserErrorPage";
    

    /** Creates a new instance of UserBean */
    public UserBean() {
        try {
            this.setLogger( ContactLogger.getInstance().getLogger() );
            logger.fatal( "In constructor for AdminUserBean" );
            // get the DeadletterCount at the beginning
            
        } catch ( Exception e ) {
            e.printStackTrace( System.out );
        } finally {
            this.setLoggedIn( 0 );
            this.loggedIn = 0;
        } // end try/catch/finally
    } // end constructor
    
    public synchronized String addUser() {
        String result = GOOD_RESULT;
        
        this.getLogger().warn( "in method addUser" );
        
        this.getLogger().warn( "Confirm the password" );
        if ( !this.getPassword().equals( this.getConfirmPassword() ) ) {
            this.setStatusMessage( "The passwords are not equal" );
            result = ERROR_RESULT;
        } else {
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            
            try  {
                session.beginTransaction();

                String queryString = "from User where userName like :theName"
                   + " order by userName";

                Query q = session.createQuery( queryString );
                q.setString( "theName", this.getUserName()  );

                List< User > userList = AppHelper.castList( User.class, q.list() );
                
                if ( !userList.isEmpty() ) {
                    this.setStatusMessage( "There is already a user named " + this.getUserName() );
                    result = ERROR_RESULT;
                } else {
            
                    if ( !this.confirmIsAdmin() ) {
                        this.setIsAdmin( "n" );
                    }
                    this.getLogger().warn( "About to add user " + getUserName() );

                    User user = new User();
                    user.setUserName( getUserName() );
                    user.setPassword( this.getPassword() );
                    user.setIsAdmin( this.getIsAdmin() );
                    user.setDatestamp( new Date() );
                    user.setEmailAddress( this.getEmailAddress() );

                    session.save( user );
                    session.getTransaction().commit();
                    this.setStatusMessage( "The user " + this.getUserName() + " was added to the database" );
                    this.setLoggedIn( 1 );
                }
            } catch ( ConstraintViolationException cvEx ) {
                logger.warn( "ConstraintExpception: perhaps user " + this.getUserName() + " already exists" );
                this.setStatusMessage( "There is already a user named " + this.getUserName() );
                logger.warn( cvEx.getMessage() );
                result = "ERROR_RESULT";
            } catch ( DataException dEx ) {
                // logger.warn( "ConstraintExpception: perhaps user " + this.getUserName() + " already exists" );
                logger.warn( dEx.getMessage() );
                this.setStatusMessage( "There is already a user named " + this.getUserName() );
                dEx.printStackTrace();
                result = "ERROR_RESULT";
            } catch ( Exception e ) {
                e.printStackTrace();
            } finally {
                session.close();
            }// end try/catch
            
        } // if passwords not equal
        return result;
       
    } // end method addUser
    
    /**
     * 
     * @return 
     */
    public synchronized String loginAction() {
        
        String result = "UserMainPage";
        
            try {
                logger.warn( "Starting loginAction, loggedIn set to " + this.getLoggedIn() + " result = " + result );

                List userNameList = new ArrayList();

                @SuppressWarnings( "unchecked" ) // I hate this - perhaps it is time for Spring?

                Session session = NewHibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();

                String queryString = "from User where userName like :theName"
                + " and password like :thePass order by userName";

                Query q = session.createQuery( queryString );
                q.setString( "theName", this.getUserName() + "%" );
                q.setString( "thePass", this.getPassword() );
                
                List< User > userList = AppHelper.castList( User.class, q.list() );
                
                boolean gotAUser = false;
                for ( User user : userList ) {
                    if ( ( user.getUserName().equals( this.getUserName() ) ) &&
                            ( user.getPassword().equals( this.getPassword() ) ) ) {
                        gotAUser = true;
                    }
                }
                if ( !gotAUser ) {
                    result = "UserErrorPage";
                    this.setStatusMessage( "The user " + this.getUserName() +
                        " was not found in the database"
                    );
                    this.setLoggedIn( 0 );
                } else{
                    this.setLoggedIn( 1 );
                }      

                session.close();
                logger.warn( "Leaving loginAction, loggedIn == " + this.getLoggedIn() );
                logger.warn( "At end of loginAction, here is result: " + result );
            } catch ( NullPointerException npEx ) {
                logger.info( npEx.getMessage() );
                logger.info( npEx.getClass() );
                npEx.printStackTrace( System.out );
            } //  
        
        return result;
    } // end method loginAction
    
    /**
     * 
     * @return 
     */
    public synchronized String getListOfContacts() {

        String methodResult = "badLoginResult";
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        logger.warn( "Got session" );
        session.beginTransaction();

        @SuppressWarnings( "unchecked" )
        List< Contact > tempList = session.createQuery( "from Contact order by lastName, firstName" ).list();
                // java.util.Collections.checkedList(  session.createQuery( "from Users" ).list(), Users.class );
        try {
            if ( this.contactList != null && ( this.contactList.size() > 0 ) ) {
                this.contactList.clear();
            }
        } catch ( NullPointerException npEx ) {
            npEx.printStackTrace();
        }
        this.setContactList( tempList );        

        session.getTransaction().commit();
        session.close();

        for ( int i = 0; i < getContactList().size(); i++ ) {
            Contact nextC = ( Contact ) getContactList().get( i );
            logger.warn( "Contact: " + nextC.getFirstName() + " " +
                nextC.getLastName()
            );
        } // int i = 0; i < getContactList().size(); i++ )

        methodResult = "ListContacts";
        return methodResult;
    } // end method getListOfContacts


    public int getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn( int loggedIna ) {
        if ( ( loggedIna != 1 ) && ( loggedIna != 0 ) ) {
            this.loggedIn = 0;
        } else {
            this.loggedIn = loggedIna;
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public String getUserName() {
        return userName;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getIsAdmin() {
        
        return isAdmin;
    }

    public void setIsAdmin( String isAdminA ) {
        if ( isAdminA == null || 
                ( !isAdminA.equals( "n" ) && !isAdminA.equals( "y" ) ) ) {
            this.isAdmin = "n";
        } else {
            this.isAdmin = isAdminA;
        }
    }
    
    public boolean confirmIsAdmin() {
        if ( isAdmin == null ) {
            return false;
        } else if ( !isAdmin.equals( "n" ) && !isAdmin.equals( "y" ) ) {
            return false;
        } else {
            return true;
        }
    } // end method confirmIsAdmin

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List< Contact > getContactList() {
        return contactList;
    }

    public void setContactList(List< Contact > contactList) {
        this.contactList = contactList;
    }
    
    public int getVersion() {
        return version;
    }
} // end method UserBean
