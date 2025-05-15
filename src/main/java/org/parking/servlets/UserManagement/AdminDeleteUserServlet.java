package org.parking.servlets.UserManagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.services.AdminService;

import java.io.IOException;

@WebServlet("/admin/user/delete")
public class AdminDeleteUserServlet extends HttpServlet {

    private final AdminService svc=new AdminService();
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        svc.deleteAdmin(req.getParameter("username"));    // works for any user
        res.sendRedirect("../users?msg=del");
    }
}
