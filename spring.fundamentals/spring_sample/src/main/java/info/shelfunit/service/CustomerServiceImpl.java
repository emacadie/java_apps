package info.shelfunit.service;

import java.util.List;

import info.shelfunit.model.Customer;
import info.shelfunit.repository.CustomerRepository;
import info.shelfunit.repository.HibernateCustomerRepositoryImpl;

public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository custRepo = new HibernateCustomerRepositoryImpl();
	
	@Override
	public List< Customer > findAll() {
		return custRepo.findAll();
	}
}
