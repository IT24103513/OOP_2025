package org.parking.models.Feedback;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Feedback implements Serializable {


    private final String id = UUID.randomUUID().toString();
    private final LocalDateTime createdAt = LocalDateTime.now();

    private String subject;
    private String content;           // encapsulated
    private Status status = Status.OPEN;
    private LocalDateTime lastUpdated = createdAt;

    protected Feedback() {}          // for deserialisation

    protected Feedback(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    /* ---------- Getters / Setters ---------- */
    public String getId() { return id; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) {
        this.subject = subject; touch();
    }

    public String getContent() { return content; }
    public void setContent(String content) {
        this.content = content; touch();
    }

    public Status getStatus() { return status; }
    public void setStatus(Status status) {
        this.status = status; touch();
    }

    private void touch() { this.lastUpdated = LocalDateTime.now(); }

    /* ------------ Polymorphic hook ------------ */
    public abstract String displayHeader();   // overriden below

    /* --------- Extra enum --------- */
    public enum Status { OPEN, IN_PROGRESS, CLOSED }
}
