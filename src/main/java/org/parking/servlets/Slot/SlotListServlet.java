package org.parking.servlets.Slot;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.services.ParkingSlotService;

import java.io.IOException;

@WebServlet("/admin/slots")
public class SlotListServlet extends HttpServlet {

    private final ParkingSlotService svc = new ParkingSlotService();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("slots", svc.listAll());
        req.getRequestDispatcher("/WEB-INF/jsp/slot/slotList.jsp")
                .forward(req,res);
    }
}
