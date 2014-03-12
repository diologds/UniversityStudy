package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.LoginService;
import domain.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String userID, password;
		userID = request.getParameter("UserID");
		password = request.getParameter("UserPassword");

		LoginService service = new LoginService();
		boolean result = service.authenticate(userID, password);
		if (result) {
			User user = service.getUser(userID);
			request.getSession().setAttribute("user", user);
			RequestDispatcher dispacher = request
					.getRequestDispatcher("user.jsp");
			dispacher.forward(request, response);
			return;
		} else {
			response.sendRedirect("login.jsp");
			return;
		}
	}
}
