package org.parking.servlets.UserManagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.models.AdminLogViewer;
import org.parking.models.LogViewer;
import org.parking.models.SystemLogViewer;

import java.io.IOException;

@WebServlet("/admin/logs")
public class AdminLogsServlet extends HttpServlet {

    private final LogViewer adminLogs  = new AdminLogViewer();
    private final LogViewer systemLogs = new SystemLogViewer();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("adminLogs",  adminLogs.viewLogs());
        req.setAttribute("systemLogs", systemLogs.viewLogs());
        req.getRequestDispatcher("/WEB-INF/jsp/admin/logs.jsp")
                .forward(req,res);
    }
}
