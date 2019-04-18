package com.vadim.karavayev.jms.subscriber.security;

import com.vadim.karavayev.jms.constant.Topics;
import com.vadim.karavayev.jms.model.Employee;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.concurrent.TimeUnit;

public class SecurityApp {

    public static void main(String[] args) throws NamingException {
        InitialContext initialContext = new InitialContext();
        Topic topic = (Topic) initialContext.lookup(Topics.EMP_TOPIC);

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext()) {
            jmsContext.setClientID("securityApp");

            JMSConsumer consumer = jmsContext.createDurableConsumer(topic, "subscription1");
            consumer.close();

            TimeUnit.SECONDS.sleep(10);

            consumer = jmsContext.createDurableConsumer(topic, "subscription1");
            Employee employee = consumer.receive().getBody(Employee.class);
            System.out.println(employee.getLastName());

            consumer.close();
            jmsContext.unsubscribe("subscription1");

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
