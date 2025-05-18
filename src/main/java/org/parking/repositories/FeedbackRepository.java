package org.parking.repositories;

import org.parking.models.Feedback.Feedback;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FeedbackRepository {

    void add(Feedback fb) throws IOException;
    List<Feedback> all() throws IOException;
    Optional<Feedback> findById(String id) throws IOException;
    void update(Feedback fb) throws IOException;
    void delete(String id) throws IOException;
}
