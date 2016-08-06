package com.perks.emilena.dao;

import com.perks.emilena.api.Client;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
public class ClientDAO extends AbstractDAO<Client> {

    public ClientDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Client findById(Long id) {
        return get(id);
    }

    public Client create(Client client) {
        return persist(client);
    }

    public List<Client> findAll() {
        return list(namedQuery("com.perks.emilena.Client.findAll"));
    }

    public List<Client> findAllActive() {
        return list(currentSession().createQuery("select c from client c where c.active = true"));
    }
}
