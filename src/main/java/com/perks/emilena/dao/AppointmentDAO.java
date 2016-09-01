package com.perks.emilena.dao;

import com.perks.emilena.api.Appointment;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 03/08/2016.
 */
public class AppointmentDAO extends AbstractDAO<Appointment> {

    public AppointmentDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * Either save or update the given instance, depending upon resolution of the unsaved-value
     * checks (see the manual for discussion of unsaved-value checking).
     * <p/>
     * This operation cascades to associated instances if the association is mapped with
     * <tt>cascade="save-update"</tt>.
     *
     * @param entity a transient or detached instance containing new or updated state
     * @throws HibernateException
     * @see Session#saveOrUpdate(Object)
     */
    @Override
    public Appointment persist(Appointment entity) throws HibernateException {
        return super.persist(entity);
    }

    /**
     * Return the persistent instance of {@code <E>} with the given identifier, or {@code null} if
     * there is no such persistent instance. (If the instance, or a proxy for the instance, is
     * already associated with the session, return that instance or proxy.)
     *
     * @param id an identifier
     * @return a persistent instance or {@code null}
     * @throws HibernateException
     * @see Session#get(Class, Serializable)
     */
    @Override
    public Appointment get(Serializable id) {
        return super.get(id);
    }

    /**
     * Returns all appointments, both completed an incomplete
     *
     * @return list of appointments
     */
    public List<Appointment> findAll() {
        return list(currentSession().createQuery("select a from Appointment a"));
    }

    /**
     * Returns all appointments which are yet to be completed
     *
     * @return list of appointments
     */
    public List<Appointment> listIncomplete() {
        Query query = currentSession().createQuery("select a from Appointment a where a.complete = :complete");
        query.setParameter("complete", false);
        return list(query);
    }


}
