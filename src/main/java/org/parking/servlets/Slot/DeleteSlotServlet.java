package org.parking.servlets.Slot;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.services.ParkingSlotService;

import java.io.IOException;


@WebServlet("/admin/slot/delete")
public class DeleteSlotServlet extends HttpServlet {


    private final ParkingSlotService svc=new ParkingSlotService();

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse res)

            throws ServletException, IOException {
        svc.delete(Integer.parseInt(req.getParameter("number")));
        res.sendRedirect("../slots?msg=deleted");
    }
}
