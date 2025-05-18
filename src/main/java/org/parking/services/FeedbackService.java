package org.parking.services;

import org.parking.dao.FeedbackDAO;
import org.parking.models.Feedback.AnonymousFeedback;
import org.parking.models.Feedback.Feedback;
import org.parking.models.Feedback.VerifiedFeedback;
import org.parking.models.User;

import org.parking.repositories.FeedbackRepository;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FeedbackService {


    //private final FeedbackDAO dao = new FeedbackDAO();

    private final FeedbackRepository dao = new FeedbackDAO();


    /* --------- Create --------- */
    public void submit(User userOpt, String subject, String content, boolean anonymous) throws IOException {
        Feedback fb = anonymous || userOpt == null
                ? new AnonymousFeedback(subject, content)
                : new VerifiedFeedback(userOpt, subject, content);
        dao.add(fb);
    }

    /* --------- Read --------- */
    public List<Feedback> listForUser(User user) throws IOException {
        return dao.all().stream()
                .filter(f -> f instanceof VerifiedFeedback vf && vf.getUsername().equals(user.getUsername()))
                .collect(Collectors.toList());
    }
    public List<Feedback> listAll() throws IOException { return dao.all(); }

    /* --------- Update / Delete (owner or admin) --------- */
    public boolean update(User actor, Feedback updated) throws IOException {
        if (!permits(actor, updated)) return false;
        dao.update(updated); return true;
    }

    public boolean delete(User actor, String id) throws IOException {
        var opt = dao.findById(id);
        if (opt.isEmpty() || !permits(actor, opt.get())) return false;
        dao.delete(id); return true;
    }

    /* --------- helper --------- */
    private boolean permits(User actor, Feedback fb) {
        if (actor == null) return false;
        if (actor.isAdmin()) return true;
        return fb instanceof VerifiedFeedback vf && vf.getUsername().equals(actor.getUsername());
    }
}
