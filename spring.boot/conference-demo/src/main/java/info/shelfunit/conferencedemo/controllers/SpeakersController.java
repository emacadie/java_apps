package info.shelfunit.conferencedemo.controllers;

import info.shelfunit.conferencedemo.models.Session;
import info.shelfunit.conferencedemo.models.Speaker;
import info.shelfunit.conferencedemo.repositories.SessionRepository;
import info.shelfunit.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/api/v1/speakers" )
public class SpeakersController {

    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List< Speaker > list() {
        return speakerRepository.findAll();
    }

    // get a speaker session by id
    @GetMapping
    @RequestMapping( "{id}" ) // this will be appended to the url in the class @RequestMapping
    public Speaker get( @PathVariable Long id ) {
        return speakerRepository.getOne( id );
    }

    @PostMapping
    // @ResponseStatus( HttpStatus.CREATED ) // http code 201
    public Speaker create( @RequestBody final Speaker speaker ) {
        return speakerRepository.saveAndFlush( speaker );
    }

    @RequestMapping( value = "{id}", method = RequestMethod.DELETE )
    public void delete( @PathVariable Long id ) {
        // also need to check for child records before deleting
        speakerRepository.deleteById( id );
    }

    @RequestMapping( value = "{id}", method = RequestMethod.PUT )
    public Speaker update( @PathVariable Long id, @RequestBody Speaker speaker ) {
        // because this is a PUT, we expect all the attributes to be passed in
        // A PATCH can be used to only pass what needs to be updated; new to HTTP in 2010
        // TODO: add validation ensuring that all attributes are passed in
        Speaker existingSpeaker = speakerRepository.getOne( id );
        // args: source, target, array of stuff to ignore
        BeanUtils.copyProperties( speaker, existingSpeaker, "speaker_id" );
        return speakerRepository.saveAndFlush( existingSpeaker );
    }

}

