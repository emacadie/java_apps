package info.shelfunit.app

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

import groovy.mock.interceptor.MockFor

import spock.lang.Specification

class RecvTest extends Specification {
   
    def "first test"() {
        
        def delivery = Stub( QueueingConsumer.Delivery )
        String message = "ldld"
        def x = "ok".getBytes()
        delivery.getBody() >> x
        // setup:
            
            println("message: $message")
        
        when:
            println( "Here is delivery.getBody(): ${delivery.getBody()}" )
            def stringOutput = new String( delivery.getBody() )
            println( "Here is stringOutput: ${stringOutput}" )
            // message = delivery.getBody()
            println( "message in when block: $message" )
        then:
            // (1..5) * delivery.getBody()
            stringOutput == "ok"
       
    }
   
  
    
} // end class RecvTest 

