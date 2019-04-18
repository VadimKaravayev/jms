package apps;

import constants.Queues;
import model.Passenger;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CheckInApp {

    public static void main(String[] args) throws NamingException {
        InitialContext initialContext = new InitialContext();
        Queue requestQueue = (Queue) initialContext.lookup(Queues.REQUEST_QUEUE);
        Queue replyQueue = (Queue) initialContext.lookup(Queues.REPLY_QUEUE);


        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            JMSContext jmsContext = connectionFactory.createContext()) {

            JMSProducer producer = jmsContext.createProducer();

            for (int i = 0; i < 20; i++) {
                producer.send(requestQueue, new Passenger("Jackob", "Rosenfeld",
                        "resen@mail.com", "80506092669"));
            }

            //JMSConsumer consumer = jmsContext.createConsumer(replyQueue);
            //MapMessage replyMessage = (MapMessage) consumer.receive(30000);
            //System.out.println("Passenger reserved: " + replyMessage.getBoolean("reserved"));
        }
    }
}
