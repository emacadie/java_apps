package info.shelfunit.contact.beans;

import info.shelfunit.contact.NewHibernateUtil;
import info.shelfunit.contact.hibernate.Contact;
import info.shelfunit.contact.hibernate.ContactEmail;
import info.shelfunit.contact.hibernate.ContactPhone;
import info.shelfunit.contact.util.AppHelper;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author ericm
 */
@ManagedBean
@RequestScoped
public class ContactSearchBean {
    
    
    private Integer contactCounter;
    private int version;
    private String firstName;
    private String lastName;
    private String isActive;
    private Date datestamp;
    private Integer emailCounter;
    private String emailAddress;
    private String isPrimary;
    private Integer phoneCounter;
    private String phoneNumber;
    private String fullName;
    private List< ContactSearchBean > contactSearchBeanList;
    
    
     
    /** Creates a new instance of ContactSearchBean */
    public ContactSearchBean() {
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

    public Integer getContactCounter() {
        return contactCounter;
    }

    public void setContactCounter(Integer contactCounter) {
        this.contactCounter = contactCounter;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Date getDatestamp() {
        return datestamp;
    }

    public void setDatestamp(Date datestamp) {
        this.datestamp = datestamp;
    }

    public Integer getEmailCounter() {
        return emailCounter;
    }

    public void setEmailCounter(Integer emailCounter) {
        this.emailCounter = emailCounter;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(String isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Integer getPhoneCounter() {
        return phoneCounter;
    }

    public void setPhoneCounter(Integer phoneCounter) {
        this.phoneCounter = phoneCounter;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    

    public List< ContactSearchBean > getContactSearchBeanList() {
        return contactSearchBeanList;
    }

    public void setContactSearchBeanList(List< ContactSearchBean > contactSearchBeanList) {
        this.contactSearchBeanList = contactSearchBeanList;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    
    
} // end class ContactSearchBean
