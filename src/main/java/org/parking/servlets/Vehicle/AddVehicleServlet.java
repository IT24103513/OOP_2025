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

@WebServlet("/vehicle/add")
public class AddVehicleServlet extends HttpServlet {

    private final VehicleService service = new VehicleService();

    /* ----------  GET  → show the form  ---------- */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/jsp/vehicle/addVehicle.jsp")
                .forward(req, resp);
    }


    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session=req.getSession(false);
        if(session==null){ resp.sendRedirect("../login.jsp"); return; }
        String owner=((User)session.getAttribute("user")).getUsername();

        boolean ok = service.add(req.getParameter("plate"),
                req.getParameter("color"),
                req.getParameter("type"),
                owner);
        resp.sendRedirect("../vehicle/list?"+(ok?"msg=added":"error=dup"));
    }
}
