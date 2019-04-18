package com.vadim.karavayev.jms.producer;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageProducer {

    public static void main(String[] args) {

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            JMSContext jmsContext = connectionFactory.createContext(JMSContext.SESSION_TRANSACTED)) {

            InitialContext initialContext = new InitialContext();
            Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");

            JMSProducer producer = jmsContext.createProducer();
            producer.send(requestQueue, "Message 1");
            producer.send(requestQueue, "Message 2");
            jmsContext.commit();

            //if something wrong happens
            //jmsContext.rollback();


        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
