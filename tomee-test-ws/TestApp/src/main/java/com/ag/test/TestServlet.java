package com.ag.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.ag.util.TomeeLogger;

/**
 * 
 * JUST A TEST SERVLET Uses @Resource Annotation To Lookup An Datasource
 * 
 */

@WebServlet("/")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/test")
	private DataSource dataSource;

	public TestServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TomeeLogger.logInfo("@@@@ TEST HIT @@@@");
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
		}
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
