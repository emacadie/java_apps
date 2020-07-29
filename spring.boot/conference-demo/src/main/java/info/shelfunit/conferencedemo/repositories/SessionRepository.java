package info.shelfunit.conferencedemo.repositories;

import info.shelfunit.conferencedemo.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
// Long refers to primary key
public interface SessionRepository extends JpaRepository< Session, Long > {
}
