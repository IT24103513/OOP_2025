package org.parking.servlets.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.services.BookingService;

import java.io.IOException;

@WebServlet("/admin/booking/delete")
public class DeleteBookingServlet extends HttpServlet {

    private final BookingService svc=new BookingService();

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        svc.delete(Long.parseLong(req.getParameter("id")));
        res.sendRedirect("../bookings?msg=del");
    }
}
