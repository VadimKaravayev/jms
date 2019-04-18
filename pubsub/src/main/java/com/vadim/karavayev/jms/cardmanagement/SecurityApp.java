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

public class SecurityApp {

    public static void main(String[] args) throws NamingException {

        InitialContext initialContext = new InitialContext();
        Topic topic = (Topic) initialContext.lookup(Topics.CREDIT_CARD);

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            JMSContext jmsContext = connectionFactory.createContext()) {

            JMSConsumer sharedConsumer1 = jmsContext.createSharedConsumer(topic, "sharedConsumer");
            JMSConsumer sharedConsumer2 = jmsContext.createSharedConsumer(topic, "sharedConsumer");

            Purchase purchase1 = sharedConsumer1.receive().getBody(Purchase.class);
            checkPurchase(purchase1);

            Purchase purchase2 = sharedConsumer2.receive().getBody(Purchase.class);
            checkPurchase(purchase2);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static void checkPurchase(Purchase purchase) {
        if (purchase != null) {
            if (purchase.getAmount() > 10000) {
                System.out.println("Alarm!");
            } else {
                System.out.println("No worries.");
            }
        }
    }
}
