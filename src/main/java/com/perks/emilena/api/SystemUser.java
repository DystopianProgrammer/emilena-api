package com.perks.emilena.api;

import com.perks.emilena.api.type.RoleType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
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
public class SystemUser implements Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "password", nullable = false)
    @Length(min = 8)
    private String password;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_id")
    private Person person;

    @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)
    @JoinTable(name = "tbl_roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "roles", nullable = false)
    @Enumerated(EnumType.STRING)
    private Collection<RoleType> roleTypes;

    /**
     * Returns the name of this principal.
     *
     * @return the name of this principal.
     */
    @Override
    @Transient
    public String getName() {
        return this.userName;
    }

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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Collection<RoleType> getRoleTypes() {
        return roleTypes;
    }

    public void setRoleTypes(Collection<RoleType> roleTypes) {
        this.roleTypes = roleTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemUser that = (SystemUser) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(password, that.password) &&
                Objects.equals(person, that.person) &&
                Objects.equals(roleTypes, that.roleTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, person, roleTypes);
    }

}
