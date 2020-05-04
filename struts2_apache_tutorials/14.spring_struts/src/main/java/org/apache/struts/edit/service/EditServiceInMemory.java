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
public class EditServiceInMemory implements EditService {
	
    private static Logger log = Logger.getLogger( EditServiceInMemory.class.getName() );
    private static Person person ;
    private static String [] carModels = {"Ford","Nissan"};

    static {
	log.info("In EditServiceInMemory static block");
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
	log.info("In EditServiceInMemory.getPerson");
	return EditServiceInMemory.person;
    }

    public void savePerson(Person personBean) {
	log.info("In EditServiceInMemory.savePerson");
	EditServiceInMemory.person.setFirstName(personBean.getFirstName() );
	EditServiceInMemory.person.setLastName(personBean.getLastName() );
	EditServiceInMemory.person.setSport(personBean.getSport() );
	EditServiceInMemory.person.setGender( personBean.getGender() );
	EditServiceInMemory.person.setResidency( personBean.getResidency() );
	EditServiceInMemory.person.setOver21( personBean.isOver21() );
	EditServiceInMemory.person.setCarModels(personBean.getCarModels() );
    }

}
