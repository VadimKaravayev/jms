package hm.clinicals;

import hm.model.Patient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ClinicalsApp {
    public static void main(String[] args) throws NamingException {
        InitialContext initialContext = new InitialContext();
        Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");
        Queue replyQueue = (Queue) initialContext.lookup("queue/replyQueue");

        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
             JMSContext jmsContext = cf.createContext()) {

            JMSProducer producer = jmsContext.createProducer();

            //ObjectMessage objectMessage = jmsContext.createObjectMessage();
            Patient patient = new Patient(123, "Bob", "United Health",
                    30, 500);
            //objectMessage.setObject(patient);
            //producer.send(requestQueue, objectMessage);
            producer.send(requestQueue, patient);
            JMSConsumer consumer = jmsContext.createConsumer(replyQueue);
            MapMessage replyMessage = (MapMessage) consumer.receive(30000);
            //MapMessage replyMessage = consumer.receiveBody(MapMessage.class, 30000);
            System.out.println("Patient eligibility: " + replyMessage.getBoolean("eligible"));


        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

