package com.perks.emilena.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.perks.emilena.api.Availability;

import io.dropwizard.hibernate.AbstractDAO;

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
