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
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String password = request.getParameter("psw");
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

		Key userKey = (Key) request.getSession().getAttribute("userKey");

		Entity task = null;Entity.newBuilder(datastore.get(userKey)).set("Password", password).build();

		try {
			datastore.update(task);
			out.println("<script type=\"text/javascript\">");
			out.println("alert('updated successfully');");
			out.println("location='DisplayServlet';");
			out.println("</script>");

		} catch (DatastoreException e) {
			System.out.printf("Code: %s%nReason: %s%nMessage: %s%n", e.getCode(), e.getReason(), e.getMessage());
		}
	}

}
