package com.ag.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
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
 *        Uses @Resource Annotation To Lookup An Datasource And Gets Data Into
 *        Entity com.ag.entity.TestUsers
 * 
 */
@WebServlet("/testdata")
public class ServletDatasource extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/test")
	private DataSource dataSource;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("@@@@ SERVLET DATASOURCE 1 HIT @@@@");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM TEST_USERS");
			ResultSet resultSet = statement.executeQuery();
			List<TestUsers> usersList = new ArrayList<TestUsers>();
			TestUsers mdl = null;
			while (resultSet.next()) {
				mdl = new TestUsers();
				mdl.setId(resultSet.getLong("ID"));
				mdl.setName(resultSet.getString("NAME"));
				usersList.add(mdl);
			}

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
			e.printStackTrace();
			e.printStackTrace(out);
		}
	}

}
