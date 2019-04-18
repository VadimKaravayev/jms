package basics;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.soap.Text;
import java.util.Enumeration;

public class QueueBrowserDemo {
    public static void main(String[] args) {
        InitialContext initialContext = null;
        Connection connection = null;
        try {
            initialContext = new InitialContext();
            ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
            connection = cf.createConnection();
            final Session session = connection.createSession();
            final Queue queue = (Queue) initialContext.lookup("queue/myQueue");
            final MessageProducer producer = session.createProducer(queue);
            final TextMessage textMessage1 = session.createTextMessage("I am a creator of my destiny");
            final TextMessage textMessage2 = session.createTextMessage("This wine goes well with poultry");
            producer.send(textMessage1);
            producer.send(textMessage2);
            System.out.printf("Message1 sent: %s\n", textMessage1.getText());

            final QueueBrowser browser = session.createBrowser(queue);
            final Enumeration messagesEnum = browser.getEnumeration();
            while (messagesEnum.hasMoreElements()) {
                TextMessage each = (TextMessage) messagesEnum.nextElement();
                System.out.println("Browsing: " + each.getText());
            }

            final MessageConsumer consumer = session.createConsumer(queue);
            connection.start();
            TextMessage messageReceived = (TextMessage) consumer.receive(5000);
            System.out.printf("First message received: %s\n", messageReceived.getText());
            messageReceived = (TextMessage) consumer.receive(5000);
            System.out.printf("Second message received %s\n", messageReceived.getText());


        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (initialContext != null) {
                try {
                    initialContext.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }   }
}
