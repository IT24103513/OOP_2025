package org.parking.servlets.Vehicle;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.services.VehicleService;

import java.io.IOException;

@WebServlet("/vehicle/edit")
public class EditVehicleServlet extends HttpServlet {

    private final VehicleService service=new VehicleService();
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        // pre‑fill form
        service.get(req.getParameter("plate")).ifPresent(v->req.setAttribute("veh",v));
        req.getRequestDispatcher("/WEB-INF/jsp/vehicle/editVehicle.jsp").forward(req,resp);
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        service.updateColor(req.getParameter("plate"), req.getParameter("color"));
        resp.sendRedirect("list?msg=updated");
    }
}
