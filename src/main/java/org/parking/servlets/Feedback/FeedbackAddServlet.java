package org.parking.servlets.Feedback;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.parking.models.User;
import org.parking.services.FeedbackService;

import java.io.IOException;

@WebServlet("/feedback/add")
public class FeedbackAddServlet extends HttpServlet {

    private final FeedbackService service = new FeedbackService();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/feedback/add.jsp").forward(req, resp);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        boolean anonymous = "on".equals(req.getParameter("anonymous"));

        service.submit(user,
                req.getParameter("subject"),
                req.getParameter("content"),
                anonymous);

        resp.sendRedirect(req.getContextPath() + "/feedback/list");

    }
}
