package com.perks.emilena.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.perks.emilena.api.Absence;

import io.dropwizard.hibernate.AbstractDAO;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
public class AbsenceDAO extends AbstractDAO<Absence> {

    public AbsenceDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Absence> findAll() {
        return list(namedQuery("com.perks.emilena.Absence.findAll"));
    }
}
