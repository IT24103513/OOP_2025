package org.parking.models;

import java.util.EnumSet;
import java.util.Set;

public class AdminUser extends User{

    /* ENCAPSULATION — permissions hidden behind methods */
    private Set<Permission> permissions = EnumSet.allOf(Permission.class);

    public AdminUser(String username, String password, String email) {

        super(username, password, email);
    }

    public Set<Permission> getPermissions()   {
        return permissions;
    }
    public void setPermissions(Set<Permission> perms)  {
        this.permissions = perms;
    }

    @Override
    public boolean login(String passwordAttempt) {
        // Could add MFA or extra checks for admins
        return passwordAttempt.equals(getPassword());
    }


}
