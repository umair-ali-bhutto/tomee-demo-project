package com.ag.main;

import java.io.IOException;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ag.util.TomeeLogger;

/**
 * @author umair.ali
 * @version 1.0
 * @since 12-JUN-2024
 * 
 * 
 * 
 *        This servlet listens for HTTP GET requests on the /recieve URL path.
 *        it recieves the message sent to the JMS queue specified by the
 *        injected Queue resource using the injected QueueConnectionFactory.
 * 
 */
@WebServlet("/recieve")
public class ServletQueueReciever extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jms/MyTestConnectionFactory")
	private QueueConnectionFactory connectionFactory;

	@Resource(name = "jms/MyTestQueue")
	private Queue queue;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("@@@@ SERVLET CONNECTION FACTORY RECIEVER HIT @@@@");

		Connection connection = null;
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageConsumer consumer = session.createConsumer(queue);

			TomeeLogger.logInfo("Waiting for messages...");

			while (true) {
				Message message = consumer.receive();
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					response.getWriter().append("Received message: " + textMessage.getText());
					TomeeLogger.logInfo("Received message: " + textMessage.getText());
				} else {
					response.getWriter().append("Received message: " + message);
					TomeeLogger.logInfo("Received message: " + message);
				}
				Thread.sleep(5000);
			}
		} catch (Exception e) {
			TomeeLogger.logError(getClass(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
