package org.parking.models.Feedback;

import org.parking.models.User;

public class VerifiedFeedback extends Feedback{

    private final String username;           // store only username to avoid deep-serialisation

    public VerifiedFeedback(User user, String subject, String content) {
        super(subject, content);
        this.username = user.getUsername();
    }
    public String getUsername() { return username; }

    @Override public String displayHeader() {
        return username + "  —  " + getSubject();
    }
}
