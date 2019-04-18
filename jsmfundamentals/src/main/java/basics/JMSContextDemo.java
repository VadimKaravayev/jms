package basics;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSContextDemo {
    public static void main(String[] args) throws NamingException {
        InitialContext context = new InitialContext();
        Queue queue = (Queue) context.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
             JMSContext jmsContext = cf.createContext()) {

            jmsContext.createProducer().send(queue, "For the thicker gravy, add some flour");

            String message = jmsContext.createConsumer(queue).receiveBody(String.class);

            System.out.println("Received message: " + message);
        }
    }
}
