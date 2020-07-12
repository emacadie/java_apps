package info.shelfunit.runner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import info.shelfunit.service.CustomerService;

public class Application {

	public static void main( String[] args ) {
		ApplicationContext appContext = new AnnotationConfigApplicationContext( AppConfig.class );
		CustomerService service1 = appContext.getBean( "customerService", CustomerService.class );
		CustomerService service2 = appContext.getBean( "customerService", CustomerService.class );
		// we should get same address to prove they are the same instance if singleton
		// different address if prototype
		System.out.println( "Address of service1: " + service1 );
		System.out.println( "Address of service2: " + service2 );
		System.out.println( service1.findAll().get( 0 ).getFirstName() );
	} // end main

}
