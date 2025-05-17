package org.parking.models.Feedback;

import java.time.LocalDateTime;

public class AnonymousFeedback extends Feedback{

//    public AnonymousFeedback(String subject, String content) {
//        super(subject, content);
//    }
//    @Override public String displayHeader() {
//        return "[Anonymous]  —  " + getSubject();
//    }

    public AnonymousFeedback(String subject, String content) {
        super(subject, content);
    }

    /** for hydrating from CSV/DB */
    public AnonymousFeedback(String id,
                             LocalDateTime createdAt,
                             LocalDateTime lastUpdated,
                             String subject,
                             String content,
                             Status status)
    {
        super(id, createdAt, lastUpdated, subject, content, status);
    }

    @Override
    public String displayHeader() {
        return "Anonymous  —  " + getSubject();
    }
}
