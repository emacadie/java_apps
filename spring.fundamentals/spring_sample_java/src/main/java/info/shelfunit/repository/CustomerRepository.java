package info.shelfunit.repository;

import java.util.List;

import info.shelfunit.model.Customer;

public interface CustomerRepository {

	List< Customer > findAll();

}
