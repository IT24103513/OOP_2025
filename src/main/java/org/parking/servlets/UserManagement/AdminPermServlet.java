package org.parking.servlets.UserManagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.models.Permission;
import org.parking.services.AdminService;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

@WebServlet("/admin/user/perm")
public class AdminPermServlet extends HttpServlet {

    private final AdminService svc=new AdminService();
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String u = req.getParameter("username");
        Set<Permission> perms = EnumSet.noneOf(Permission.class);
        for(Permission p:Permission.values())
            if(req.getParameter(p.name())!=null) perms.add(p);
        svc.setPermissions(u,perms);
        res.sendRedirect("../users?msg=perm");
    }
}
