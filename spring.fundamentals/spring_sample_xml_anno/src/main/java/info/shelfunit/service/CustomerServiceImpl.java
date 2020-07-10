package info.shelfunit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.shelfunit.model.Customer;
import info.shelfunit.repository.CustomerRepository;

@Service( "customerService" )
public class CustomerServiceImpl implements CustomerService {
	/*
	 * using this way is member injection
	@Autowired
	private CustomerRepository custRepository;
	*/
	/*
	// this is setter injection
	private CustomerRepository customerRepository;
	@Autowired
	public void setCustomerRepository( CustomerRepository customerRepository ) {
		System.out.println( "Using setter injection to set CustomerRepository" );
		this.customerRepository = customerRepository;
	}
	*/
	
	@Autowired
	public CustomerServiceImpl( CustomerRepository customerRepository ) {
		this.customerRepository = customerRepository;
		System.out.println( "Using constructor injection to set CustomerRepository" );
	}
	
	private CustomerRepository customerRepository;
	
	public void setCustomerRepository( CustomerRepository argCustomerRepository ) {
		// System.out.println( "Using setter injection to set CustomerRepository" );
		this.customerRepository = argCustomerRepository;
	}

	@Override
	public List< Customer > findAll() {
		return customerRepository.findAll();
	}
}
