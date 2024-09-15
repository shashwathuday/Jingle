package com.pdp.jingle.servlets;

import java.io.IOException;
import java.time.LocalTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pdp.jingle.models.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			response.sendRedirect("HomePageServlet");
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/login.jsp");
			dispatcher.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// get the form data
			String email = request.getParameter("email").toLowerCase();
			String password = request.getParameter("password").toLowerCase();

			User user = new User(email, password);

			boolean userExists = dbUtil.findUser(user);
			if (userExists) {
				LocalTime currentTime = LocalTime.now();

				// Get the hours alone
				int hours = currentTime.getHour();
				String greetings;

				if ((hours >= 6) && (hours < 12)) {
					greetings = "Good Morning,";
				}

				else if ((hours >= 12) && (hours < 16)) {
					greetings = "Good Afternoon,";
				}

				else if ((hours >= 16) && (hours < 22)) {
					greetings = "Good Evening,";
				}

				else {
					greetings = "Stay Tuned,";
				}

				User userDetails = dbUtil.getUserDetails(user);
				request.removeAttribute("loginError");

				HttpSession session = request.getSession(true);
				session.setAttribute("User_Details", userDetails);
				session.setAttribute("Greetings", greetings);
				response.sendRedirect("HomePageServlet");

			} else {
				request.setAttribute("loginError", "Incorrect email or password");
				RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/login.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception exec) {
			exec.printStackTrace();
		}

	}
}
