package com.perks.emilena.service;

import com.perks.emilena.api.Appointment;
import com.perks.emilena.dao.AppointmentDAO;
import com.perks.emilena.value.Alerts;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Geoff Perks
 * Date: 04/09/2016.
 */
public class AlertsService {

    private final AppointmentDAO appointmentDAO;

    public AlertsService(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    /**
     * Fetches all appointments by staff id and maps them to a wrapper 'Alerts'. The separation is
     * by splitting appointments into past appointments which require an action such as whether the appointment
     * took place or not for invoicing purposes. The other list is simply to notify the staff of upcoming appointments
     *
     * @param id the staff id
     * @return a wrapper object 'alerts' with separated appointments for both past and future which have not been completed
     */
    public Alerts sortAppointmentsByStaffId(Long id) {

        List<Appointment> appointments = this.appointmentDAO.listByStaffId(id);

        Predicate<Appointment> reverseCheck = (appt) -> {
            if (appt.getIsComplete() == null) {
                return true;
            }
            return !appt.getIsComplete();
        };

        List<Appointment> pendingList = appointments.stream()
                .filter(reverseCheck)
                .filter(appt -> appt.getFromDate().before(Date.from(Instant.now())))
                .collect(Collectors.toList());

        List<Appointment> futureList = appointments.stream()
                .filter(reverseCheck)
                .filter(appt -> appt.getFromDate().after(Date.from(Instant.now())))
                .collect(Collectors.toList());


        return new Alerts(pendingList, futureList);
    }
}
