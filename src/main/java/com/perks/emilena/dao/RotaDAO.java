package com.perks.emilena.dao;

import com.perks.emilena.api.Rota;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
public class RotaDAO extends AbstractDAO<Rota> {

    public RotaDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Rota update(Rota rota) {
        return persist(rota);
    }

    public List<Rota> fetchAll() {
        return list(currentSession().createQuery("select r from Rota r"));
    }

    public List<Rota> findByWeekCommencing(LocalDate weekStarting) {
        Query query = currentSession().createQuery("select r from Rota r where r.weekStarting = :weekStarting");
        query.setParameter("weekStarting", weekStarting);
        return list(query);
    }

    public Rota findById(Long id) {
        Query query = currentSession().createQuery("select r from Rota r where r.id = :id");
        query.setParameter("id", id);
        return (Rota) query.uniqueResult();
    }

    public void delete(Rota rota) {
        currentSession().delete(rota);
    }
}
