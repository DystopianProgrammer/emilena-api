package com.perks.emilena.service;

import com.perks.emilena.api.Person;
import com.perks.emilena.dao.ClientDAO;
import com.perks.emilena.dao.StaffDAO;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.lowerCase;

/**
 * More a delegate than a service really, but creates a separation from the resources and the DAOs and faciliates
 * adding appropriate logic if necessary
 *
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public abstract class PersonService {

    protected final ClientDAO clientDAO;
    protected final StaffDAO staffDAO;

    public PersonService(ClientDAO clientDAO, StaffDAO staffDAO) {
        this.clientDAO = clientDAO;
        this.staffDAO = staffDAO;
    }

    protected <E extends Person> E transform(E person) {
        requireNonNull(person, "person must not be null");
        requireNonNull(person.getForename(), "forename must not be null");
        requireNonNull(person.getSurname(), "surname must not be null");

        String forename = capitalise(person.getForename());
        String surname = capitalise(person.getSurname());
        person.setForename(forename);
        person.setSurname(surname);
        return person;
    }

    private String capitalise(String word) {
        String modified = lowerCase(word);
        return capitalize(modified);
    }
}
