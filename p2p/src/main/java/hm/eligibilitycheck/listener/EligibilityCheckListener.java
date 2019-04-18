package hm.eligibilitycheck.listener;

import hm.model.Patient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EligibilityCheckListener implements MessageListener {

    private static final String BLUE_CROSS = "Blue Cross Blue Shield";
    private static final String UNITED_HEALTH = "United Health";

    @Override
    public void onMessage(Message message) {
        System.out.println("In the listener");
        ObjectMessage objectMessage = (ObjectMessage) message;
        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext()) {

            InitialContext initialContext = new InitialContext();
            Queue replyQueue = (Queue) initialContext.lookup("queue/replyQueue");

            MapMessage replyMessage = jmsContext.createMapMessage();
            //Patient patient = (Patient) objectMessage.getObject();
            Patient patient = objectMessage.getBody(Patient.class);
            System.out.println(patient);

            String insuranceProvider = patient.getInsuranceProvider();
            System.out.println(insuranceProvider);

            if (insuranceProvider.equals(BLUE_CROSS) || insuranceProvider.equals(UNITED_HEALTH)) {
                System.out.println("Copay " + patient.getCopay());
                if (patient.getCopay() < 40 && patient.getAmountToBePaid() < 1000) {
                    replyMessage.setBoolean("eligible", true);
                }
            } else {
                replyMessage.setBoolean("eligible", false);
            }
            JMSProducer producer = jmsContext.createProducer();
            producer.send(replyQueue, replyMessage);

        } catch (JMSException | NamingException e) {
            e.printStackTrace();
        }
    }
}
