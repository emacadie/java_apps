package info.shelfunit.runner;

import info.shelfunit.service.CustomerService;
import info.shelfunit.service.CustomerServiceImpl;

public class Application {

	public static void main( String[] args ) {
		// TODO Auto-generated method stub
		CustomerService service = new CustomerServiceImpl();
		System.out.println( service.findAll().get( 0 ).getFirstName() );
	} // end main

}
