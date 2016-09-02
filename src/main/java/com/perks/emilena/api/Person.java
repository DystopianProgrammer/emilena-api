package com.perks.emilena.api;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "person")
public abstract class Person implements Serializable {

	private static final long serialVersionUID = 1173286545163491540L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

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

    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
    private Collection<Availability> availabilities;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Absence> absences;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private GeneralAvailability generalAvailability;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Appointment> appointments;

    @Column(length = 1000)
    private String preferences;

    /**
     * Used to indicate whether the person is active or not - rather than deleting the person.
     */
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

    public Collection<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(Collection<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public Collection<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(Collection<Absence> absences) {
        this.absences = absences;
    }

    public GeneralAvailability getGeneralAvailability() {
        return generalAvailability;
    }

    public void setGeneralAvailability(GeneralAvailability generalAvailability) {
        this.generalAvailability = generalAvailability;
    }

    public Collection<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Collection<Appointment> appointments) {
        this.appointments = appointments;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(forename, person.forename) &&
                Objects.equals(surname, person.surname) &&
                Objects.equals(email, person.email) &&
                Objects.equals(dob, person.dob) &&
                Objects.equals(telephoneNumber, person.telephoneNumber) &&
                Objects.equals(address, person.address) &&
                Objects.equals(active, person.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, forename, surname, email, dob, telephoneNumber, address, active);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("id=").append(id);
        sb.append(", forename='").append(forename).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", dob=").append(dob);
        sb.append(", telephoneNumber='").append(telephoneNumber).append('\'');
        sb.append(", address=").append(address);
        sb.append(", availabilities=").append(availabilities);
        sb.append(", absences=").append(absences);
        sb.append(", generalAvailability=").append(generalAvailability);
        sb.append(", appointments=").append(appointments);
        sb.append(", preferences='").append(preferences).append('\'');
        sb.append(", active=").append(active);
        sb.append('}');
        return sb.toString();
    }
}
