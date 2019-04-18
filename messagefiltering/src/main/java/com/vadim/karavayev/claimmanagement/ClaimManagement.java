package com.vadim.karavayev.claimmanagement;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ClaimManagement {
    public static void main(String[] args) throws NamingException {
        final InitialContext initialContext = new InitialContext();
        final Queue claimQueue = (Queue) initialContext.lookup("queue/claimQueue");

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        final JMSContext jmsContext = connectionFactory.createContext()) {

            final JMSProducer producer = jmsContext.createProducer();
            String selector = "insuranceProvider IN ('Blue Cross', 'American') AND doctorName LIKE 'H%' AND multipleOf10=0";
            final JMSConsumer consumer = jmsContext
                    .createConsumer(claimQueue, selector);
            final ObjectMessage objectMessage = jmsContext.createObjectMessage();
            final Claim claim = new Claim(1, "Henry", "Neuro",
                    "American", 1000d);
            objectMessage.setObject(claim);
            objectMessage.setStringProperty("insuranceProvider", claim.getInsuranceProvider());
            objectMessage.setStringProperty("doctorName", claim.getDoctorName());
            objectMessage.setIntProperty("multipleOf10", (int) (claim.getClaimAmount() % 10));
            producer.send(claimQueue, objectMessage);

            final Claim receivedClaim = consumer.receiveBody(Claim.class, 3000);
            try {
                System.out.println("Doctor name: " + receivedClaim.getDoctorName());
                System.out.println("Insurance provider: " + receivedClaim.getInsuranceProvider());
                System.out.println("Claim amount: " + receivedClaim.getClaimAmount());
            } catch (NullPointerException ex) {
                System.out.println("No desired message was consumed");
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
