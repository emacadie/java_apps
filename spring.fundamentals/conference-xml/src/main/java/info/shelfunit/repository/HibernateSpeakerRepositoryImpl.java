package info.shelfunit.repository;

import info.shelfunit.model.Speaker;

import java.util.ArrayList;
import java.util.List;

public class HibernateSpeakerRepositoryImpl implements SpeakerRepository {

    public List< Speaker > findAll() {
        List < Speaker > speakers = new ArrayList< Speaker >();
        Speaker speaker = new Speaker();
        speaker.setFirstName( "George" );
        speaker.setLastName( "Washington" );
        speakers.add( speaker );
        return speakers;
    }
}
