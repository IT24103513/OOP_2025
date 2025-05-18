package org.parking.servlets.Feedback;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.models.Feedback.Feedback;
import org.parking.models.User;
import org.parking.services.FeedbackService;

import java.io.IOException;

@WebServlet("/feedback/edit")
public class FeedbackEditServlet extends HttpServlet {

    private final FeedbackService service = new FeedbackService();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User actor = (User) req.getSession().getAttribute("user");
        String id   = req.getParameter("id");

        var opt = service.listAll().stream()   // quick lookup
                .filter(f -> f.getId().equals(id))
                .findFirst();

        if (opt.isEmpty() || !service.update(actor, opt.get())) {   // permission check
            resp.sendError(403);
            return;
        }

        req.setAttribute("fb", opt.get());
        req.getRequestDispatcher("/WEB-INF/jsp/feedback/edit.jsp")
                .forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User actor = (User) req.getSession().getAttribute("user");
        String id = req.getParameter("id");

        var opt = service.listAll().stream()
                .filter(f -> f.getId().equals(id))
                .findFirst();
        if (opt.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        var fb = opt.get();
        fb.setSubject(req.getParameter("subject"));
        fb.setContent(req.getParameter("content"));

        if (actor != null && actor.isAdmin() && req.getParameter("status") != null) {
            fb.setStatus(Feedback.Status.valueOf(req.getParameter("status")));
        }


        if (!service.update(actor, fb)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/feedback/list");

    }
}
