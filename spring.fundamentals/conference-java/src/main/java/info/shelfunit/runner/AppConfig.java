package info.shelfunit.runner;

import info.shelfunit.info.shelfunit.service.SpeakerService;
import info.shelfunit.info.shelfunit.service.SpeakerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean( name = "speakerService" )
    public SpeakerService getSpeakerService() {
        return new SpeakerServiceImpl();
    }

}
