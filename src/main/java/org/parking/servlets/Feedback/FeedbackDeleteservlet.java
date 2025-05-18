package org.parking.servlets.Feedback;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.models.User;
import org.parking.services.FeedbackService;

import java.io.IOException;

@WebServlet("/feedback/delete")
public class FeedbackDeleteservlet extends HttpServlet {

    private final FeedbackService service = new FeedbackService();

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User actor = (User) req.getSession().getAttribute("user");
        String id  = req.getParameter("id");

        if (!service.delete(actor, id)) { resp.sendError(403); return; }

        resp.sendRedirect(req.getContextPath() + "/feedback");
    }
}
