package org.parking.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class User implements Serializable {

    private String username;
    private String password;
    private String email;
    private LocalDateTime createdAt;


    protected User() { }             // For deserialization

    protected User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email    = email;
        this.createdAt = LocalDateTime.now();
    }

    //  Encapsulation
    public String getUsername() {
        return username;
    }
    public String getPassword()              {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // --- Polymorphic behaviour ---
    public abstract boolean login(String passwordAttempt);



    @Override public boolean equals(Object o) {
        return o instanceof User u && Objects.equals(username, u.username);
    }
    @Override public int hashCode() {
        return Objects.hash(username);
    }

    public boolean isAdmin() {
        return this instanceof AdminUser;
    }
}
