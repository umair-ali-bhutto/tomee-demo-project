package com.ag.main;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
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

import com.ag.entity.AuditLog;
import com.ag.entity.Message;
import com.ag.service.AuditLogService;
import com.ag.service.MessageService;
import com.ag.util.TomeeLogger;

/**
 * @author umair.ali
 * @version 1.0
 * @since 13-JUN-2024
 * 
 * 
 * 
 *        This servlet listens for HTTP GET requests on the /send URL path. When
 *        a request is received, it sends a message to the JMS queue specified
 *        by the injected Queue resource using the injected
 *        QueueConnectionFactory. If the message sending is successful, it
 *        responds with "Message sent successfully." Otherwise, it responds with
 *        an error message.
 * 
 * 
 */
@WebServlet("/send")
public class ServletQueueSender extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jms/MyTestConnectionFactory")
	private QueueConnectionFactory connectionFactory;

	@Resource(name = "jms/MyTestQueue")
	private Queue queue;

//	@EJB
//	private AuditLogService auditLogService;
//
//	@EJB
//	private MessageService messageService;
	
    @EJB(lookup = "java:global/SenderApp/AuditLogServiceSender!com.ag.service.AuditLogService")
    private AuditLogService auditLogService;

    @EJB(lookup = "java:global/SenderApp/MessageServiceSender!com.ag.service.MessageService")
    private MessageService messageService;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("code", "0001");
		response.sendRedirect(request.getContextPath() + "/");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TomeeLogger.logInfo("@@@@ SENDING SMS @@@@");
		String ipAddress = request.getHeader("IP");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		try {

			String message = request.getParameter("message");

			if (message != null && !message.trim().equals("")) {
				QueueConnection connection = connectionFactory.createQueueConnection();
				QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
				QueueSender sender = session.createSender(queue);

				TextMessage senderMessage = session.createTextMessage(message);
				sender.send(senderMessage);

				TomeeLogger.logInfo("Message Sent.");
				request.setAttribute("code", "0000");
				request.setAttribute("message", "Message Sent Successfully.");
			} else {
				request.setAttribute("code", "9991");
				request.setAttribute("message", "Message Cannot Be Empty.");
			}
		} catch (Exception e) {
			request.setAttribute("code", "9999");
			request.setAttribute("message", "Something Went Wrong.");
			TomeeLogger.logError(getClass(), e);
		} finally {
			AuditLog log = new AuditLog();
			log.setEntryDate(new Timestamp(new Date().getTime()));
			log.setRequestIp(ipAddress);
			log.setRequestMode("SEND");
			log.setRequest("MESSAGE: " + request.getParameter("message"));
			log.setResponse("{\"code\":\"" + request.getAttribute("code") + "\",\"message\":\""
					+ request.getAttribute("message") + "\"}");
			int logId = auditLogService.insertLog(log);

			Message msg = new Message();
			msg.setAuditLogId(logId);
			msg.setEntryDate(new Timestamp(new Date().getTime()));
			msg.setMessage(request.getParameter("message"));
			msg.setIsSent(request.getAttribute("code").toString().equals("0000") ? 1 : 0);
			msg.setIsRecieved(0);
			msg.setRecievedDate(null);
			messageService.insert(msg);

			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");

			String htmlContent = "<html lang=\"en\">\n" + "\n" + "	<head>\n" + "		<meta charset=\"UTF-8\">\n"
					+ "		<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
					+ "		<title>SENDER</title>\n"
					+ "		<link rel=\"stylesheet\" href=\"assets/css/style.css\">\n"
					+ "		<link rel=\"shortcut icon\" href=\"assets/images/favicon.ico\" type=\"image/x-icon\">\n"
					+ "	</head>\n" + "\n" + "	<body>\n" + "		<main id=\"main\">\n" + "			<header>\n"
					+ "				<img src=\"assets/images/logo.png\" alt=\"Logo\" width=\"5%\">\n"
					+ "			</header>\n" + "\n" + "			<h2>Send Message</h2>\n" + "\n"
					+ "			<form id=\"myForm\" method=\"post\" onsubmit=\"submitForm()\" autocomplete=\"off\">\n"
					+ "				<label for=\"message\">Enter Message:</label>\n"
					+ "				<input type=\"text\" id=\"message\" name=\"message\" title=\"Please Enter Message\"\n"
					+ "					placeholder=\"Please Enter Message\" required>\n"
					+ "				<button type=\"submit\">Send</button>\n" + "			</form>\n"
					+ "		</main>\n" + "		<div id=\"spinnerContainer\" class=\"spinnerContainer\">\n"
					+ "			<div id=\"spinner\" class=\"spinner\"></div>\n"
					+ "			<div id=\"spinnerMessage\" class=\"spinnerMessage\">Sending Message....</div>\n"
					+ "		</div>\n" + "\n" + "		<div id=\"myModal\" class=\"modal\">\n"
					+ "			<div class=\"modal-content\">\n" + "<p>" + request.getAttribute("message")
					+ "</p>		<button onclick=\"closeModal()\">Close</button>	</div></div><script type=\"text/javascript\">document.addEventListener('DOMContentLoaded', function () {document.getElementById('spinnerContainer').style.display = 'none';document.getElementById('main').style.display = 'none';		document.getElementById('myModal').style.display = 'block';});	function submitForm() {		var myForm = document.getElementById('myForm');		myForm.action = 'generatePayment';		myForm.method = 'post';		document.getElementById('main').style.display = 'none';		document.getElementById('spinnerContainer').style.display = 'block';document.getElementById('myModal').style.display = 'block';		myForm.submit();	}</script></body><script type=\"text/javascript\">	function closeModal() {		document.getElementById('myModal').style.display = 'none';		document.getElementById('spinnerContainer').style.display = 'none';		document.getElementById('main').style.display = 'block';		document.getElementById('myForm').reset();	window.location.href = \"\"+window.location.pathname.replace('generatePayment', '')+\"\";}</script></html>";
			response.getWriter().write(htmlContent);

			/**
			 * CAN DO THESE BELOW METHODS ALSO BUT WHEN USING INTRANET THESE CAUSE PROBLEMS
			 * AND SOMETIMES INTERNET IS DISALLOWED SO ABOVE METHOD WILL STILL WORK
			 */

			// response.sendRedirect("default.jsp");

			// RequestDispatcher dispatcher = request.getRequestDispatcher("/");
			// dispatcher.forward(request, response);

		}
	}

}
