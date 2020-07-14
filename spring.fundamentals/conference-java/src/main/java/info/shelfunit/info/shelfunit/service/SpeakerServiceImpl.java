package info.shelfunit.info.shelfunit.service;

import info.shelfunit.model.Speaker;
import info.shelfunit.repository.HibernateSpeakerRepositoryImpl;
import info.shelfunit.repository.SpeakerRepository;

import java.util.List;

public class SpeakerServiceImpl implements SpeakerService {

    private SpeakerRepository repository;


    public SpeakerServiceImpl( SpeakerRepository speakerRepository ) {
        this.repository = speakerRepository;
        System.out.println( "In SpeakerServiceImpl constructor" );
    }

    public void setRepository( SpeakerRepository repository ) {
        this.repository = repository;
        System.out.println( "In SpeakerServiceImpl.setRepository" );
    }

    public List< Speaker > findAll() {
        return repository.findAll();
    }
}
