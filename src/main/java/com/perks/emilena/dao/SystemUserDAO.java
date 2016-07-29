package com.perks.emilena.dao;

import com.perks.emilena.api.SystemUser;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Geoff Perks
 * Date: 28/07/2016.
 */
public class SystemUserDAO extends AbstractDAO<SystemUser> {

    /**
     * FIXME See http://www.dropwizard.io/1.0.0/docs/manual/hibernate.html#transactional-resource-methods
     *
     * Currently the proxy for unit of work is not working. The work around is to manually open a new session
     */
    private final SessionFactory sessionFactory;

    public SystemUserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    public SystemUser findByUserName(String userName) {

        Session session = sessionFactory.openSession();

        Query query =
                session.createQuery("select s from SystemUser s where s.userName = :email");
        query.setParameter("email", userName);

        SystemUser systemUser = (SystemUser)query.uniqueResult();

        session.close();

        return systemUser;
    }
}
