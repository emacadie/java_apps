package info.shelfunit;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Application implements AppConfig {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
		MessageBean bean = context.getBean(ShowMessageBean.class);
		bean.showMessage("Test message");
		context.close();
	}
}

// I typed in the code, it did not work.
// I copied and pasted from git hub, it works. Why? I have no idea
