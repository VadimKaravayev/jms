package messagestructure;

import constant.Queues;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RequestReplyDemo {
    public static void main(String[] args) throws NamingException {
        InitialContext initialContext = new InitialContext();
        Queue requestQueue = (Queue) initialContext.lookup(Queues.REQUEST_QUEUE);
        Queue replyQueue = (Queue) initialContext.lookup(Queues.REPLY_QUEUE);

        try (JMSContext jmsContext = new ActiveMQConnectionFactory().createContext()) {

            JMSProducer requestProducer = jmsContext.createProducer();
            requestProducer.send(requestQueue,"This wine goes well with poultry");

            JMSConsumer requestConsumer = jmsContext.createConsumer(requestQueue);
            System.out.println(requestConsumer.receiveBody(String.class));

            JMSProducer replyProducer = jmsContext.createProducer();
            replyProducer.send(replyQueue, "You are awesome");

            JMSConsumer replyConsumer = jmsContext.createConsumer(replyQueue);
            System.out.println(replyConsumer.receiveBody(String.class));
        }
    }
}
