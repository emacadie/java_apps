package info.shelfunit.runner;

import info.shelfunit.info.shelfunit.service.SpeakerService;
import info.shelfunit.info.shelfunit.service.SpeakerServiceImpl;
import info.shelfunit.repository.HibernateSpeakerRepositoryImpl;
import info.shelfunit.repository.SpeakerRepository;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean( name = "speakerService" )
    @Scope( value = BeanDefinition.SCOPE_SINGLETON )
    public SpeakerService getSpeakerService() {
        /*
        // this breaks with constructor injection
        SpeakerServiceImpl service = new SpeakerServiceImpl();
        service.setRepository( this.getSpeakerRepository() );
        */
        SpeakerServiceImpl service = new SpeakerServiceImpl( this.getSpeakerRepository() );

        return service;
    }

    @Bean( name = "speakerRepository" )
    public SpeakerRepository getSpeakerRepository() {
        return new HibernateSpeakerRepositoryImpl();
    }

}
