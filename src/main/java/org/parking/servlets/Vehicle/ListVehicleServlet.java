package org.parking.servlets.Vehicle;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.parking.models.User;
import org.parking.services.VehicleService;

import java.io.IOException;

@WebServlet("/vehicle/list")
public class ListVehicleServlet extends HttpServlet {

    private final VehicleService service=new VehicleService();
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s=req.getSession(false);
        if(s==null){ resp.sendRedirect("../login.jsp"); return; }
        String owner=((User)s.getAttribute("user")).getUsername();
        req.setAttribute("vehicles", service.listOf(owner));
        req.getRequestDispatcher("/WEB-INF/jsp/vehicle/listVehicles.jsp").forward(req,resp);
    }
}
