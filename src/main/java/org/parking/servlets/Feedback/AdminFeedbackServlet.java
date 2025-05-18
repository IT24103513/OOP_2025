package org.parking.servlets.Feedback;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.models.User;
import org.parking.services.FeedbackService;

import java.io.IOException;

@WebServlet("/admin/feedbacks")
public class AdminFeedbackServlet extends HttpServlet {

    private final FeedbackService service = new FeedbackService();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User actor = (User) req.getSession().getAttribute("user");
        if (actor == null || !actor.isAdmin()) { resp.sendError(403); return; }

        req.setAttribute("entries", service.listAll());
        req.getRequestDispatcher("/WEB-INF/jsp/feedback/admin_list.jsp")
                .forward(req, resp);
    }
}
