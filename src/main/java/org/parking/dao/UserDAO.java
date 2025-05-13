package org.parking.dao;

import org.parking.models.AdminUser;
import org.parking.models.RegularUser;
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

public class UserDAO {

    private static final Path USER_FILE = Paths.get("data", "users.txt");

    public void addUser(User u) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(USER_FILE, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            bw.write(toCsv(u));
            bw.newLine();
        }
    }

    public Optional<User> findByUsername(String username) throws IOException {
        return getAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    public List<User> getAll() throws IOException {
        if (Files.notExists(USER_FILE)) return new ArrayList<>();
        List<User> list = new ArrayList<>();
        for (String line : Files.readAllLines(USER_FILE)) {
            if (line.isBlank()) continue;
            list.add(fromCsv(line));
        }
        return list;
    }

    // --- Update ---
    public void updateUser(User updated) throws IOException {
        List<User> users = getAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updated.getUsername())) {
                users.set(i, updated);
                break;
            }
        }
        overwrite(users);
    }

    // --- Delete ---
    public void deleteUser(String username) throws IOException {
        List<User> users = getAll();
        users.removeIf(u -> u.getUsername().equals(username));
        overwrite(users);
    }

    public List<User> getAdmins() throws IOException {
        return getAll().stream()
                .filter(u -> u instanceof AdminUser)
                .toList();
    }


    /* --------------- Helpers --------------- */

    private void overwrite(List<User> users) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(USER_FILE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (User u : users) {
                bw.write(toCsv(u));
                bw.newLine();
            }
        }
    }

    private String toCsv(User u) {
        String role = (u instanceof AdminUser) ? "ADMIN" : "REGULAR";
        return String.join(",", u.getUsername(), u.getPassword(), u.getEmail(), role);
    }

    private User fromCsv(String csv) {
        String[] p = csv.split(",", -1);
        return switch (p[3]) {
            case "ADMIN"   -> new AdminUser(p[0], p[1], p[2]);
            default        -> new RegularUser(p[0], p[1], p[2]);
        };
    }

}
