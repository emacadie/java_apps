/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.shelfunit.contact.beans;

import info.shelfunit.contact.NewHibernateUtil;
import info.shelfunit.contact.hibernate.ContactPhone;
import info.shelfunit.contact.util.AppHelper;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;

/**
 *
 * @author ericm
 */
@ManagedBean
@RequestScoped
public class ContactPhoneBean {
    
    // private ContactBean contactBean;
    private int contactCounter;
    private String phoneNumber;
    private String isPrimary;
    private String isActive;
    private Date datestamp;
    
    private final static String STALE_OBJECT   = "StaleObject";
    private final static String SUCCESSFUL     = "OperationSuccessful";
    private final static String NOT_SUCCESSFUL = "OperationNotSuccessful";
    
    @ManagedProperty( value="#{statusBean}" )
    private StatusBean statusBean;

    /** Creates a new instance of ContactPhoneBean */
    public ContactPhoneBean() {
    }
    
     public String addContactPhone( int contactCounterA ) {
        
        // System.out.println( contactBean.getContactCounter() );
        String result = SUCCESSFUL;
        System.out.println( "contactCounter: " + contactCounterA );
        ContactPhone cPhone = new ContactPhone();
        cPhone.setContactCounter( contactCounterA );
        cPhone.setPhoneNumber( phoneNumber );
        cPhone.setIsPrimary( isPrimary );
        cPhone.setIsActive( "y" );
        cPhone.setDatestamp( new Date() );
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
            
        try  {
            // see if we have a primary address for this contact 
            if ( this.updatePrimaryPhone( contactCounterA ).equals( STALE_OBJECT ) ) {
                result = NOT_SUCCESSFUL;
                this.getStatusBean().setStatus( "Unsuccessful" );
                this.getStatusBean().setStatusMessage( "The object was stale" );
            } else {

                session.beginTransaction();
                session.save( cPhone );
                session.getTransaction().commit();   
                this.getStatusBean().setStatus( "Successful" );
                this.getStatusBean().setStatusMessage( "The phone number was added" );
                this.setDatestamp( cPhone.getDatestamp() );
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
    } // end method addContactPhone
    
    public String deleteContactPhone( int contactCounterA, int phoneCounterA ) {
        String result = SUCCESSFUL;
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        
        
        try {
            session.beginTransaction();
            Query qP = session.createQuery( "Update ContactPhone set " +
                "isActive = :isAct where contactCounter = :cCounter and phoneCounter = :pCounter"
            );

            qP.setString( "isAct", "n" );
            qP.setInteger( "cCounter", contactCounterA );
            qP.setInteger( "pCounter", phoneCounterA );
            int rowCount = qP.executeUpdate();

            session.getTransaction().commit();
            this.getStatusBean().setStatus( "Successful" );
            this.getStatusBean().setStatusMessage( "The operation was good" );

        } catch ( StaleObjectStateException e ) {
            result = STALE_OBJECT;
            session.getTransaction().rollback();
            this.getStatusBean().setStatus( "Unsuccessful" );
            this.getStatusBean().setStatusMessage( "Exception" );
        } catch ( Exception e ) { 
            
            session.getTransaction().rollback();
            result = NOT_SUCCESSFUL;
            this.getStatusBean().setStatus( "Unsuccessful" );
            this.getStatusBean().setStatusMessage( "Exception" );
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return result;
    } // end method deleteContactPhone - line 117
    
    public String updateContactPhone( int contactCounterA, int phoneCounterA ) { // , String isPrimary, String emailAddress ) {
        String result = SUCCESSFUL;
         
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        
        if ( this.getIsPrimary().equals( "y" ) ) {
            if ( this.updatePrimaryPhone( contactCounterA ).equals( STALE_OBJECT ) ) {
                result = NOT_SUCCESSFUL;
                this.getStatusBean().setStatus( "Unsuccessful" );
                this.getStatusBean().setStatusMessage( "The object was stale" );
            }
        }
        
        try {
            session.beginTransaction();
            
            Query q = session.createQuery( "from ContactPhone where contactCounter " +
                " = :cCounter and phoneCounter = :pCounter"
            );
            q.setInteger( "cCounter", contactCounterA );
            q.setInteger( "pCounter", phoneCounterA );
            ContactPhone cPhone = ( ContactPhone ) q.uniqueResult(); 
            
            cPhone.setPhoneNumber( phoneNumber );
            cPhone.setIsPrimary( isPrimary );

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
    } // end method updateContactPhone

    
    /**
     * This is called by other methods that add or update emails. This method
     * will only be called if the method is going to add or update an email address
     * that is set to be the primary address. It will retrieve an email for the 
     * contact that is set to be primary, and change it to be non-primary.
     */ 
    private String updatePrimaryPhone( int contactCounterA ) {
        String result = "OKAY";
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        
        try {
            
            Query q = session.createQuery( "from ContactPhone where contactCounter " +
                    " = :cCounter and isPrimary like :isPrim"
                );
                
            q.setInteger( "cCounter", contactCounterA );
            q.setString( "isPrim", "y" );
            List< ContactPhone > cPhoneList = AppHelper.castList( ContactPhone.class, q.list() );
            if ( !cPhoneList.isEmpty() ) {
                session.beginTransaction();
                for ( ContactPhone cPhoneUnit : cPhoneList ) {
                    cPhoneUnit.setIsPrimary( "n" );
                    session.update( cPhoneUnit );
                }
                session.getTransaction().commit();
            } // if ( !cEmailList.isEmpty() )

        } catch ( StaleObjectStateException e ) {
            result = STALE_OBJECT;
        } finally {
            session.close();
        }
        /*
        try {
            session.beginTransaction();
            Query qP = session.createQuery( "Update ContactPhone set " +
                "isPrimary = :isPrim where contactCounter = :cCounter"
            );

            qP.setString(  "isPrim", "n" );
            qP.setInteger( "cCounter", contactCounter );
            int rowCount = qP.executeUpdate();

            session.getTransaction().commit();

        } catch ( StaleObjectStateException e ) {
            result = STALE_OBJECT;
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        */
        return result;
    } // updatePrimaryEmail - line 194

    
    public int getContactCounter() {
        return contactCounter;
    }

    public void setContactCounter( int contactCounter ) {
        this.contactCounter = contactCounter;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber ) {
        this.phoneNumber = phoneNumber;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
    
} // end class ContactPhoneBean
