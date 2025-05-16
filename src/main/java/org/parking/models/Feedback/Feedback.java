package org.parking.models.Feedback;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Feedback implements Serializable {


    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    private String subject;
    private String content;
    private Status status;

    /**
     * Constructor for new feedbacks
     */
    protected Feedback(String subject, String content) {
        this.id          = UUID.randomUUID().toString();
        this.createdAt   = LocalDateTime.now();
        this.lastUpdated = this.createdAt;
        this.subject     = subject;
        this.content     = content;
        this.status      = Status.OPEN;
    }

    /**
     * Constructor for hydrating from persistence (CSV, DB, etc.)
     */
    protected Feedback(String id,
                       LocalDateTime createdAt,
                       LocalDateTime lastUpdated,
                       String subject,
                       String content,
                       Status status)
    {
        this.id          = id;
        this.createdAt   = createdAt;
        this.lastUpdated = lastUpdated;
        this.subject     = subject;
        this.content     = content;
        this.status      = status;
    }

    /* ---------- Getters / Setters ---------- */
    public String getId() { return id; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; touch(); }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; touch(); }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; touch(); }

    private void touch() { this.lastUpdated = LocalDateTime.now(); }

    /** Subclasses implement how to display header text */
    public abstract String displayHeader();

    public enum Status { OPEN, IN_PROGRESS, CLOSED }
}
