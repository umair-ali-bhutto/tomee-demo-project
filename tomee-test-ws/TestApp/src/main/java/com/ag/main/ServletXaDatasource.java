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

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/testxa")
public class ServletXaDatasource extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name = "jdbc/testXA")
	private DataSource dataSource;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletXaDatasource() {
        super();
        // TODO Auto-generated constructor stub
    }
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("@@@@ SERVLET XA DATASOURCE 2 HIT @@@@");
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
			e.printStackTrace();
			e.printStackTrace(out);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
