package jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

@MessageDriven(name = "MyMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/test"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class MyConsumer implements MessageListener {

    private final static Logger LOGGER = Logger.getLogger(MyConsumer.class.toString());

    @Override
    public void onMessage(final Message msg) {
        if (msg instanceof TextMessage) {
            try {
                final String text = ((TextMessage) msg).getText();
                LOGGER.info(() -> "Received: " + text);
            } catch (final JMSException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
