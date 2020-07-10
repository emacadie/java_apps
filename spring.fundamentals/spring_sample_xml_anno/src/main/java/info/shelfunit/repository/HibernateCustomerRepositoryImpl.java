package info.shelfunit.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import info.shelfunit.model.Customer;

@Repository( "customerRepository" )
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
