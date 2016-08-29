package com.perks.emilena.api;

import com.perks.emilena.api.type.RoleType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;
import java.util.Objects;

/**
 * A user of the system - this is linked to some staff
 *
 * Created by Geoff Perks
 * Date: 28/07/2016.
 */
@Entity
@Table(name = "system_user")
public class SystemUser implements Serializable, Principal {

    private static final long serialVersionUID = 1273286745163391540L;

    @Id
    @Column(name = "su_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "password", nullable = false)
    @Length(min = 8)
    private String password;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)
    @JoinTable(name = "tbl_roles", joinColumns = @JoinColumn(name = "su_id"))
    @Column(name = "roles", nullable = false)
    @Enumerated(EnumType.STRING)
    private Collection<RoleType> roleTypes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Collection<RoleType> getRoleTypes() {
        return roleTypes;
    }

    public void setRoleTypes(Collection<RoleType> roleTypes) {
        this.roleTypes = roleTypes;
    }

    /**
     * Returns the name of this principal.
     *
     * @return the name of this principal.
     */
    @Override
    @Transient
    public String getName() {
        return this.getUserName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SystemUser that = (SystemUser) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(password, that.password) &&
                Objects.equals(staff, that.staff) &&
                Objects.equals(roleTypes, that.roleTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, staff, roleTypes);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SystemUser{");
        sb.append("id=").append(id);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", staff=").append(staff);
        sb.append(", roleType=").append(roleTypes);
        sb.append('}');
        return sb.toString();
    }
}
