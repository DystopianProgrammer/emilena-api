package com.perks.emilena.api;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person implements Serializable {

    @NotNull
    @Column(name = "FORENAME")
    private String forename;

    @NotNull
    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DATE_OF_BIRTH")
    private Date dob;

    @Column(name = "TELEPHONE_NUMBER")
    private String telephoneNumber;

    @Embedded
    private Address address;

    @OneToMany(mappedBy="person")
    private List<Availability> availability;

    @Column(length = 1000)
    private String preferences;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Availability> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Availability> availability) {
        this.availability = availability;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return Objects.equals(forename, person.forename) &&
                Objects.equals(surname, person.surname) &&
                Objects.equals(email, person.email) &&
                Objects.equals(dob, person.dob) &&
                Objects.equals(telephoneNumber, person.telephoneNumber) &&
                Objects.equals(address, person.address) &&
                Objects.equals(availability, person.availability) &&
                Objects.equals(preferences, person.preferences) &&
                Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(forename, surname, email, dob, telephoneNumber, address, availability, preferences, id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("forename='").append(forename).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", dob=").append(dob);
        sb.append(", telephoneNumber='").append(telephoneNumber).append('\'');
        sb.append(", address=").append(address);
        sb.append(", availability=").append(availability);
        sb.append(", preferences='").append(preferences).append('\'');
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
