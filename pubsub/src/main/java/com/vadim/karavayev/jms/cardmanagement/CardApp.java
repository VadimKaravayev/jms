package com.vadim.karavayev.jms.cardmanagement;

import com.vadim.karavayev.jms.cardmanagement.model.Purchase;
import com.vadim.karavayev.jms.constant.Topics;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CardApp {

    public static void main(String[] args) throws NamingException {

        final InitialContext initialContext = new InitialContext();
        final Topic topic = (Topic) initialContext.lookup(Topics.CREDIT_CARD);


        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
         JMSContext jmsContext = connectionFactory.createContext()) {

            JMSProducer producer = jmsContext.createProducer();
            Purchase purchase = new Purchase(123, "Walmart", 345);
            producer.send(topic, purchase);
            System.out.println("Purchase made");
            Purchase purchase2 = new Purchase(124, "Suspicious shop", 20000);
            producer.send(topic, purchase2);
            System.out.println("Purchase made");
        }
    }
}
