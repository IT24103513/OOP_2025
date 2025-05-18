package org.parking.servlets.UserManagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.services.AdminService;

import java.io.IOException;

@WebServlet("/user/changeRole")
public class ChangeRoleServlet extends HttpServlet {

    private final AdminService svc = new AdminService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String newRole  = req.getParameter("newRole");

        boolean ok = svc.changeRole(username, newRole);
        if (!ok) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unable to change role");
            return;
        }

        // back to the user list
        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }
}
