package com.perks.emilena.dao;

import com.perks.emilena.api.Absence;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
public class AbsenceDAO extends AbstractDAO<Absence> {

    public AbsenceDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Absence update(Absence absence) {
        return persist(absence);
    }

    public List<Absence> findAll() {
        return list(currentSession().createQuery("select a from Absence a"));
    }
}
