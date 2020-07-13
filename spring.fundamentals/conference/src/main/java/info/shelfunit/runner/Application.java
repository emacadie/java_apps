package info.shelfunit.runner;

import info.shelfunit.info.shelfunit.service.SpeakerService;
import info.shelfunit.info.shelfunit.service.SpeakerServiceImpl;

public class Application {
    /*
     * Still giving error about  java release version 5 not supported
     * So in command line go to directory
     * mvn exec:java -Dexec.mainClass="info.shelfunit.runner.Application"
     * Or in IntelliJ right-click the pom.xml on the side and choose Maven|Reload Project
     */

    public static void main( String args[] ) {
        SpeakerService service = new SpeakerServiceImpl();
        System.out.println( "Here is the result: " + service.findAll().get( 0 ).getFirstName() );
    }
}
