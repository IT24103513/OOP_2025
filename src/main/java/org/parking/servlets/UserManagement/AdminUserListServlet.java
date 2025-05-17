package org.parking.servlets.UserManagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.services.AdminService;

import java.io.IOException;

@WebServlet("/admin/users")
public class AdminUserListServlet extends HttpServlet {

    private final AdminService svc=new AdminService();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("users", svc.listAllUsers());
        req.getRequestDispatcher("/WEB-INF/jsp/admin/userList.jsp")
                .forward(req,res);
    }
}
