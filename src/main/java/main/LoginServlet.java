package main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
	

		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

		Query<Entity> query = null;

		try {
			query = Query.newEntityQueryBuilder().setKind("UserDetails").setFilter(PropertyFilter.eq("Email", email))
					.build();

			QueryResults<Entity> results = datastore.run(query);

			while (results.hasNext()) {
				Entity entity = results.next();
				String pass = entity.getString("Password");

				if (pass.equals(password)) {
						if(email.equals("admin")) {
							response.sendRedirect("AdminServlet");
							break;
						}
						else {
							response.sendRedirect("DisplayServlet");
							break;}
				}

				else {
					out.println("<script type=\"text/javascript\">");
					out.println("alert('username and password is incorrect');");
					out.println("location='login.html';");
					out.println("</script>");

				}

			}

		} catch (NullPointerException e) {
			out.printf("Message: %s%n", e.getMessage());

		}

	}
//  list queries, And operation, multiple filters indexes --> build and composite indexes,
}
