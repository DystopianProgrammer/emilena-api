package com.perks.emilena.dao;

import com.perks.emilena.api.Availability;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
public class AvailabilityDAO extends AbstractDAO<Availability> {

    public AvailabilityDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Availability> findAll() {
        return list(namedQuery("com.perks.emilena.Availability.findAll"));
    }
}
