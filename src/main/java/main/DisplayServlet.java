package main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;

/**
 * Servlet implementation class DisplayServlet
 */
@WebServlet("/DisplayServlet")
public class DisplayServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");

		PrintWriter out = response.getWriter();

		Key userKey = (Key) request.getSession().getAttribute("userKey");

		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

		
		Entity task = null;

		try {
			task = datastore.get(userKey);
			String firstName = task.getString("FirstName");
			String lastName = task.getString("LastName");
			String email = task.getString("Email");
			String password = task.getString("Password");
			long age = task.getLong("Age");
			String gender = task.getString("Gender");
			
			out.println("<b>FirstName : " + firstName + "</b><br>");
			out.println("<b>LastName : " + lastName + "</b><br>");
			out.println("<b>Age: " + age + "</b><br>");
			out.println("<b>Gender " + gender + "</b><br>");
			out.println("<b>Email Id : " + email + "</b><br>");
			out.println("<b>Password: " + password + "</b>");
			

			out.println("<br><br><b><a href = \"update.html\">" + "UpdateAnPassword</a><b>");
			out.println("<br><br><b><a href = \"DeleteServlet\">" + "DeleteAnAccount</a></b>");

		} catch (DatastoreException e) {
			System.out.printf("Code: %s%nReason: %s%nMessage: %s%n", e.getCode(), e.getReason(), e.getMessage());
		}

	}

}
