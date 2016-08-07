package com.perks.emilena.service;

import com.perks.emilena.api.Staff;
import com.perks.emilena.dao.StaffDAO;

/**
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public class StaffService implements PersonService<Staff> {

    private final StaffDAO staffDAO;

    public StaffService(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    @Override
    public Staff create(Staff person) {
        person.setActive(true);
        return staffDAO.create(person);
    }

    @Override
    public StaffDAO getDataAccessObject() {
        return staffDAO;
    }
}
