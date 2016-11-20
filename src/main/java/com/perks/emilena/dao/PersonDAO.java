package com.perks.emilena.dao;

import com.perks.emilena.api.Person;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
public class PersonDAO extends AbstractDAO<Person> {

    public PersonDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Person findPersonById(Long id) {
        return get(id);
    }

    public void delete(Person p) {
        currentSession().delete(p);
    }

    public List<Person> listActive() {
        return list(currentSession().createQuery("select p from Person p"));
    }
}
