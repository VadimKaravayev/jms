package com.vadim.karavayev.jms.subscriber.payroll;

import com.vadim.karavayev.jms.constant.Topics;
import com.vadim.karavayev.jms.model.Employee;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PayrollApp {

    public static void main(String[] args) throws NamingException {
        InitialContext initialContext = new InitialContext();
        Topic topic = (Topic) initialContext.lookup(Topics.EMP_TOPIC);

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext()) {

            JMSConsumer consumer = jmsContext.createConsumer(topic);
            Employee employee = consumer.receive().getBody(Employee.class);
            System.out.println(employee.getFirstName());

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
