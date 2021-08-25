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
import com.google.cloud.datastore.Key;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

		Key userKey = (Key) request.getSession().getAttribute("userKey");

		try {
			datastore.delete(userKey);

			out.println("<script type=\"text/javascript\">");
			out.println("alert('deleted successfully');");
			out.println("location='Register.html';");
			out.println("</script>");
		} catch (DatastoreException e) {
			System.out.printf("Code: %s%nReason: %s%nMessage: %s%n", e.getCode(), e.getReason(), e.getMessage());
		}

	}

}
