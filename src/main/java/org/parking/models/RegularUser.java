package org.parking.models;

public class RegularUser extends User{

    public RegularUser(String username, String password, String email) {
        super(username, password, email);
    }
    @Override
    public boolean login(String passwordAttempt) {


        return passwordAttempt.equals(getPassword());
    }
}


