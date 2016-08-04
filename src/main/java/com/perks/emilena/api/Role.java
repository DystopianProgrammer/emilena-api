package com.perks.emilena.api;

import com.perks.emilena.api.type.RoleType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

/**
 * ROLE for system access, this can either be SYSTEM or ADMIN
 *
 * Created by Geoff Perks
 * Date: 29/07/2016.
 */
@Entity
@Table(name = "role")
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "role_type")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    private List<SystemUser> systemUsers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public List<SystemUser> getSystemUsers() {
        return systemUsers;
    }

    public void setSystemUsers(List<SystemUser> systemUsers) {
        this.systemUsers = systemUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                roleType == role.roleType &&
                Objects.equals(systemUsers, role.systemUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleType, systemUsers);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Role{");
        sb.append("id=").append(id);
        sb.append(", roleType=").append(roleType);
        sb.append(", systemUsers=").append(systemUsers);
        sb.append('}');
        return sb.toString();
    }
}
