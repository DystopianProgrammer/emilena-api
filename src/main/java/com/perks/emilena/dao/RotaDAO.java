package com.perks.emilena.dao;

import com.perks.emilena.api.Rota;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

/**
 * Created by 466707 on 15/11/2016.
 */
public class RotaDAO extends AbstractDAO<Rota> {

    public RotaDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void update(Rota rota) {
        persist(rota);
    }
}
