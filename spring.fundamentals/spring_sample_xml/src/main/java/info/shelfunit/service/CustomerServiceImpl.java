package info.shelfunit.service;

import java.util.List;

import info.shelfunit.model.Customer;
import info.shelfunit.repository.CustomerRepository;

public class CustomerServiceImpl implements CustomerService {
	
	private CustomerRepository customerRepository;
	
	public CustomerServiceImpl() {
		
	}
	
	public CustomerServiceImpl( CustomerRepository argCustomerRepository ) {
		this.customerRepository = argCustomerRepository; 
	}
	
	public void setCustomerRepository( CustomerRepository customerRepository ) {
		this.customerRepository = customerRepository;
	}

	@Override
	public List< Customer > findAll() {
		return customerRepository.findAll();
	}
}
