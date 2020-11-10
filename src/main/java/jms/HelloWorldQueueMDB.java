package jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jboss.ejb3.annotation.ResourceAdapter;

import java.util.logging.Logger;

@MessageDriven(name = "DemoMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/" + Constants.QUEUE),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")})
@ResourceAdapter(value= Constants.CONNECTION_FACTORY_NAME)
public class HelloWorldQueueMDB implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(HelloWorldQueueMDB.class.toString());

    public void onMessage(Message rcvMessage) {
        TextMessage msg;
        try {
            if (rcvMessage instanceof TextMessage) {
                msg = (TextMessage) rcvMessage;
                LOGGER.info("Received Message "+ msg.getJMSMessageID() +" from queue: " + msg.getText());
            } else {
                LOGGER.warning("Message of wrong type: " + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            LOGGER.throwing("HelloWorldQueueMDB", "onMessage", e);
        }
    }
}
