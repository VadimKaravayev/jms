package messagestructure;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessagePriority {
    public static void main(String[] args) throws NamingException {
        final InitialContext context = new InitialContext();
        final Queue queue = (Queue) context.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory fc = new ActiveMQConnectionFactory();
             JMSContext jmsContext = fc.createContext()) {
            final JMSProducer producer = jmsContext.createProducer();
            String[] messages = new String[3];
            messages[0] = "Message one";
            messages[1] = "Message two";
            messages[2] = "Message three";

            producer.setPriority(3);
            producer.send(queue, messages[0]);

            producer.setPriority(1);
            producer.send(queue, messages[1]);

            producer.setPriority(9);
            producer.send(queue, messages[2]);

            final JMSConsumer consumer = jmsContext.createConsumer(queue);

            for (int i = 0; i < messages.length; i++) {
                System.out.println(consumer.receiveBody(String.class));
            }
        }
    }
}
