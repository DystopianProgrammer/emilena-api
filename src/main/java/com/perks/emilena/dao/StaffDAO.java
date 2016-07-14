package com.perks.emilena.dao;

import com.perks.emilena.api.Staff;
import io.dropwizard.hibernate.AbstractDAO;
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

    public long create(Staff staff) {
        return persist(staff).getId();
    }

    public List<Staff> findAll() {
        return list(namedQuery("com.perks.emilena.Staff.findAll"));
    }
}
