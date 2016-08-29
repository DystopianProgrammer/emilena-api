package com.perks.emilena.dao;

import com.google.common.base.Preconditions;
import com.perks.emilena.api.SystemUser;
import io.dropwizard.hibernate.AbstractDAO;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.validation.Valid;
import java.io.Serializable;

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

        Preconditions.checkNotNull(StringUtils.isNoneBlank(userName), "userName cannot be null or empty");

        Session session = sessionFactory.openSession();
        Query query =
                session.createQuery("select s from SystemUser s where s.userName = :email");
        query.setParameter("email", userName);
        SystemUser systemUser = (SystemUser)query.uniqueResult();
        session.close();

        return systemUser;
    }

    /**
     * As this is not directly accessible via a UnitOfWork by means of a resource, this needs to attach to the
     * transaction, so the db current session has to be obtained. If no session can be attached, an exception is thrown.
     *
     * @param systemUser - the system user
     * @return the system user
     */
    public Serializable create(@Valid SystemUser systemUser) {
        Session session = sessionFactory.getCurrentSession();
        return session.save(systemUser);
    }
}
