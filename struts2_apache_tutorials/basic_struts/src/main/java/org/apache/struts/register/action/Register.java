package org.apache.struts.register.action;

import org.apache.struts.register.exceptions.SecurityBreachException;
import org.apache.struts.register.model.Person;
import com.opensymphony.xwork2.ActionSupport;

public class Register extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private Person personBean;

    @Override
    public String execute() throws Exception {
	// call Service class to store personBean's state in database
	return SUCCESS;
    }

    public Person getPersonBean() {
	return personBean;
    }

    public void setPersonBean(Person person) {
	personBean = person;
    }

    public void validate() {
	if ( personBean.getFirstName().length() == 0 ) {
	    addFieldError( "personBean.firstName", "First name is required." );
	}

	if ( personBean.getEmail().length() == 0 ) {
	    addFieldError( "personBean.email", "Email is required." );
	}

	if ( personBean.getAge() < 18 ) {
	    addFieldError( "personBean.age", "Age is required and must be 18 or older" );
	}
    } // end validate
    
    	public void throwException() throws Exception {

		throw new Exception("Exception thrown from throwException");

	}

	public void throwNullPointerException() throws NullPointerException {

		throw new NullPointerException("Null Pointer Exception thrown from "
				+ Register.class.toString());
	}

	public void throwSecurityException() throws SecurityBreachException {

		throw new SecurityBreachException("Security breach exception thrown from throwSecurityException");
	}

} // end class
