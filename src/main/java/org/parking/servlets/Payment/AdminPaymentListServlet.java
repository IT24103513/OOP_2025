package org.parking.servlets.Payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.dao.PaymentDAO;

import java.io.IOException;

@WebServlet("/admin/payments")
public class AdminPaymentListServlet extends HttpServlet {

    private final PaymentDAO dao=new PaymentDAO();
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("payments", dao.findAll());
        req.getRequestDispatcher("/WEB-INF/jsp/admin/paymentList.jsp")
                .forward(req,res);
    }
}
