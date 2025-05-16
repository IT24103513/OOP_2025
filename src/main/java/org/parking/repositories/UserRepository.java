package org.parking.repositories;

import org.parking.models.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void addUser(User u) throws IOException;

    Optional<User> findByUsername(String username) throws IOException;

    List<User> getAll() throws IOException;

    void updateUser(User updated) throws IOException;

    void deleteUser(String username) throws IOException;

    List<User> getAdmins() throws IOException;

    void overwrite(List<User> users) throws IOException;

}
