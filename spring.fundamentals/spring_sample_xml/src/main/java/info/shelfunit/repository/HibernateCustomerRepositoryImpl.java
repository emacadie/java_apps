package info.shelfunit.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import info.shelfunit.model.Customer;

public class HibernateCustomerRepositoryImpl implements CustomerRepository {
	
	@Value( "${dbUserName}" ) // accessing properties in app.properties with Java and XML
	private String dbUserName;
	
	/*
	 * we need this if we just bring in property with XML
	public void setDbUserName( String dbUserName ) {
		this.dbUserName = dbUserName;
	}
	*/

	@Override
	public List< Customer > findAll() {
		
		System.out.println( "Here is dbUserName: " + dbUserName );
		
		List< Customer > customersList = new ArrayList< Customer >();
		
		Customer cust = new Customer();
		cust.setFirstName( "George" );
		cust.setLastname( "Washington" );
		customersList.add( cust );
		return customersList;
	}
}
