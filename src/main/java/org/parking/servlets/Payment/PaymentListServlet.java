package org.parking.servlets.Payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.services.PaymentService;

import java.io.IOException;

@WebServlet("/payments")
public class PaymentListServlet extends HttpServlet {

    private final PaymentService svc=new PaymentService();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String user=((org.parking.models.User)req.getSession()
                .getAttribute("user")).getUsername();
        req.setAttribute("payments", svc.listOf(user));
        req.getRequestDispatcher("/WEB-INF/jsp/payment/paymentList.jsp")
                .forward(req,res);
    }
}
