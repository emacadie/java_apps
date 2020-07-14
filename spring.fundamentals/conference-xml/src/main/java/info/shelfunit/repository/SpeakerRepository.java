package info.shelfunit.repository;

import info.shelfunit.model.Speaker;

import java.util.List;

public interface SpeakerRepository {
    List< Speaker > findAll();
}
