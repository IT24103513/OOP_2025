package org.parking.models.Feedback;

public class AnonymousFeedback extends Feedback{

    public AnonymousFeedback(String subject, String content) {
        super(subject, content);
    }
    @Override public String displayHeader() {
        return "[Anonymous]  —  " + getSubject();
    }
}
