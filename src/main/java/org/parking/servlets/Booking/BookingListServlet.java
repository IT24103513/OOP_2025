package org.parking.servlets.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.services.BookingService;

import java.io.IOException;

@WebServlet("/bookings")
public class BookingListServlet extends HttpServlet {

    private final BookingService svc=new BookingService();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String user=((org.parking.models.User)req.getSession()
                .getAttribute("user")).getUsername();
        req.setAttribute("bookings", svc.getAllOf(user));
        req.getRequestDispatcher("/WEB-INF/jsp/booking/bookingList.jsp")
                .forward(req,res);
    }
}
