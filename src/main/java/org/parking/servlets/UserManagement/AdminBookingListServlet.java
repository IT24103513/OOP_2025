package org.parking.servlets.UserManagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.services.AdminService;

import java.io.IOException;

@WebServlet("/admin/bookings")
public class AdminBookingListServlet extends HttpServlet {

    private final AdminService svc=new AdminService();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("bookings", svc.listAllBookings());
        req.getRequestDispatcher("/WEB-INF/jsp/admin/bookingList.jsp")
                .forward(req,res);
    }
}
