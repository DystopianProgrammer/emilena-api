package com.perks.emilena.api;

import com.perks.emilena.api.type.PersonType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
@Table(name = "PERSON")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "P")
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PersonType personType;

    @NotNull
    @Column(name = "forename")
    private String forename;

    @NotNull
    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "date_of_birth")
    private Date dob;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Availability> availabilities;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Absence> absences;

    @Column(length = 1000)
    private String preferences;

    @Column(name = "active")
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(forename, person.forename) &&
                Objects.equals(surname, person.surname) &&
                Objects.equals(email, person.email) &&
                Objects.equals(dob, person.dob) &&
                Objects.equals(telephoneNumber, person.telephoneNumber) &&
                Objects.equals(address, person.address) &&
                Objects.equals(availabilities, person.availabilities) &&
                Objects.equals(absences, person.absences) &&
                Objects.equals(preferences, person.preferences) &&
                Objects.equals(active, person.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, forename, surname, email, dob, telephoneNumber, address, availabilities, absences, preferences, active);
    }

}
