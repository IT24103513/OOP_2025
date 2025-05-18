package org.parking.servlets.Feedback;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.models.User;
import org.parking.services.FeedbackService;

import java.io.IOException;

@WebServlet("/feedback/list")
public class FeedbackListServlet extends HttpServlet {

    private final FeedbackService service = new FeedbackService();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {           // not logged-in
            resp.sendRedirect("login.jsp");
            return;
        }

        req.setAttribute("entries", service.listForUser(user));
        req.getRequestDispatcher("/WEB-INF/jsp/feedback/list.jsp")
                .forward(req, resp);
    }
}
