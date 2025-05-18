package org.parking.servlets.Report;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.services.ReportService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@WebServlet("/admin/reports")
public class ReportServlet extends HttpServlet {

    private final ReportService svc = new ReportService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, Double> byType = svc.getRevenueByType();
        Map<LocalDate, Double> daily  = svc.getDailyRevenue(7);

        req.setAttribute("revenueByType", byType);
        req.setAttribute("dailyRevenue",  daily);
        req.getRequestDispatcher("/WEB-INF/jsp/admin/reports.jsp")
                .forward(req, resp);
    }
}
