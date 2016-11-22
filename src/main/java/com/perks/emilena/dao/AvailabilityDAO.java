package com.perks.emilena.dao;

import com.perks.emilena.api.Availability;
import com.perks.emilena.api.Person;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.time.DayOfWeek;
import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
public class AvailabilityDAO extends AbstractDAO<Availability> {

    public AvailabilityDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void update(Availability availability) {
        persist(availability);
    }

    public List<Availability> findAll() {
        return list(currentSession().createQuery("select a from Availability a"));
    }

    public List<Availability> findByPersonId(Long id) {
        Query query = currentSession().createQuery("select a from Availability a where a.person_availability = :id");
        query.setParameter("person_availability", id);
        return list(query);
    }

    public void delete(Availability availability) {
        currentSession().delete(availability);
    }

    public <T extends Person> List<Availability> findByDay(DayOfWeek dayOfWeek, Class<T> person) {
        Query query = currentSession()
                .createQuery("select a from Availability a join a.person p where a.dayOfWeek = :day and type(p) = :person");
        query.setParameter("day", dayOfWeek);
        query.setParameter("person", person);
        return list(query);
    }

}
