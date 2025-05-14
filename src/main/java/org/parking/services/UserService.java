package org.parking.services;

import org.parking.dao.UserDAO;
import org.parking.models.AdminUser;
import org.parking.models.RegularUser;
import org.parking.models.User;

import java.io.IOException;
import java.util.Optional;

public class UserService {


    private final UserDAO dao = new UserDAO();

    public boolean register(String username, String password, String email, boolean admin) throws IOException {
        if (dao.findByUsername(username).isPresent()) return false;   // duplicate
        User user = admin ? new AdminUser(username, password, email)
                : new RegularUser(username, password, email);
        dao.addUser(user);
        return true;
    }

    public Optional<User> login(String username, String pw) throws IOException {
        Optional<User> userOpt = dao.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().login(pw)) return userOpt;
        return Optional.empty();
    }

    public boolean updateEmail(String username, String newEmail) throws IOException {
        Optional<User> userOpt = dao.findByUsername(username);
        if (userOpt.isEmpty()) return false;
        User u = userOpt.get();
        u.setEmail(newEmail);
        dao.updateUser(u);
        return true;
    }

    public boolean delete(String username) throws IOException {
        if (dao.findByUsername(username).isEmpty()) return false;
        dao.deleteUser(username);
        return true;
    }
}
