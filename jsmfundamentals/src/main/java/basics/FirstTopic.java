package basics;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstTopic {
    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext = new InitialContext();
        Topic topic = (Topic) initialContext.lookup("topic/myTopic");
        ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
        Connection connection = cf.createConnection();
        Session session = connection.createSession();
        MessageProducer producer = session.createProducer(topic);
        MessageConsumer consumer1 = session.createConsumer(topic);
        MessageConsumer consumer2 = session.createConsumer(topic);
        final TextMessage textMessage = session.createTextMessage("Hello Dolly");
        producer.send(textMessage);
        connection.start();
        final TextMessage message1 = (TextMessage) consumer1.receive();
        System.out.printf("Consumer 1 message: %s\n", message1.getText());
        final TextMessage message2 = (TextMessage) consumer2.receive();
        System.out.printf("Consumer 2 message %s\n", message2.getText());
        connection.close();
        initialContext.close();



    }
}
