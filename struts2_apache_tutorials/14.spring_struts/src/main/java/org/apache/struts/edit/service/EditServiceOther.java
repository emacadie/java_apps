package org.apache.struts.edit.service;

import org.apache.struts.edit.model.Person;
import org.apache.log4j.Logger;

/**
 * Implement Services needed to edit and save
 * a Person object's state.  In this implementation
 * the Person object's state is stored in memory
 * @author brucephillips
 *
 */
public class EditServiceOther implements EditService {
	
    private static Person person ;
    private static String [] carModels = {"Ford","Nissan"};
    private static Logger log = Logger.getLogger( EditServiceOther.class.getName() );

    static {
	log.info("In EditServiceOther static block");
	person = new Person();
	person.setFirstName("Bruce");
	person.setLastName("Phillips");
	person.setSport("basketball");
	person.setGender("not sure");
	person.setResidency("KS");
	person.setOver21(true);		
	person.setCarModels( carModels);		
    }

    public Person getPerson() {
	log.info("In EditServiceOther.getPerson");
	return EditServiceOther.person;
    }

    public void savePerson(Person personBean) {
	log.info("In EditServiceOther.savePerson");
	EditServiceOther.person.setFirstName(personBean.getFirstName() );
	EditServiceOther.person.setLastName(personBean.getLastName() );
	EditServiceOther.person.setSport(personBean.getSport() );
	EditServiceOther.person.setGender( personBean.getGender() );
	EditServiceOther.person.setResidency( personBean.getResidency() );
	EditServiceOther.person.setOver21( personBean.isOver21() );
	EditServiceOther.person.setCarModels(personBean.getCarModels() );
    }

} // end EditServiceOther - line 51
