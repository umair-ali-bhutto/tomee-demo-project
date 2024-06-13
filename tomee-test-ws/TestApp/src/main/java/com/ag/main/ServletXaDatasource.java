package com.ag.main;

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
 * @author umair.ali
 * @version 1.0
 * @since 12-JUN-2024
 * 
 *        Servlet implementation class Servlet
 * 
 *        Uses @Resource Annotation To Lookup An XA Datasource
 * 
 */
@WebServlet("/testxa")
public class ServletXaDatasource extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/testXA")
	private DataSource dataSource;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TomeeLogger.logInfo("@@@@ SERVLET XA DATASOURCE 2 HIT @@@@");
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
