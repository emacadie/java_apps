package info.shelfunit.repository;

import java.util.ArrayList;
import java.util.List;

import info.shelfunit.model.Customer;

public class HibernateCustomerRepositoryImpl implements CustomerRepository {
	
	@Override
	public List< Customer > findAll() {
		List< Customer > customersList = new ArrayList< Customer >();
		
		Customer cust = new Customer();
		cust.setFirstName( "George" );
		cust.setLastname( "Washington" );
		customersList.add( cust );
		return customersList;
	}
}
