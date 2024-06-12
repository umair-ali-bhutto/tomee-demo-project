package com.ag.main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author umair.ali
 * @version 1.0
 * @since 12-JUN-2024
 * 
 * 
 * 
 *        This servlet listens for HTTP GET requests on the /send URL path. When
 *        a request is received, it sends a message to the JMS queue specified
 *        by the injected Queue resource using the injected
 *        QueueConnectionFactory. The message content is hardcoded as "Hello,
 *        JMS!". If the message sending is successful, it responds with "Message
 *        sent successfully." Otherwise, it responds with an error message.
 * 
 */
@WebServlet("/send")
public class ServletQueueSender extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jms/MyTestConnectionFactory")
	private QueueConnectionFactory connectionFactory;

	@Resource(name = "jms/MyTestQueue")
	private Queue queue;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletQueueSender() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("@@@@ SERVLET CONNECTION FACTORY HIT @@@@");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			// Create JMS connection, session, and message sender
			try {

				QueueConnection connection = connectionFactory.createQueueConnection();
				QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
				QueueSender sender = session.createSender(queue);
				// Create a text message
				TextMessage message = session.createTextMessage("Hello, JMS!");

				// Send the message
				sender.send(message);

				out.println("Message sent successfully.");
			} catch (JMSException e) {
				out.println("Failed to send message: " + e.getMessage());
			}
		} finally {
			out.close();
		}
	}

}
