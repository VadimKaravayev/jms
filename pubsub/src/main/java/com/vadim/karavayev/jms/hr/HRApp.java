package com.vadim.karavayev.jms.hr;

import com.vadim.karavayev.jms.constant.Topics;
import com.vadim.karavayev.jms.model.Employee;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class HRApp {
    public static void main(String[] args) throws NamingException {
        InitialContext initialContext = new InitialContext();
        Topic topic = (Topic) initialContext.lookup(Topics.EMP_TOPIC);

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            JMSContext jmsContext = connectionFactory.createContext()) {

            Employee employee = new Employee(123, "Jackob", "Rosenkranz", "Jewlery master",
                "kranz@mail.com", "80508903456");
            for (int i = 0; i < 10; i++) {
                jmsContext.createProducer().send(topic, employee);
                System.out.println("Message sent");
            }

        }
    }
}
