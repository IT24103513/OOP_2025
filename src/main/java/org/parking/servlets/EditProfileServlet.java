package org.parking.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.parking.services.UserService;

import java.io.IOException;

@WebServlet("/profile/edit")
public class EditProfileServlet extends HttpServlet {

    private final UserService service = new UserService();

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null) { resp.sendRedirect("login.jsp"); return; }

        String username = (String) ((org.parking.models.User) session.getAttribute("user")).getUsername();
        String newEmail = req.getParameter("email");

        service.updateEmail(username, newEmail);
        resp.sendRedirect("../profile?msg=updated");
    }
}
