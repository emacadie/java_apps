package info.shelfunit.app;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.util.UUID;


public class Send {


    public static String getRandomString() {
	// # UUID hibernateUuid = UUID.randomUUID();
        // # return hibernateUuid.toString();
	return UUID.randomUUID().toString();
        
    }

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) 
	throws java.io.IOException, java.lang.InterruptedException {

	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("localhost");
	Connection connection = factory.newConnection();
	Channel channel = connection.createChannel();

	channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	String message = "Hello World!";
	channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
	System.out.println(" [x] Sent '" + message + "'");

	Thread.sleep(4000);

	message = getRandomString();
	channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
	System.out.println(" [x] Sent '" + message + "'");

	channel.close();
	connection.close();


      
    } // end method main

} // end class
