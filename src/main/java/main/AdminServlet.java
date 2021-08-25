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
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;


@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

		try {

			Query<Entity> query = Query.newEntityQueryBuilder().setKind("UserDetails")
					.setFilter(CompositeFilter.and(PropertyFilter.gt("Age", 20),
							PropertyFilter.lt("Age", 30))).setOrderBy(OrderBy.desc("Age")).build();

			QueryResults<Entity> results = datastore.run(query);

			while (results.hasNext()) {	
				Entity task = results.next();

				String firstName = task.getString("FirstName");
				String lastName = task.getString("LastName");
				String email = task.getString("Email");
				String password = task.getString("Password");
				long age = task.getLong("Age");
				String gender = task.getString("Gender");

				out.print("<br><b>FirstName : " + firstName + "</b><br>");
				out.print("<b>LastName : " + lastName + "</b><br>");
				out.print("<b>Age: " + age + "</b><br>");
				out.print("<b>Gender: " + gender + "</b><br>");
				out.print("<b>Email Id : " + email + "</b><br>");
				out.print("<b>Password: " + password + "</b><br>");
				out.print("<b>--------------------</b>");
			}

		} catch (DatastoreException e) {
			out.printf("Code: %s%nReason: %s%nMessage: %s%n", e.getCode(), e.getReason(), e.getMessage());
		}

	}

}
