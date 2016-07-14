package com.perks.emilena.api;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public abstract class Person implements Serializable {

    private String forename;
    private String surname;
    private String email;
    private LocalDate dob;
    private String telephoneNumber;
    private Address address;

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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
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
}
