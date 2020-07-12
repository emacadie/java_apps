package info.shelfunit.runner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import info.shelfunit.service.CustomerService;

public class Application {

	public static void main( String[] args ) {
		
		ApplicationContext appContext = new ClassPathXmlApplicationContext( "applicationContext.xml" );
		
		CustomerService service1 = appContext.getBean( "customerService", CustomerService.class );
		CustomerService service2 = appContext.getBean( "customerService", CustomerService.class );
		// same address, same object if singleton, diff if prototype
		System.out.println( "Here is service1: " + service1 );
		System.out.println( "Here is service2: " + service2 ); 
		System.out.println( service1.findAll().get( 0 ).getFirstName() );
	} // end main
}
