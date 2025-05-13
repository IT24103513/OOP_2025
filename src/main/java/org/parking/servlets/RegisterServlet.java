package org.parking.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.services.UserService;

import java.io.IOException;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserService service = new UserService();

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String user = req.getParameter("username");
        String pass = req.getParameter("password");
        String mail = req.getParameter("email");
        boolean admin = "on".equals(req.getParameter("admin"));

        if (service.register(user, pass, mail, admin)) {
            resp.sendRedirect("login.jsp?msg=registered");
        } else {
            resp.sendRedirect("register.jsp?error=exists");
        }
    }


}
