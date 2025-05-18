package org.parking.services;

import org.parking.dao.BookingDAO;
import org.parking.dao.UserDAO;
import org.parking.models.AdminUser;
import org.parking.models.Permission;
import org.parking.models.RegularUser;
import org.parking.models.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class AdminService {

    private final UserDAO userDAO = new UserDAO();
    private final BookingDAO bookDAO  = new BookingDAO();

    /* ----------  CRUD on admins  ---------- */

    // registerAdmin()
    public boolean addAdmin(String username,String pw,String email) throws IOException {
        if(userDAO.findByUsername(username).isPresent()) return false;
        userDAO.addUser(new AdminUser(username,pw,email));
        return true;
    }

    // viewUsers()
    public List<User> listAllUsers() throws IOException {
        return userDAO.getAll();
    }

    // updatePermissions()
    public boolean setPermissions(String adminUsername, Set<Permission> perms) throws IOException{
        Optional<User> opt=userDAO.findByUsername(adminUsername);
        if(opt.isEmpty() || !(opt.get() instanceof AdminUser a)) return false;
        a.setPermissions(perms);
        userDAO.updateUser(a);
        return true;
    }

    // deleteAdmin()
    public boolean deleteAdmin(String username) throws IOException{
        Optional<User> opt=userDAO.findByUsername(username);
        if(opt.isEmpty() || !(opt.get() instanceof AdminUser)) return false;
        userDAO.deleteUser(username);  return true;
    }

    // in AdminService.java
    public boolean deleteUser(String username) throws IOException {
        Optional<User> opt = userDAO.findByUsername(username);
        if (opt.isEmpty()) return false;
        userDAO.deleteUser(username);
        return true;
    }



    public boolean changeRole(String username, String newRole) throws IOException {
        Optional<User> opt = userDAO.findByUsername(username);
        if (opt.isEmpty()) return false;

        User old = opt.get();
        // if they’re already in the requested role, nothing to do
        boolean alreadyAdmin = old instanceof AdminUser;
        if (newRole.equals("AdminUser") && alreadyAdmin)     return true;
        if (newRole.equals("RegularUser") && !alreadyAdmin)  return true;

        // build the new instance
        User updated;
        if (newRole.equals("AdminUser")) {
            updated = new AdminUser(old.getUsername(), old.getPassword(), old.getEmail());
        } else if (newRole.equals("RegularUser")) {
            updated = new RegularUser(old.getUsername(), old.getPassword(), old.getEmail());
        } else {
            return false;
        }

        userDAO.updateUser(updated);
        return true;
    }


    /* ---------- booking oversight ---------- */

    public List<org.parking.models.Bookings.Booking> listAllBookings() throws IOException{
        return bookDAO.findAll();
    }
    public void cancelBooking(long id) throws IOException{ bookDAO.delete(id); }
}
