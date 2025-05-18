package org.parking.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.parking.models.User;
import org.parking.services.UserService;

import java.io.IOException;
import java.util.Optional;


@WebServlet("/login")
public class LoginServlet  extends HttpServlet {

    private final UserService service = new UserService();



    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Optional<User> userOpt = service.login(req.getParameter("username"), req.getParameter("password"));

        if (userOpt.isPresent()) {
            HttpSession session = req.getSession();
            session.setAttribute("user", userOpt.get());
            resp.sendRedirect("dashboard");
        } else {
            resp.sendRedirect("login.jsp?error=invalid");
        }
    }


}
