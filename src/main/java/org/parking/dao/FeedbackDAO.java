package org.parking.dao;

import org.parking.models.Feedback.AnonymousFeedback;
import org.parking.models.Feedback.Feedback;
import org.parking.models.Feedback.VerifiedFeedback;
import org.parking.models.User;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FeedbackDAO {

    private static final Path FILE = Paths.get("data", "feedbacks.txt");

    /* ---------- C R U D ---------- */
    public void add(Feedback fb) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(FILE,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            bw.write(toCsv(fb));
            bw.newLine();
        }
    }

    public List<Feedback> all() throws IOException {
        if (Files.notExists(FILE)) return new ArrayList<>();
        List<Feedback> list = new ArrayList<>();
        for (String ln : Files.readAllLines(FILE)) {
            if (!ln.isBlank()) list.add(fromCsv(ln));
        }
        return list;
    }

    public Optional<Feedback> findById(String id) throws IOException {
        return all().stream().filter(f -> f.getId().equals(id)).findFirst();
    }

    public void update(Feedback updated) throws IOException {
        List<Feedback> list = all();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(updated.getId())) {
                list.set(i, updated); break;
            }
        }
        overwrite(list);
    }

    public void delete(String id) throws IOException {
        List<Feedback> list = all();
        list.removeIf(f -> f.getId().equals(id));
        overwrite(list);
    }

    /* ---------- helpers ---------- */
    private void overwrite(List<Feedback> list) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(FILE,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Feedback f : list) {
                bw.write(toCsv(f)); bw.newLine();
            }
        }
    }

    private String toCsv(Feedback f) {
        String type = (f instanceof VerifiedFeedback) ? "VERIFIED" : "ANON";
        String user = (f instanceof VerifiedFeedback vf) ? vf.getUsername() : "";
        return String.join("::",
                f.getId(),
                type,
                escape(f.getSubject()),
                escape(f.getContent()),
                f.getStatus().name(),
                user,
                f.getCreatedAt().toString(),
                f.getLastUpdated().toString());
    }

    private Feedback fromCsv(String csv) {
        String[] p       = csv.split("::", -1);
        String type      = p[1];
        String subject   = unescape(p[2]);
        String content   = unescape(p[3]);
        Feedback f       = switch (type) {
            case "VERIFIED" -> {
                // stub User: we only care about getUsername() here
                User stub = new User(p[5], "", "") {
                    @Override
                    public boolean login(String passwordAttempt) {
                        // not used in DAO; just satisfy the abstract method
                        return false;
                    }
                };
                yield new VerifiedFeedback(stub, subject, content);
            }
            default -> new AnonymousFeedback(subject, content);
        };
        f.setStatus(Feedback.Status.valueOf(p[4]));
        return f;
    }


    private String escape(String s) { return s.replace("\\", "\\\\").replace("\n", "\\n"); }
    private String unescape(String s) { return s.replace("\\n", "\n").replace("\\\\", "\\"); }
}
