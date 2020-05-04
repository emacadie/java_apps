package info.shelfunit.contact.beans;

import info.shelfunit.contact.NewHibernateUtil;
import info.shelfunit.contact.hibernate.Contact;
import info.shelfunit.contact.hibernate.ContactPhone;
import info.shelfunit.contact.hibernate.ContactEmail;
import info.shelfunit.contact.util.AppHelper;
import info.shelfunit.contact.util.ContactLogger;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;


@ManagedBean
@SessionScoped // keep SessionScoped
public class ContactBean {
    
    private int contactCounter;
    private String firstName;
    private String lastName;
    private Date dateStamp;
    private Logger logger;
    private int version;
    private int emailCounter;
    private String emailAddress;
    private String isPrimary;
    
    private int phoneCounter;
    private String phoneNumber;
    @ManagedProperty( value="#{statusBean}" )
    private StatusBean statusBean;
    private String status;
    private String statusMessage;
    private List< ContactPhone > contactPhoneList;
    private List< ContactEmail > contactEmailList;
    private List< ContactSearchBean > contactSearchBeanList;
    
    private final static String STALE_OBJECT   = "StaleObject";
    private final static String SUCCESSFUL     = "OperationSuccessful";
    private final static String NOT_SUCCESSFUL = "OperationNotSuccessful";
    
    /** Creates a new instance of ContactBean */
    public ContactBean() {
        try {
            this.setLogger( ContactLogger.getInstance().getLogger() );
            logger.fatal( "In constructor for ContactBean" );
            // this.setStatusBean();
        } catch ( Exception e ) {
            e.printStackTrace( System.out );
        } finally {
            
        } // end try/catch/finally
    } // end constructor
    
    public String addContact() {
        
        String result = "StatusPage";
        
        this.getLogger().warn( "in method addUser" );
        
        this.getLogger().warn( "Confirm the password" );
        /*
        if ( !this.getPassword().equals( this.getConfirmPassword() ) ) {
            this.setStatusMessage( "The passwords are not equal" );
            result = ERROR_RESULT;
        } else {
            */
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            
            try  {
                session.beginTransaction();
                /*
                String queryString = "from User where userName like :theName"
                   + " order by userName";

                Query q = session.createQuery( queryString );
                q.setString( "theName", this.getUserName()  );

                List< User > userList = q.list();
                if ( !userList.isEmpty() ) {
                    this.setStatusMessage( "There is already a user named " + this.getUserName() );
                    result = ERROR_RESULT;
                } else {
            
            */
                   
                    this.getLogger().warn( "About to add user " + getFirstName() );

                    Contact contact = new Contact();
                    contact.setFirstName(firstName);
                    contact.setLastName(lastName);
                    contact.setDatestamp( new Date() );
                    contact.setIsActive( "y" );

                    session.save( contact );
                    session.getTransaction().commit();
                    this.setStatusMessage( 
                       "The contact " + this.getFirstName() + " " + 
                       this.getLastName() + " was added to the database" 
                    );
                    this.setStatus( "Successful" );
                    this.setContactCounter( contact.getContactCounter() );
                // } // if userlist is empty
            } catch ( ConstraintViolationException cvEx ) {
                logger.warn( "ConstraintExpception: perhaps user " + this.getFullName() + " already exists" );
                this.setStatusMessage( "There is already a user named " + this.getFullName() );
                this.setStatus( "Unsuccessful" );
                logger.warn( cvEx.getMessage() );
                result = "ERROR_RESULT";
            } catch ( DataException dEx ) {
                // logger.warn( "ConstraintExpception: perhaps user " + this.getUserName() + " already exists" );
                logger.warn( dEx.getMessage() );
                this.setStatusMessage( "There is already a user named " + this.getFullName() );
                this.setStatus( "Unsuccessful" );
                dEx.printStackTrace();
                result = "ERROR_RESULT";
            } finally {
                session.close();
            }// end try/catch

            
        // } // if passwords not equal
        
        return result;
    } // end method addContact
    
    public String listContact( String counter ) {
        String result = "ContactInfo";
        System.out.println( "here is counter: " + counter );
        System.out.println( "In listContact with counter as a string" );
        return result;
    }
    /**
     * This will list info about the contact
     * @param counter
     * @return ContactInfo or ERROR_RESULT
     */
    public String listContact( int counter ) {
        String result = "ContactInfo";
        System.out.println( "here is counter: " + counter );
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
            
            try  {
                session.beginTransaction();
                
                String queryString = "from Contact where contactCounter like :theCounter and isActive like :isAct";

                Query q = session.createQuery( queryString );
                q.setInteger( "theCounter", counter );
                q.setString( "isAct", "y" );
                Contact contact = ( Contact ) q.uniqueResult();
                this.setFirstName( contact.getFirstName() );
                this.setLastName( contact.getLastName() );
                this.setContactCounter( contact.getContactCounter() );
                
                session.getTransaction().commit();
                this.setStatusMessage( 
                   "The contact " + this.getFirstName() + " " + 
                   this.getLastName() + " was added to the database" 
                );
                this.setStatus( "Successful" );
                
                // get contact addresses for this contact
                queryString = "from ContactPhone where contactCounter = :caCounter and isActive like :isAct";
                Query q2 = session.createQuery( queryString );
                q2.setInteger( "caCounter", counter );
                q2.setString( "isAct", "y" );
                contactPhoneList = AppHelper.castList( ContactPhone.class, q2.list() );
                                
                // get emails for this contact
                queryString = "from ContactEmail where contactCounter = :caCounter and isActive like :isAct";
                Query q3 = session.createQuery( queryString );
                q3.setInteger( "caCounter", counter );
                q3.setString( "isAct", "y" );
                contactEmailList = AppHelper.castList( ContactEmail.class, q3.list() ) ;
                               
                result = "ContactInfo";   
            } catch ( ConstraintViolationException cvEx ) {
                logger.warn( "ConstraintExpception: perhaps user " + this.getFullName() + " already exists" );
                this.setStatusMessage( "There is already a user named " + this.getFullName() );
                this.setStatus( "Unsuccessful" );
                logger.warn( cvEx.getMessage() );
                result = "ERROR_RESULT";
            } catch ( DataException dEx ) {
                // logger.warn( "ConstraintExpception: perhaps user " + this.getUserName() + " already exists" );
                logger.warn( dEx.getMessage() );
                this.setStatusMessage( "There is already a user named " + this.getFullName() );
                this.setStatus( "Unsuccessful" );
                dEx.printStackTrace();
                result = "ERROR_RESULT";
            } finally {
                session.close();
            } // end try/catch
        
        return result;
    } // end method listContact
    
    public String goToContactPage(  ) {
        String result = "ContactInfo";
        System.out.println( "here is counter: " + contactCounter );
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
            
            try  {
                session.beginTransaction();
                
                String queryString = "from Contact where contactCounter like :theCounter and isActive like :isAct";

                Query q = session.createQuery( queryString );
                q.setInteger( "theCounter", contactCounter );
                q.setString( "isAct", "y" );

                Contact contact = ( Contact ) q.uniqueResult();
                this.setFirstName( contact.getFirstName() );
                this.setLastName( contact.getLastName() );
                this.setContactCounter( contact.getContactCounter() );
                
                session.getTransaction().commit();
                this.setStatusMessage( 
                   "The contact " + this.getFirstName() + " " + 
                   this.getLastName() + " was added to the database" 
                );
                this.setStatus( "Successful" );
                
                // get contact addresses for this contact
                queryString = "from ContactPhone where contactCounter = :caCounter and isActive = :isAct";
                Query q2 = session.createQuery( queryString );
                
                q2.setInteger( "caCounter", contactCounter );
                q2.setString( "isAct", "y" );
                contactPhoneList = AppHelper.castList( ContactPhone.class, q2.list() );
                                
                // get emails for this contact
                queryString = "from ContactEmail where contactCounter = :caCounter and isActive = :isAct";
                Query q3 = session.createQuery( queryString );
                q3.setInteger( "caCounter", contactCounter );
                q3.setString( "isAct", "y" );
                contactEmailList = AppHelper.castList( ContactEmail.class, q3.list() ) ;
                               
                result = "ContactInfo";   
            } catch ( ConstraintViolationException cvEx ) {
                logger.warn( "ConstraintExpception: perhaps user " + this.getFullName() + " already exists" );
                this.setStatusMessage( "There is already a user named " + this.getFullName() );
                this.setStatus( "Unsuccessful" );
                logger.warn( cvEx.getMessage() );
                result = "ERROR_RESULT";
            } catch ( DataException dEx ) {
                // logger.warn( "ConstraintExpception: perhaps user " + this.getUserName() + " already exists" );
                logger.warn( dEx.getMessage() );
                this.setStatusMessage( "There is already a user named " + this.getFullName() );
                this.setStatus( "Unsuccessful" );
                dEx.printStackTrace();
                result = "ERROR_RESULT";
            } finally {
                session.close();
            } // end try/catch
     
        return result;
    } // end method listContact
    
    
    public String dropContact( int contactCounter ) {
        String result = "";
        Session session = NewHibernateUtil.getSessionFactory().openSession();

        try  {
            // drop emails 
            try {
                session.beginTransaction();
                Query qE = session.createQuery( "Update ContactEmail set " +
                    "isActive = :isAct where contactCounter = :cCounter"
                );

                qE.setString(  "isAct", "n" );
                qE.setInteger( "cCounter", contactCounter );
                int rowCount = qE.executeUpdate();

                session.getTransaction().commit();

            } catch ( StaleObjectStateException e ) {
                result = STALE_OBJECT;
                session.getTransaction().rollback();
            } finally {
                session.close();
            }

            // drop phone
            try {
                session.beginTransaction();
                Query qP = session.createQuery( "Update ContactPhone set " +
                    "isActive = :isAct where contactCounter = :cCounter"
                );

                qP.setString(  "isAct", "n" );
                qP.setInteger( "cCounter", contactCounter );
                int rowCount = qP.executeUpdate();

                session.getTransaction().commit();

            } catch ( StaleObjectStateException e ) {
                result = STALE_OBJECT;
                session.getTransaction().rollback();
            } finally {
                session.close();
            }

            // now drop the contact
            try {
                session.beginTransaction();
                Query qC = session.createQuery( "Update Contact set " +
                    "isActive = :isAct where contactCounter = :cCounter"
                );

                qC.setString(  "isAct", "n" );
                qC.setInteger( "cCounter", contactCounter );
                int rowCount = qC.executeUpdate();

                session.getTransaction().commit();

            } catch ( StaleObjectStateException e ) {
                result = STALE_OBJECT;
                session.getTransaction().rollback();
            } finally {
                session.close();
            }              

            result = "ContactInfo";   
        } catch ( ConstraintViolationException cvEx ) {
            logger.warn( "ConstraintExpception: perhaps user " + this.getFullName() + " already exists" );
            this.setStatusMessage( "There is already a user named " + this.getFullName() );
            this.setStatus( "Unsuccessful" );
            logger.warn( cvEx.getMessage() );
            result = "ERROR_RESULT";
        } catch ( DataException dEx ) {
            // logger.warn( "ConstraintExpception: perhaps user " + this.getUserName() + " already exists" );
            logger.warn( dEx.getMessage() );
            this.setStatusMessage( "There is already a user named " + this.getFullName() );
            this.setStatus( "Unsuccessful" );
            dEx.printStackTrace();
            result = "ERROR_RESULT";
        } finally {
            session.close();
        } // end try/catch

        return result;
    } // end method dropContact   
    
    public String forwardUpdateContactEmail( int contactCounterA, 
            int emailCounterA, 
            String emailAddressA, 
            String isPrimaryA ) {
        String result = "UpdateEmailForm";
        try {
            this.setContactCounter( contactCounterA );
            this.setEmailCounter( emailCounterA );
            this.setEmailAddress( emailAddressA );
            this.setIsPrimary( isPrimaryA );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return result;
    } // end method forwardUpdateContactEmail
    
    public String forwardUpdateContactPhone( int contactCounterA, 
            int phoneCounterA, 
            String phoneNumberA, 
            String isPrimaryA ) {
        String result = "UpdatePhoneForm";
        
        this.setContactCounter( contactCounterA );
        this.setPhoneCounter( phoneCounterA );
        this.setPhoneNumber( phoneNumberA );
        this.setIsPrimary( isPrimaryA );
        
        return result;
    } // end method forwardUpdateContactPhone
    

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    public Date getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp( Date dateStamp ) {
        this.dateStamp = dateStamp;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    
    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }

    public int getVersion() {
        return version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int getContactCounter() {
        return contactCounter;
    }

    public void setContactCounter(int contactCounter) {
        this.contactCounter = contactCounter;
    }

    public List< ContactPhone > getContactPhoneList() {
        return contactPhoneList;
    }

    public void setContactPhoneList( List< ContactPhone > contactPhoneList ) {
        this.contactPhoneList = contactPhoneList;
    }

    public List< ContactEmail > getContactEmailList() {
        return contactEmailList;
    }

    public void setContactEmailList(List< ContactEmail > contactEmailList) {
        this.setContactEmailList(contactEmailList);
    }

    public int getEmailCounter() {
        return emailCounter;
    }

    public void setEmailCounter(int emailCounter) {
        this.emailCounter = emailCounter;
    }

    public String getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(String isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getPhoneCounter() {
        return phoneCounter;
    }

    public void setPhoneCounter(int phoneCounter) {
        this.phoneCounter = phoneCounter;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StatusBean getStatusBean() {
        return statusBean;
    }

    public void setStatusBean(StatusBean statusBean) {
        this.statusBean = statusBean;
    }

    public List< ContactSearchBean > getContactSearchBeanList() {
        return contactSearchBeanList;
    }

    public void setContactSearchBeanList(List< ContactSearchBean > contactSearchBeanList) {
        this.contactSearchBeanList = contactSearchBeanList;
    }
    
    public String searchByPhoneNumber() {
        String result = "SearchResultPhoneNumber";
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        
        try  {
            // session.beginTransaction();
            String queryString = "from ContactPhone where phoneNumber like :phoneN and isActive like :isAct";

            Query q = session.createQuery( queryString );
            q.setString( "phoneN", "%" +  phoneNumber + "%" );
            q.setString( "isAct", "y" );

            List< ContactPhone > cPhoneList = AppHelper.castList( ContactPhone.class, q.list() ) ;

            Map< Integer, Contact > contactMap = new HashMap< Integer, Contact >();
            if ( cPhoneList.isEmpty() ) {
                result = "NoSearchResults";
            }
            this.setContactSearchBeanList( new LinkedList< ContactSearchBean >() );
            for ( ContactPhone cPhone : cPhoneList ) {
                if ( !contactMap.containsKey( cPhone.getContactCounter() ) ) {
                    Query qC = session.createQuery( "from Contact where contactCounter = :cCounter" );
                    qC.setInteger( "cCounter", cPhone.getContactCounter() );
                    Contact contact = ( Contact ) qC.uniqueResult();
                    contactMap.put( cPhone.getContactCounter(), contact );    
                }
                System.out.println( "Size of contactMap: " + contactMap.size() );
                ContactSearchBean csBean = new ContactSearchBean();
                csBean.setContactCounter( cPhone.getContactCounter() );
                csBean.setFirstName( contactMap.get( cPhone.getContactCounter() ).getFirstName() );
                csBean.setLastName( contactMap.get( cPhone.getContactCounter() ).getLastName() );
                csBean.setFullName( csBean.getFirstName() + " " + csBean.getLastName() );
                csBean.setPhoneCounter( cPhone.getPhoneCounter() );
                csBean.setPhoneNumber( cPhone.getPhoneNumber() );
                this.getContactSearchBeanList().add( csBean );

            } // for ( ContactPhone cPhone : cPhoneList )
                /*
                this.setStatusMessage( 
                   "The contact " + this.getFirstName() + " " + 
                   this.getLastName() + " was added to the database" 
                );
                this.setStatus( "Successful" );
                */
      
            // result = "ContactInfo";   

        } catch ( Exception dEx ) {
            // logger.warn( "ConstraintExpception: perhaps user " + this.getUserName() + " already exists" );
            // logger.warn( dEx.getMessage() );
            // this.setStatusMessage( "There is already a user named " + this.getFullName() );
            // this.setStatus( "Unsuccessful" );
            dEx.printStackTrace();
            result = "ERROR_RESULT";
        } finally {
            session.close();
        } // end try/catch
            
        return result;
    } // end method searchByPhoneNumber
    
    public String searchByEmailAddress( String emailString ) {
        String result = "";
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        
        try  {
            // session.beginTransaction();

            String queryString = "from ContactEmail where emailAddress like :emailA and isActive like :isAct";
            Query q = session.createQuery( queryString );
            q.setString( "emailA", "%" +  emailString + "%" );
            q.setString( "isAct", "y" );

            List< ContactEmail > cEmailList = AppHelper.castList( ContactEmail.class, q.list() ) ;

            Map< Integer, Contact > contactMap = new HashMap< Integer, Contact >();
            if ( cEmailList.isEmpty() ) {
                result = "NoSearchResults";
            }
            this.setContactSearchBeanList( new LinkedList< ContactSearchBean >() );
            for ( ContactEmail cEmail : cEmailList ) {
                if ( !contactMap.containsKey( cEmail.getContactCounter() ) ) {
                    Query qC = session.createQuery( "from Contact where contactCounter = :cCounter" );
                    qC.setInteger( "cCounter", cEmail.getContactCounter() );
                    Contact contact = ( Contact ) qC.uniqueResult();
                    contactMap.put( cEmail.getContactCounter(), contact );    
                }
                System.out.println( "Size of contactMap: " + contactMap.size() );
                ContactSearchBean csBean = new ContactSearchBean();
                csBean.setContactCounter( cEmail.getContactCounter() );
                csBean.setFirstName( contactMap.get( cEmail.getContactCounter() ).getFirstName() );
                csBean.setLastName( contactMap.get( cEmail.getContactCounter() ).getLastName() );
                
                csBean.setEmailCounter( cEmail.getEmailCounter() );
                csBean.setEmailAddress( cEmail.getEmailAddress() );
                this.getContactSearchBeanList().add( csBean );

            } // for ( ContactPhone cPhone : cPhoneList )
                /*
                this.setStatusMessage( 
                   "The contact " + this.getFirstName() + " " + 
                   this.getLastName() + " was added to the database" 
                );
                this.setStatus( "Successful" );
                */
   
            result = "ContactInfo";   

        } catch ( Exception dEx ) {
            // logger.warn( "ConstraintExpception: perhaps user " + this.getUserName() + " already exists" );
            // logger.warn( dEx.getMessage() );
            // this.setStatusMessage( "There is already a user named " + this.getFullName() );
            // this.setStatus( "Unsuccessful" );
            dEx.printStackTrace();
            result = "ERROR_RESULT";
        } finally {
            session.close();
        } // end try/catch

        return result;
    } // end method searchByEmailAddress
    
} // end class ContactBean
