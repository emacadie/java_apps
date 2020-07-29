package info.shelfunit.conferencedemo.controllers;

import info.shelfunit.conferencedemo.models.Session;
import info.shelfunit.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/api/v1/sessions" )
public class SessionsController {

    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping
    public List< Session > list() {
        return sessionRepository.findAll();
    }

    // get a specific session by id
    @GetMapping
    @RequestMapping( "{id}" ) // this will be appended to the url in the class @RequestMapping
    public Session get( @PathVariable Long id ) {
        return sessionRepository.getOne( id );
    }

    @PostMapping
    // @ResponseStatus( HttpStatus.CREATED ) // http code 201
    public Session create( @RequestBody final Session session ) {
        return sessionRepository.saveAndFlush( session );
    }

    @RequestMapping( value = "{id}", method = RequestMethod.DELETE )
    public void delete( @PathVariable Long id ) {
        // also need to check for child records before deleting
        sessionRepository.deleteById( id );
    }

    @RequestMapping( value = "{id}", method = RequestMethod.PUT )
    public Session update( @PathVariable Long id, @RequestBody Session session ) {
        // because this is a PUT, we expect all the attributes to be passed in
        // A PATCH can be used to only pass what needs to be updated; new to HTTP in 2010
        // TODO: add validation ensuring that all attributes are passed in
        Session existingSession = sessionRepository.getOne( id );
        // args: source, target, array of stuff to ignore
        BeanUtils.copyProperties( session, existingSession, "session_id" );
        return sessionRepository.saveAndFlush( existingSession );
    }

}
