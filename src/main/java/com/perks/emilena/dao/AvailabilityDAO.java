package com.perks.emilena.dao;

import com.perks.emilena.api.Availability;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.Session;
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
        return list(namedQuery("com.perks.emilena.Availability.findAll"));
    }

    public List<Availability> findByPersonId(Long id) {
        Query query = currentSession().createQuery("select a from Availability a where a.person_availability = :id");
        query.setParameter("person_availability", id);
        return list(query);
    }

    public List<Availability> findByPersonForDay(Long id, DayOfWeek dayOfWeek) {
        Query query = currentSession()
                .createQuery("select a from Availability a where a.person_availability = :id and a.day_of_week = :day");
        query.setParameter("person_availability", id);
        query.setParameter("day", dayOfWeek);
        return list(query);
    }

    public List<Availability> findByDay(DayOfWeek dayOfWeek) {
        Query query = currentSession()
                .createQuery("select a from Availability a where a.day_of_week = :day");
        query.setParameter("day", dayOfWeek);
        return list(query);
    }
}
