package com.perks.emilena.dao;

import com.perks.emilena.api.Rota;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public Optional<Rota> findByWeekCommencing(LocalDate weekStarting) {
        Rota rota = null;
        try {
            Query query = currentSession()
                    .createQuery("select r from Rota r where r.weekStarting = :weekStarting");
            query.setParameter("weekStarting", weekStarting);
            rota = (Rota) query.uniqueResult();
        } catch (Exception e) {
            // no results, we're ok
        }
        return Optional.ofNullable(rota);
    }

    public Rota findById(Long id) {
        Query query = currentSession().createQuery("select r from Rota r where r.id = :id");
        query.setParameter("id", id);
        return (Rota) query.uniqueResult();
    }
}
