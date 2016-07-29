package com.perks.emilena.security;

import com.perks.emilena.api.Role;
import com.perks.emilena.api.type.RoleType;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user of the system
 * <p>
 * Created by Geoff Perks
 * Date: 28/07/2016.
 */
public class User implements Principal {

    /**
     * Synonmous with Principal. In this system, the subject is identified with the the user name...
     */
    private String userName;
    private String password;
    private List<Role> roles;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * Returns the name of this principal.
     *
     * @return the name of this principal.
     */
    @Override
    public String getName() {
        return this.userName;
    }

}
