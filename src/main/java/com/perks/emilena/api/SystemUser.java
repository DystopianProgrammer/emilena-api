package com.perks.emilena.api;

import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

/**
 * A user of the system - this is linked to some staff
 *
 * Created by Geoff Perks
 * Date: 28/07/2016.
 */
@Entity
@Table(name = "system_user")
public class SystemUser {

    @Id
    @Column(name = "su_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;


    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "password", nullable = false)
    @Length(min = 8)
    private String password;

    @OneToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "system_user_role", joinColumns = {
            @JoinColumn(name = "system_user_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "role_id",
                    nullable = false, updatable = false) })
    private List<Role> roles;

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
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
        SystemUser that = (SystemUser) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(password, that.password) &&
                Objects.equals(staff, that.staff) &&
                Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, staff, roles);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SystemUser{");
        sb.append("id=").append(id);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", staff=").append(staff);
        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }
}
