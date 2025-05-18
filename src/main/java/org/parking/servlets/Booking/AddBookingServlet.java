package org.parking.servlets.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.models.Slots.SlotStatus;
import org.parking.services.BookingService;
import org.parking.services.ParkingSlotService;
import org.parking.services.VehicleService;

import java.io.IOException;

@WebServlet("/booking/add")
public class AddBookingServlet extends HttpServlet {

    private final BookingService svc=new BookingService();
    private final ParkingSlotService slotSvc=new ParkingSlotService();
    private final VehicleService vehSvc=new VehicleService();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String user=((org.parking.models.User)req.getSession()
                .getAttribute("user")).getUsername();
        req.setAttribute("vehicles", vehSvc.listOf(user));
        req.setAttribute("slots",
                slotSvc.listAll().stream()
                        .filter(s->s.getStatus()== SlotStatus.AVAILABLE)
                        .toList());
        req.getRequestDispatcher("/WEB-INF/jsp/booking/addBooking.jsp")
                .forward(req,res);
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse res)
            throws ServletException,IOException{
        String user=((org.parking.models.User)req.getSession()
                .getAttribute("user")).getUsername();
        boolean ok=svc.createBooking(
                user,
                req.getParameter("plate"),
                Integer.parseInt(req.getParameter("slot")),
                req.getParameter("btype"));
        res.sendRedirect("../bookings?"+(ok?"msg=created":"error=slot"));
    }
}
