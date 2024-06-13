package com.ag.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.ag.entity.TestUsers;

/**
 * @author umair.ali
 * @version 1.0
 * @since 12-JUN-2024
 * 
 *        Servlet implementation class ServletDatasource
 * 
 *        Uses @PersistenceContext Annotation To Lookup A Datasource And Gets
 *        Data From Entity com.ag.entity.TestUsers Using Named Query
 * 
 */
@WebServlet("/testnamed")
public class ServletNamedQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@PersistenceContext(name = "myPersistenceUnit")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("@@@@ SERVLET NAMED QUERY HIT @@@@");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {

			List<TestUsers> usersList = new ArrayList<TestUsers>();
			Query q = entityManager.createNamedQuery("TestUsers.findAll", TestUsers.class);
			usersList = (List<TestUsers>) q.getResultList();

			if (usersList.size() != 0) {
				out.println("<table border=\"1\"><thead>\n<tr>\n<th>ID</th>\n<th>NAME</th>\n</tr>\n</thead>\n<tbody>");

				for (TestUsers user : usersList) {
					out.println("<tr>");
					out.println("<td>" + user.getId() + "</td>");
					out.println("<td>" + user.getName() + "</td>");
					out.println("</tr>");
				}
				out.println("</tbody>\n</table>");

				out.println("<br><br><ol>");

				for (TestUsers user : usersList) {
					out.println("<li>" + user.getName() + "</li>");
				}
				out.println("</ol>");
			} else {
				out.println("<p>No Record Found.</p>");
			}

		} catch (Exception e) {
			out.println("<p>Something Went Wrong <br> " + e.getMessage() + " </p>");
			e.printStackTrace();
		}
	}

}
