package org.parking.servlets.Vehicle;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.services.VehicleService;

import java.io.IOException;

@WebServlet("/vehicle/delete")
public class DeleteVehicleServlet extends HttpServlet {

    private final VehicleService service=new VehicleService();
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        service.delete(req.getParameter("plate"));
        resp.sendRedirect("list?msg=deleted");
    }
}
