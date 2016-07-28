package com.perks.emilena.dao;

import com.perks.emilena.api.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

/**
 * Created by Geoff Perks
 * Date: 28/07/2016.
 */
public class UserDAO extends AbstractDAO<User> {


    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public boolean login(User user) {
        System.out.println("TODO" + user);
        return false;
    }
}
