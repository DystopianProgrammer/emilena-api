package com.perks.emilena.dao;

import com.perks.emilena.api.RotaItem;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
public class RotaItemDAO extends AbstractDAO<RotaItem>{

    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    public RotaItemDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void update(RotaItem rotaItem) {
        persist(rotaItem);
    }
}
