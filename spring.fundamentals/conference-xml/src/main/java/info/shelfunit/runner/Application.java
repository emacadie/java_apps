package info.shelfunit.runner;

import info.shelfunit.service.SpeakerService;
import info.shelfunit.service.SpeakerServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    /*
     * Still giving error about  java release version 5 not supported
     * So in command line go to directory
     * mvn exec:java -Dexec.mainClass="info.shelfunit.runner.Application"
     * Or in IntelliJ right-click the pom.xml on the side and choose Maven|Reload Project
     */

    public static void main( String args[] ) {

        ApplicationContext appContext = new ClassPathXmlApplicationContext( "applicationContext.xml" );

        // This can be commented out after adding appContext
        // SpeakerService service = new SpeakerServiceImpl();

        SpeakerService service = appContext.getBean( "speakerService", SpeakerService.class );

        System.out.println( "Here is the result: " + service.findAll().get( 0 ).getFirstName() );
    }
}
