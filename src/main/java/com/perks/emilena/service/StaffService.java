package com.perks.emilena.service;

import com.perks.emilena.api.Client;
import com.perks.emilena.api.Staff;
import com.perks.emilena.dao.ClientDAO;
import com.perks.emilena.dao.StaffDAO;

import java.time.DayOfWeek;
import java.util.List;

/**
 * More a delegate than a service really, but creates a separation from the resources and the DAOs and faciliates
 * adding appropriate logic if necessary
 *
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public class StaffService extends PersonService {

    public StaffService(ClientDAO clientDAO, StaffDAO staffDAO) {
        super(clientDAO, staffDAO);
    }

    public void updateStaff(Staff staff) {
        this.staffDAO.create(transform(staff));
    }

    public void deleteStaff(Staff staff) {
        this.staffDAO.delete(staff);
    }

    public List<Staff> listAllStaff() {
        return this.staffDAO.findAll();
    }

    public List<Staff> listAllActiveStaff() {
        return this.staffDAO.findAllActive();
    }

    public Staff findById(Long id) {
        return this.staffDAO.findById(id);
    }

    public List<Client> clientsByStaffId(Long id) {
        return this.staffDAO.clientsByStaffId(id);
    }

    public List<Staff> joinPersonAvailabilityByDayOfWeek(DayOfWeek dayOfWeek) {
        return this.joinPersonAvailabilityByDayOfWeek(dayOfWeek);
    }
}
