package info.shelfunit.web.reactive.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import info.shelfunit.web.reactive.model.Person;

public interface PersonRepository extends JpaRepository< Person, Long > {

}
