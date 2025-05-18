package org.parking.servlets.Slot;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.services.ParkingSlotService;

import java.io.IOException;

@WebServlet("/admin/slot/add")
public class AddSlotServlet extends HttpServlet {

    private final ParkingSlotService svc = new ParkingSlotService();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/slot/addSlot.jsp")
                .forward(req,res);
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse res)
            throws ServletException, IOException {
        boolean ok = svc.defineSlot(
                Integer.parseInt(req.getParameter("number")),
                req.getParameter("type"));
        res.sendRedirect("../slots?" + (ok?"msg=added":"error=dup"));
    }
}
