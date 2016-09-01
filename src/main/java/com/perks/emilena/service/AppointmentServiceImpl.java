package com.perks.emilena.service;

import com.google.common.collect.Lists;
import com.perks.emilena.api.Appointment;
import com.perks.emilena.api.Staff;
import com.perks.emilena.dao.AppointmentDAO;
import com.perks.emilena.dao.StaffDAO;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Geoff Perks
 * Date: 08/08/2016.
 */
public class AppointmentServiceImpl implements AppointmentService<Appointment> {

    private final AppointmentDAO appointmentDAO;
    private final StaffDAO staffDAO;

    public AppointmentServiceImpl(AppointmentDAO appointmentDAO, StaffDAO staffDAO) {
        this.appointmentDAO = appointmentDAO;
        this.staffDAO = staffDAO;
    }

    /**
     * Creates/Updates a new/existing appointment. This checks that it doesn't overlap with existing appointments
     * for both Staff and Clients
     *
     * @param appointment - the appointment to create or update
     * @return the updated/created appointment
     */
    @Override
    public Appointment create(Appointment appointment) {
        return appointmentDAO.persist(appointment);
    }

    /**
     * Cancels an appointment. This does not remove it from the database, but simply marks it as cancelled.
     *
     * @param id - The appointment ID
     * @return The cancelled appointment
     */
    @Override
    public Appointment cancel(Long id) {

        throw new UnsupportedOperationException("Service method not yet implemented");
    }

    /**
     * Lists all active appointments - that is appointments not marked as complete
     *
     * @return all active appointments
     */
    @Override
    public List<Appointment> allActive() {
        return appointmentDAO.listIncomplete();
    }

    /**
     * Lists all appointments - both complete and incomplete
     *
     * @return
     */
    @Override
    public List<Appointment> all() {
        return appointmentDAO.findAll();
    }

    /**
     * Gets the appointment by Id
     *
     * @param id the ID of the appointments
     * @return the Appointment
     */
    @Override
    public Appointment fetchById(Long id) {
        return appointmentDAO.get(id);
    }

    /**
     * Gets all active appointments by staff id. Unset appointment complete statuses i.e. nulls are regarded as actionable
     *
     * @param id
     * @return
     */
    @Override
    public List<Appointment> activeStaffAppointments(Long id) {
        Staff staff = staffDAO.findById(id);

        Predicate<Appointment> incompleteAppointment = (appt) -> {
            if (appt.getComplete() == null) {
                return true;
            }
            return !appt.getComplete();
        };

        return Lists.newArrayList(staff.getAppointments()).stream()
                .filter(incompleteAppointment)
                .collect(Collectors.toList());
    }
}
