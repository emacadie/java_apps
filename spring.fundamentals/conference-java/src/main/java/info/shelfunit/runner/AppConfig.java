package info.shelfunit.runner;

import info.shelfunit.util.CalendarFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Calendar;

@Configuration
@ComponentScan( { "info.shelfunit" } )
public class AppConfig {

    @Bean( name = "cal" )
    public CalendarFactory calFactory() {
        CalendarFactory factory = new CalendarFactory();
        factory.addDays( 2 );
        return factory;
    }

    @Bean
    public Calendar cal() throws Exception {
        return calFactory().getObject();
    }

    /*
    @Bean( name = "speakerService" )
    @Scope( value = BeanDefinition.SCOPE_SINGLETON )
    public SpeakerService getSpeakerService() {

        // this breaks with constructor injection
        // SpeakerServiceImpl service = new SpeakerServiceImpl();
        // service.setRepository( this.getSpeakerRepository() );


        // constructor injection
        // SpeakerServiceImpl service = new SpeakerServiceImpl( this.getSpeakerRepository() );

        // this is all we need with calling @Autowired on the setter
        SpeakerServiceImpl service = new SpeakerServiceImpl();
        return service;
    }

    @Bean( name = "speakerRepository" )
    public SpeakerRepository getSpeakerRepository() {
        return new HibernateSpeakerRepositoryImpl();
    }
    */

    // now with @ComponentScan and full autowiring using @Component and @Repository on the classes
    // we can still define scope at class level
}
