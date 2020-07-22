package info.shelfunit.web.reactive.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import info.shelfunit.web.reactive.model.Person;
import info.shelfunit.web.reactive.repository.PersonRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping( "/person" )
public class PersonController {

	@Autowired
	private PersonRepository repository;
	
	@GetMapping
	Flux< Person > list() {
		List< Person > people = repository.findAll();
		
		Flux< Person > fluxPeople = Flux.fromIterable( people );
		
		return fluxPeople; // Sending a Flux of type Person, not List
	}
	
	@GetMapping( "{id}" )
	public @ResponseBody Mono< Person > findById( @PathVariable Long id ) {
		Optional< Person > person = repository.findById( id );
		
		return Mono.just( person.orElse( Person.empty() ) );
	}
	
	/*
	 * It does not like this at all
	@GetMapping( "{id}" )
	public @ResponseBody Mono< Person > findById( @PathVariable String id ) {
		
		return Mono.just( Person.empty() );
	}
	*/
	
	
}
