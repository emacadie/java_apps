package info.shelfunit.contact.beans;

import info.shelfunit.contact.NewHibernateUtil;
import info.shelfunit.contact.hibernate.ContactEmail;
import info.shelfunit.contact.util.AppHelper;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;


@ManagedBean
@RequestScoped
public class ContactEmailBean {
    
    // private int contactCounter;
    private String emailAddress;
    private String isPrimary;
    private String isActive;
    private Date datestamp;
    
    @ManagedProperty( value="#{statusBean}" )
    private StatusBean statusBean;
    
    private final static String STALE_OBJECT   = "StaleObject";
    private final static String SUCCESSFUL     = "OperationSuccessful";
    private final static String NOT_SUCCESSFUL = "OperationNotSuccessful";

    /** Creates a new instance of ContactEmailBean */
    public ContactEmailBean() {
        System.out.println( "In constructor for ContactEmailBean" );
    }
    
    /**
     * 
     * @param contactCounter
     * @return 
     */
    public String addContactEmail( int contactCounterA ) {
        
        String result = SUCCESSFUL;
        System.out.println( "contactCounter: " + contactCounterA );
        ContactEmail cEmail = new ContactEmail();
        cEmail.setContactCounter( contactCounterA );
        cEmail.setEmailAddress( emailAddress );
        cEmail.setIsPrimary( isPrimary );
        cEmail.setIsActive( "y" );
        cEmail.setDatestamp( new Date() );
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
            
            try {
                // see if we have any primary emails for this contact 
                
                if ( this.updatePrimaryEmail( contactCounterA ).equals( STALE_OBJECT ) ) {
                    result = NOT_SUCCESSFUL;
                    this.getStatusBean().setStatus( "Unsuccessful" );
                    this.getStatusBean().setStatusMessage( "The object was stale" );
                } else {
                    session.beginTransaction();
                    session.save( cEmail );
                    session.getTransaction().commit();   
                    this.getStatusBean().setStatus( "Successful" );
                    this.getStatusBean().setStatusMessage( "The email was added" );
                    this.setDatestamp( cEmail.getDatestamp() );
                }
            } catch ( Exception e ) {
                session.getTransaction().rollback();
                result = NOT_SUCCESSFUL;
                this.getStatusBean().setStatus( "Unsuccessful" );
                this.getStatusBean().setStatusMessage( "Exception" );
            
                e.printStackTrace();
            } finally {
                session.close();
            }// end try/catch
        
        return result;
    } // end method addContactEmail
    
    public String deleteContactEmail( int contactCounter, int emailCounter ) {
        System.out.println( "In ContactEmailBean.deleteContactEmail( int " + contactCounter + ", int " + emailCounter + " )" );
        String result = SUCCESSFUL;
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            Query q = session.createQuery( "delete from ContactEmail where contactCounter " +
                " = :cCounter and emailCounter = :eCounter"
            );
            q.setInteger( "cCounter", contactCounter );
            q.setInteger( "eCounter", emailCounter );
            q.executeUpdate();

            session.getTransaction().commit();
            this.getStatusBean().setStatus( "Successful" );
            this.getStatusBean().setStatusMessage( "The operation was good" ); 
        } catch ( Exception e ) {
            e.printStackTrace();
            session.getTransaction().rollback();
            result = NOT_SUCCESSFUL;
            this.getStatusBean().setStatus( "Unsuccessful" );
            this.getStatusBean().setStatusMessage( "Exception" );
        } finally {
            session.close();
        }
        return result;
    } // end method deleteContactEmail
    
    public String updateContactEmail( int contactCounterA, int emailCounterA ) { // , String isPrimary, String emailAddress ) {
        String result = SUCCESSFUL;
         
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        
        if ( this.getIsPrimary().equals( "y" ) ) {
            if ( this.updatePrimaryEmail( contactCounterA ).equals( STALE_OBJECT ) ) {
                result = NOT_SUCCESSFUL;
                this.getStatusBean().setStatus( "Unsuccessful" );
                this.getStatusBean().setStatusMessage( "The object was stale" );
            }
        }
        
        try {
            session.beginTransaction();
            
            Query q = session.createQuery( "from ContactEmail where contactCounter " +
                " = :cCounter and emailCounter = :eCounter"
            );
            q.setInteger( "cCounter", contactCounterA );
            q.setInteger( "eCounter", emailCounterA );
            ContactEmail cEmail = ( ContactEmail ) q.uniqueResult(); 

            cEmail.setEmailAddress( emailAddress );
            cEmail.setIsPrimary( isPrimary );

            session.getTransaction().commit();

            this.getStatusBean().setStatus( "Successful" );
            this.getStatusBean().setStatusMessage( "The operation was good" );  
        } catch ( Exception e ) {
            session.getTransaction().rollback();
            result = NOT_SUCCESSFUL;
            this.getStatusBean().setStatus( "Unsuccessful" );
            this.getStatusBean().setStatusMessage( "Exception" );
        } finally {
            session.close();
        }
        return result;
    } // end method updateContactEmail
    
    /**
     * This is called by other methods that add or update emails. This method
     * will only be called if the method is going to add or update an email address
     * that is set to be the primary address. It will retrieve an email for the 
     * contact that is set to be primary, and change it to be non-primary.
     */ 
    private String updatePrimaryEmail( int contactCounterA ) {
        String result = "OKAY";
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        /*
        try {
            Query q = session.createQuery( "from ContactEmail where contactCounter " +
                        " = :cCounter and isPrimary like :isPrim"
            );

            q.setInteger( "cCounter", contactCounterA );
            q.setString( "isPrim", "y" );
            List< ContactEmail > cEmailList = AppHelper.castList( ContactEmail.class, q.list() );
            if ( !cEmailList.isEmpty() ) {
                   session.beginTransaction();
                   for ( ContactEmail cEmailUnit : cEmailList ) {
                   cEmailUnit.setIsPrimary( "n" );
                   session.update( cEmailUnit );
               }
               session.getTransaction().commit();
            } // if ( !cEmailList.isEmpty() )
        } catch ( StaleObjectStateException e ) {
            result = STALE_OBJECT;
        } finally {
            session.close();
        }
        */
        try {
            session.beginTransaction();
            Query qE = session.createQuery( "Update ContactEmail set " +
                "isPrimary = :isPrim where contactCounter = :cCounter"
            );

            qE.setString(  "isPrim", "n" );
            qE.setInteger( "cCounter", contactCounterA );
            int rowCount = qE.executeUpdate();

            session.getTransaction().commit();

        } catch ( StaleObjectStateException e ) {
            result = STALE_OBJECT;
            session.getTransaction().rollback();
        } finally {
            
            session.close();
        }
        return result;
    } // updatePrimaryEmail - line 186
    
    /*
    public int getContactCounter() {
        return contactCounter;
    }

    public void setContactCounter(int contactCounter) {
        this.contactCounter = contactCounter;
    }
    */
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress( String emailAddress ) {
        this.emailAddress = emailAddress;
    }

    public String getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary( String isPrimary ) {
        this.isPrimary = isPrimary;
    }

    public Date getDatestamp() {
        return datestamp;
    }

    public void setDatestamp( Date datestamp ) {
        this.datestamp = datestamp;
    }

    public StatusBean getStatusBean() {
        return statusBean;
    }

    public void setStatusBean( StatusBean statusBean ) {
        this.statusBean = statusBean;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
    
} // end class
