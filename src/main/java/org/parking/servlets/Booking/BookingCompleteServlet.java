package org.parking.servlets.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.services.BookingService;

import java.io.IOException;

@WebServlet("/booking/complete")
public class BookingCompleteServlet extends HttpServlet {

    private final BookingService bookingService = new BookingService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        long bookingId = Long.parseLong(req.getParameter("id"));
        String mode    = req.getParameter("mode");
        String cardNum = req.getParameter("cardNumber");  // may be null for cash

        // call new overload
        bookingService.complete(bookingId, mode, cardNum);

        // redirect back to list
        resp.sendRedirect(req.getContextPath() + "/bookings");
    }


}
