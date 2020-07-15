package info.shelfunit.service;

import info.shelfunit.model.Speaker;
import info.shelfunit.repository.SpeakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service( "speakerService" )
@Profile( "dev" )
public class SpeakerServiceImpl implements SpeakerService {

    private SpeakerRepository repository;

    public SpeakerServiceImpl() {
        System.out.println( "In SpeakerServiceImpl no-arg constructor" );
    }

    public SpeakerServiceImpl( SpeakerRepository speakerRepository ) {
        this.repository = speakerRepository;
        System.out.println( "In SpeakerServiceImpl constructor w/arg SpeakerRepository" );
    }

    @PostConstruct
    private void initialize() {
        System.out.println( "In SpeakerServiceImpl.initialize; this should be after constructors" );
    }

    @Autowired // will inject when we call no-args constructor
    public void setRepository( SpeakerRepository repository ) {
        this.repository = repository;
        System.out.println( "In SpeakerServiceImpl.setRepository" );
    }

    public List< Speaker > findAll() {
        return repository.findAll();
    }
}
