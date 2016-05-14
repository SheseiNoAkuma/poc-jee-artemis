package jms;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/")
public class MyServlet extends HttpServlet {

    @EJB
    MyProducer myProducer;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final String text = "Hello, JMS!";
        myProducer.enqueue(text);
        resp.getWriter().write("Published! check output of the consumer: " + text + "\n");
    }
}
