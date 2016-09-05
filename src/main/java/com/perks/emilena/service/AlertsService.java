package com.perks.emilena.service;

import com.perks.emilena.api.Appointment;
import com.perks.emilena.dao.AppointmentDAO;
import com.perks.emilena.value.Alerts;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 04/09/2016.
 */
public class AlertsService {

    private final AppointmentDAO appointmentDAO;

    public AlertsService(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    public Alerts sortAppointmentsByStaffId(Long id) {
        List<Appointment> appointments = this.appointmentDAO.listByStaffId(id);

        List<Appointment> pending = new ArrayList<>();
        List<Appointment> future = new ArrayList<>();
        appointments.stream().forEach(appt -> {
            if (appt.getToDate().before(Date.from(Instant.now()))) {
                pending.add(appt);
            } else {
                future.add(appt);
            }
        });

        return new Alerts(pending, future);
    }
}
