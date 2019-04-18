package com.vadim.karavayev.jms.cardmanagement;

import com.vadim.karavayev.jms.cardmanagement.model.Purchase;
import com.vadim.karavayev.jms.constant.Topics;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.concurrent.TimeUnit;

public class AlertsApp {

    public static void main(String[] args) throws NamingException {

        InitialContext initialContext = new InitialContext();
        Topic topic = (Topic) initialContext.lookup(Topics.CREDIT_CARD);

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            JMSContext jmsContext = connectionFactory.createContext()) {

            jmsContext.setClientID("cardAlertsApp");
            JMSConsumer durableConsumer = jmsContext.createDurableConsumer(topic, "subscription1");
            durableConsumer.close();

            TimeUnit.SECONDS.sleep(3);

            durableConsumer = jmsContext.createDurableConsumer(topic, "subscription1");

            Purchase purchase = durableConsumer.receive().getBody(Purchase.class);

            System.out.println("Sending sms to phone: " + purchase.getAmount() + " was spent at " + purchase.getVendor());

        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
