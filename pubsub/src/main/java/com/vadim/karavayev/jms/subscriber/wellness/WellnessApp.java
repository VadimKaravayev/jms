package com.vadim.karavayev.jms.subscriber.wellness;

import com.vadim.karavayev.jms.constant.Topics;
import com.vadim.karavayev.jms.model.Employee;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class WellnessApp {

    public static void main(String[] args) throws NamingException {
        InitialContext initialContext = new InitialContext();
        Topic topic = (Topic) initialContext.lookup(Topics.EMP_TOPIC);

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext()) {

            JMSConsumer consumer = jmsContext.createSharedConsumer(topic, "sharedConsumer");
            JMSConsumer consumer2 = jmsContext.createSharedConsumer(topic, "sharedConsumer");

            for (int i = 0; i < 10; i+=2) {
                Employee employee = consumer.receive().getBody(Employee.class);
                System.out.println("Consumer 1: " + employee.getFirstName());

                Employee employee2 = consumer2.receive().getBody(Employee.class);
                System.out.println("Consumer 2: " + employee2.getFirstName());
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
