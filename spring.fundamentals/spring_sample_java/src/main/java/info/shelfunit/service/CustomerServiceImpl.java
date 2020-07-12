package info.shelfunit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.shelfunit.model.Customer;
import info.shelfunit.repository.CustomerRepository;

@Service( "customerService" ) // can use this if we take it out of AppConfig
public class CustomerServiceImpl implements CustomerService {
	
	// @Autowired // could be here to wire customerRepository 
	private CustomerRepository customerRepository;
	
	public CustomerServiceImpl() {}
	
	public CustomerServiceImpl( CustomerRepository argCustomerRepository ) {
		System.out.println( "In constructor for injection in CustomerServiceImpl" );
		this.customerRepository = argCustomerRepository;
	}
	
	@Autowired // could be here to wire customerRepository
	public void setCustomerRepository( CustomerRepository customerRepository ) {
		System.out.println( "In setter for setter injection in CustomerServiceImpl" );
		this.customerRepository = customerRepository;
	}

	@Override
	public List< Customer > findAll() {
		return customerRepository.findAll();
	}
}
