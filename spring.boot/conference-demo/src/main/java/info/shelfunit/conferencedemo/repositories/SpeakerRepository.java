package info.shelfunit.conferencedemo.repositories;

import info.shelfunit.conferencedemo.models.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeakerRepository extends JpaRepository< Speaker, Long > {
}
