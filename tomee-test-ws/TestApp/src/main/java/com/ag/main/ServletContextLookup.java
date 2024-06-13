package com.ag.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.ag.util.TomeeLogger;

/**
 * @author umair.ali
 * @version 1.0
 * @since 12-JUN-2024
 * 
 *        Servlet implementation class Servlet
 * 
 *        Uses Context To Lookup Datasource From Server
 * 
 */
@WebServlet("/test")
public class ServletContextLookup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		try {
			InitialContext ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/test");
		} catch (NamingException e) {
			throw new ServletException("Unable to lookup DataSource", e);
		} catch (Exception e) {
			TomeeLogger.logError(getClass(), e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TomeeLogger.logInfo("@@@@ SERVLET CONTEXT LOOKUP HIT @@@@");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM TEST_USERS");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				out.println("<p>" + resultSet.getString("NAME") + "</p>");
			}
		} catch (Exception e) {
			TomeeLogger.logError(getClass(), e);
			out.println("<b>Something Went Wrong</b><br><p>" + e.getMessage() + "</p>");
		} finally {
			out.close();
		}
	}

}
