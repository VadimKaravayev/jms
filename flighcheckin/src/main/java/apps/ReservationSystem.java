package apps;

import apps.listener.CheckInRequestListener;
import constants.Queues;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.concurrent.TimeUnit;

public class ReservationSystem {

    public static void main(String[] args) throws NamingException {

        InitialContext initialContext = new InitialContext();
        Queue requestQueue = (Queue) initialContext.lookup(Queues.REQUEST_QUEUE);

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            JMSContext jmsContext = connectionFactory.createContext()) {

            JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
            JMSConsumer consumer2 = jmsContext.createConsumer(requestQueue);

            for (int i = 0; i < 5; i++) {
                System.out.println("Consumer " + consumer.receive());
                System.out.println("Consumer2" + consumer2.receive());
            }

            //consumer.setMessageListener(new CheckInRequestListener());

            //TimeUnit.SECONDS.sleep(10);

        }
    }
}
