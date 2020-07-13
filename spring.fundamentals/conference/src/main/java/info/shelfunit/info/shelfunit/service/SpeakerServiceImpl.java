package info.shelfunit.info.shelfunit.service;

import info.shelfunit.model.Speaker;
import info.shelfunit.repository.HibernateSpeakerRepositoryImpl;
import info.shelfunit.repository.SpeakerRepository;

import java.util.List;

public class SpeakerServiceImpl implements SpeakerService {

    private SpeakerRepository repository = new HibernateSpeakerRepositoryImpl();

    public List< Speaker > findAll() {
        return repository.findAll();
    }
}
