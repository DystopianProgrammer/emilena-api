package com.perks.emilena.dao;

import com.perks.emilena.api.Client;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.time.DayOfWeek;
import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
public class ClientDAO extends AbstractDAO<Client> {

    public ClientDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void create(Client client) {
        persist(client);
    }

    public Client findById(Long id) {
        return get(id);
    }

    public List<Client> findAll() {
        return list(currentSession().createQuery("select c from Client c"));
    }

    public List<Client> findAllActive() {
        Query query = currentSession().createQuery("select c from Client c where c.active = :isActive");
        query.setParameter("isActive", true);
        return list(query);
    }

    public List<Client> clientAvailabilityByDayOfWeek(DayOfWeek dayOfWeek) {
        Query query = currentSession().createQuery(
                "select c from Client c join c.availabilities a where a.dayOfWeek = :dayOfWeek");
        query.setParameter("dayOfWeek", dayOfWeek);
        return list(query);
    }

    public void delete(Client client) {
        currentSession().delete(client);
    }
}
