package org.parking.models.Feedback;

import org.parking.models.User;

import java.time.LocalDateTime;

public class VerifiedFeedback extends Feedback{

    private final String username;

    /** for new feedback submissions */

    public VerifiedFeedback(User user, String subject, String content) {
        super(subject, content);
        this.username = user.getUsername();
    }

    /** for hydrating from CSV/DB */
    public VerifiedFeedback(String id,
                            LocalDateTime createdAt,
                            LocalDateTime lastUpdated,
                            String subject,
                            String content,
                            Status status,
                            String username)
    {
        super(id, createdAt, lastUpdated, subject, content, status);
        this.username = username;
    }

    public String getUsername() { return username; }

    @Override
    public String displayHeader() {

        return username + "  —  " + getSubject();
    }
}
