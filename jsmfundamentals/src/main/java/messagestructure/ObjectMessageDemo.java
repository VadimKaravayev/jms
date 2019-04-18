package messagestructure;

import bean.Order;
import bean.TypeOfGoods;
import constant.Queues;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;

public class ObjectMessageDemo {
    public static void main(String[] args) throws NamingException, JMSException {
        final InitialContext initialContext = new InitialContext();
        final Queue queue = (Queue) initialContext.lookup(Queues.REQUEST_QUEUE);

        try (JMSContext jmsContext = new ActiveMQConnectionFactory().createContext()) {
            final JMSProducer producer = jmsContext.createProducer();
            //final ObjectMessage objectMessage = jmsContext.createObjectMessage();
            //final Order order = new Order(1, TypeOfGoods.COUNTABLE, 5);
            //objectMessage.setObject(order);
            //producer.send(queue, objectMessage);
            //producer.send(queue, order);
            producer.send(queue, new Order(2, TypeOfGoods.LIQUIDS, 89));

            //final ObjectMessage receivedObject = (ObjectMessage) jmsContext.createConsumer(queue).receive();
            //final Order receivedOrder = (Order) receivedObject.getObject();

            final Order order1 = jmsContext.createConsumer(queue).receiveBody(Order.class);

            //System.out.println(receivedOrder);
            System.out.println(order1);

        }
    }
}
