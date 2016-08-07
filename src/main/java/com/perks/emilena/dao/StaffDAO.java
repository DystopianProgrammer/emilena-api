package com.perks.emilena.dao;

import com.perks.emilena.api.Client;
import com.perks.emilena.api.Staff;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
public class StaffDAO extends AbstractDAO<Staff> {


    public StaffDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Staff findById(Long id) {
        return get(id);
    }

    public Staff create(Staff staff) {
        return persist(staff);
    }

    public void delete(Staff staff) {
        currentSession().delete(staff);
    }

    public List<Client> clientsByStaffId(Long id) {
        Query query = currentSession().createQuery("select s from Staff s join s.clients c");
        return query.list();
    }

    public List<Staff> findAll() {
        return list(namedQuery("com.perks.emilena.Staff.findAll"));
    }

    public List<Staff> findAllActive() {
        Query query = currentSession().createQuery("select s from Staff s where s.active = :isActive");
        query.setParameter("isActive", true);
        return list(query);
    }
}
