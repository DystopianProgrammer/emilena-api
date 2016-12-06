package com.perks.emilena.value;

import com.perks.emilena.api.Availability;
import com.perks.emilena.api.Person;
import com.perks.emilena.api.type.PersonType;

import static java.util.Objects.requireNonNull;

/**
 *
 * A Temporal store is a wrapper for a potential appointment. This operates for a specific day as it contains a single
 * availability. This is useful when there is a many to one relationship between availability and person. It enables
 * us to reduce a single availability per day and operate on that person accordingly for staging appointments.
 *
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public class TemporalStore {

    private final Person person;
    private final PersonType personType;
    private final Availability availability;
    private final String postCode;

    public TemporalStore(Person person, PersonType personType, Availability availability, String postCode) {
        this.person = requireNonNull(person, "person must not be null");
        this.personType = requireNonNull(personType, "person type must not be null");
        this.availability = requireNonNull(availability, "availability must not be null");
        this.postCode = requireNonNull(postCode, "post code must not be null");
    }

    public Person getPerson() {
        return person;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public Availability getAvailability() {
        return availability;
    }

    public String getPostCode() {
        return postCode;
    }
}
