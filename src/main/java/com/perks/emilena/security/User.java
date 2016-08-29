package com.perks.emilena.security;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

import com.perks.emilena.api.Staff;
import com.perks.emilena.api.type.RoleType;

/**
 * Represents a user of the system
 * <p>
 * Created by Geoff Perks
 * Date: 28/07/2016.
 */
public class User implements Principal {

    private String userName;
    private Staff staff;
    private List<RoleType> roles;

    @Override
    public String getName() {
        return this.userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public List<RoleType> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleType> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(userName, user.userName) &&
                Objects.equals(staff, user.staff) &&
                Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, staff, roles);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userName='").append(userName).append('\'');
        sb.append(", staff=").append(staff);
        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }
}
