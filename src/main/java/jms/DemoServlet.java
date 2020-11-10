package jms;

import javax.annotation.Resource;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebServlet("/HelloWorldMDBServletClient")
public class DemoServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DemoServlet.class.toString());

    private static final int MSG_COUNT = 5;

    //FIXME dont know why but this does not work..
//    @Resource(lookup = "java:/jms/FINAMQCF")
//    private static ConnectionFactory connectionFactory;

    @Resource(lookup = "java:/" + Constants.QUEUE)
    private Queue queue;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        ConnectionFactory connectionFactory;
        try {
            Context ctx = new InitialContext();
            connectionFactory = (ConnectionFactory) ctx.lookup(Constants.CONNECTION_FACTORY_JNDI);
        } catch (NamingException e) {
            LOGGER.throwing("DemoServlet", "doGet", e);
            return;
        }

        resp.setContentType("text/html");
        try (PrintWriter out = resp.getWriter(); Connection connection = connectionFactory.createConnection()) {
            out.write("<h1>Quickstart: Example demonstrates the use of <strong>JMS 2.0</strong> and <strong>EJB 3.2 Message-Driven Bean</strong> in JBoss EAP.</h1>");
            final Destination destination = queue;

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);

            out.write("<p>Sending messages to <em>" + destination + "</em></p>");
            out.write("<h2>The following messages will be sent to the destination:</h2>");
            for (int i = 0; i < MSG_COUNT; i++) {
                String text = "This is message " + (i + 1);
                TextMessage message = session.createTextMessage();
                message.setText(text);
                producer.send(message);
                out.write("Message (" + message.getJMSMessageID() + "): " + text + "</br>");
            }
            out.write("<p><i>Go to your JBoss EAP server console or server log to see the result of messages processing.</i></p>");
        } catch (JMSException | IOException e) {
            e.printStackTrace();
        }
    }
}
