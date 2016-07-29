package com.perks.emilena.security;

import com.perks.emilena.api.type.RoleType;

import java.security.Principal;
import java.util.List;

/**
 * Represents a user of the system
 * <p>
 * Created by Geoff Perks
 * Date: 28/07/2016.
 */
public class User implements Principal {

    private String userName;
    private String name;
    private List<RoleType> roles;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<RoleType> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleType> roles) {
        this.roles = roles;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
