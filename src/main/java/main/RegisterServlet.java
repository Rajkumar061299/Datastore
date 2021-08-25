package main;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("email");
		String password = request.getParameter("psw");
		int age = Integer.parseInt(request.getParameter("age"));
		String gender = request.getParameter("gender");
		
		System.out.println(gender);

		Key userKey = addingNewEntity(firstName, lastName, email, password, age, gender);
		request.getSession().setAttribute("userKey", userKey);
		response.sendRedirect("login.html");
	}

	public Key addingNewEntity(String firstName, String lastName, String email, String password, int age, String gender) {

		// String userKey1 = UUID.randomUUID().toString();
		String userKey2 = UUID.randomUUID().toString();

		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

		// Key taskKey1 = datastore.newKeyFactory().setKind("User").newKey(userKey1);

		// Entity task1 = Entity.newBuilder(taskKey1).set("Email",
		// email).set("Password", password).build();
//addAncestors(PathElement.of("User", userKey1))
		Key taskKey2 = datastore.newKeyFactory().setKind("UserDetails").newKey(userKey2);

		Entity task2 = Entity.newBuilder(taskKey2).set("FirstName", firstName).set("LastName", lastName).set("Age", age)
				.set("Gender", gender).set("Email", email).set("Password", password).build();

		// datastore.put(task1);
		datastore.put(task2);

		return taskKey2;
	}
// different types of keys, transcation
}
