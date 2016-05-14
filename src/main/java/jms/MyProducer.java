package jms;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Stateless
@LocalBean
public class MyProducer {

    @Resource(mappedName = "java:/queue/test")
    Queue testQueue;
    @Inject
    JMSContext jmsContext;

    public void enqueue(final String text) {
        jmsContext.createProducer().send(testQueue, text);
    }
}
