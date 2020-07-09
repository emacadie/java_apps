package info.shelfunit.service;

import java.util.List;

import info.shelfunit.model.Customer;

public interface CustomerService {

	List< Customer > findAll();

}