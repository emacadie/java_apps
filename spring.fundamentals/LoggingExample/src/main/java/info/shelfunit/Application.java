package info.shelfunit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Application implements AppConfig {
	
	private static final Logger logger = LogManager.getLogger( Application.class );
	
	public static void main( String[] args ) {
		logger.info( "Starting application" );
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext( Application.class );
		MessageBean bean = context.getBean( ShowMessageBean.class );
		bean.showMessage( "Test message" );
		context.close();
		logger.info( "Shutting down application" );
	}
}

// I typed in the code, it did not work.
// I copied and pasted from git hub, it works. Why? I have no idea
