package info.shelfunit.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import info.shelfunit.model.Customer;

@Repository( "customerRepository" ) // can use this if not defined in AppConfig.java
public class HibernateCustomerRepositoryImpl implements CustomerRepository {
	
	@Value( "${dbUserName}" )
	private String dbUserName;
	
	@Override
	public List< Customer > findAll() {
		List< Customer > customersList = new ArrayList< Customer >();
		System.out.println( "dbUserName with annotations: " + dbUserName );
		Customer cust = new Customer();
		cust.setFirstName( "George" );
		cust.setLastname( "Washington" );
		customersList.add( cust );
		return customersList;
	}
}
