package info.shelfunit.service;

import info.shelfunit.model.Speaker;
import info.shelfunit.repository.SpeakerRepository;

import java.util.List;

public class SpeakerServiceImpl implements SpeakerService {

    public SpeakerServiceImpl() {
        System.out.println( "In SpeakerServiceImpl constructor with no args" );
    }

    public SpeakerServiceImpl( SpeakerRepository argRepository ) {
        System.out.println( "In SpeakerServiceImpl constructor with arg SpeakerRepository" );
        this.repository = argRepository;
    }

    // private SpeakerRepository repository = new HibernateSpeakerRepositoryImpl();

    public void setSpeakerRepository( SpeakerRepository argRepository ) {
        System.out.println( "In SpeakerServiceImpl.setSpeakerRepository" );
        this.repository = argRepository;
    }

    private SpeakerRepository repository;

    public List< Speaker > findAll() {
        return repository.findAll();
    }
}
