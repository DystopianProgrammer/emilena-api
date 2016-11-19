package com.perks.emilena.dao;

import com.perks.emilena.api.Traffic;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 *
 * Simply for storing visitor ip addresses.
 *
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class TrafficDAO extends AbstractDAO<Traffic> {

    public TrafficDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Traffic> find() {
        return list(currentSession().createQuery("select t from traffic t"));
    }

    public void create(Traffic traffic) {
        persist(traffic);
    }
}
