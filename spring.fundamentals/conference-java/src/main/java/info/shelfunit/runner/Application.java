package info.shelfunit.runner;

import info.shelfunit.service.SpeakerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    /*
     * Still giving error about  java release version 5 not supported
     * So in command line go to directory
     * mvn exec:java -Dexec.mainClass="info.shelfunit.runner.Application"
     * Or in IntelliJ right-click the pom.xml on the side and choose Maven|Reload Project
     */

    public static void main( String args[] ) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext( AppConfig.class );
        // SpeakerService service = new SpeakerServiceImpl();
        SpeakerService service1 = appContext.getBean( "speakerService", SpeakerService.class );
        SpeakerService service2 = appContext.getBean( "speakerService", SpeakerService.class );
        System.out.println( "address for service1: " + service1 );
        System.out.println( "address for service2: " + service2 ); // should be same if singleton
        System.out.println( "Here is the result in Application.main: " + service1.findAll().get( 0 ).getFirstName() );
    }
}
