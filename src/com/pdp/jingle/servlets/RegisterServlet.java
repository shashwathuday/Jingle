package com.pdp.jingle.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pdp.jingle.models.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public RegisterServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Redirect to the Register page
		RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/register.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String firstName = request.getParameter("firstName").toLowerCase();
			String lastName = request.getParameter("lastName").toLowerCase();
			String email = request.getParameter("email").toLowerCase();
			String password = request.getParameter("password").toLowerCase();
			String location = request.getParameter("location").toLowerCase();
			String dp = "media/default_dp.png";

			// create a user object
			User user = new User(null, firstName, lastName, email, password, location, dp);

			// find email id
			boolean mailExists = dbUtil.findEmail(email);

			request.removeAttribute("emailError");

			if (mailExists) {
				request.setAttribute("emailError", "Email id already exists!");
				request.setAttribute("UserDetails", user);
				RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/register.jsp");
				dispatcher.forward(request, response);
			}

			else {
				// call the registerUser method in the dbUtil class to register the new
				// user to the userDb
				dbUtil.registerUser(user);

				// to go back to the login page
				request.setAttribute("registrationSuccessful", "Registration Successful, Pls Login");
				response.sendRedirect("LoginServlet");
			}
		} catch (Exception exec) {
			exec.printStackTrace();
		}
	}

}
