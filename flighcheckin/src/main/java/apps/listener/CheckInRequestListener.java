package apps.listener;

import constants.Queues;
import model.Passenger;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CheckInRequestListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;




        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext()) {

            Passenger passenger = objectMessage.getBody(Passenger.class);
            System.out.println("Checking in the follow passenger");
            System.out.println(passenger);

            InitialContext initialContext = new InitialContext();
            Queue replyQueue = (Queue) initialContext.lookup(Queues.REPLY_QUEUE);

            JMSProducer producer = jmsContext.createProducer();
            MapMessage replyMessage = jmsContext.createMapMessage();
            replyMessage.setBoolean("reserved", true);
            producer.send(replyQueue, replyMessage);

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
