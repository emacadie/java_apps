package info.shelfunit.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import info.shelfunit.repository.CustomerRepository;
import info.shelfunit.repository.HibernateCustomerRepositoryImpl;
import info.shelfunit.service.CustomerService;
import info.shelfunit.service.CustomerServiceImpl;

@Configuration
@ComponentScan( { "info.shelfunit" } ) // for autowiring
@PropertySource( "app.properties" )
public class AppConfig {
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer getPropertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	/* setter injection */
	/*
	@Bean( name = "customerService" )
	public CustomerService getCustomerService() {
		CustomerServiceImpl service = new CustomerServiceImpl();
		service.setCustomerRepository( this.getCustomerRepository() );
		return service;
	}
	*/
	/* constructor injection */
	/*
	@Bean( name = "customerService" )
	public CustomerService getCustomerService() {
		CustomerServiceImpl service = new CustomerServiceImpl( this.getCustomerRepository() );
		// System.out.println( "Using constructor injection for CustomerServiceImpl" );
		return service;
	}
	*/
	/* autowiring */
	/*
	@Bean( name = "customerService" )
	public CustomerService getCustomerService() {
		CustomerServiceImpl service = new CustomerServiceImpl( );
		service.setCustomerRepository( this.getCustomerRepository() );
		System.out.println( "Using constructor injection for autowiring for CustomerServiceImpl" );
		return service;
	}
	*/
	
	/* more autowiring 
	 * while using @Autowired annotation on CustomerRepository member within CustomerServiceImpl
	 *  
	 * */
	/*
	@Bean( name = "customerService" )
	public CustomerService getCustomerService() {
		CustomerServiceImpl service = new CustomerServiceImpl( );
		return service;
	}
	*/
	
	/* setter injection */
	/*
	@Bean( name = "customerRepository" )
	public CustomerRepository getCustomerRepository() {
		return new HibernateCustomerRepositoryImpl();
	}
	*/
	

}
