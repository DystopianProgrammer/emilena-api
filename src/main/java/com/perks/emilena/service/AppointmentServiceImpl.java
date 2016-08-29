package com.perks.emilena.service;

import java.util.List;

import com.perks.emilena.api.Appointment;
import com.perks.emilena.dao.AppointmentDAO;

/**
 * Created by Geoff Perks
 * Date: 08/08/2016.
 */
public class AppointmentServiceImpl implements AppointmentService<Appointment> {

    private final AppointmentDAO appointmentDAO;

    public AppointmentServiceImpl(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
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
        throw new UnsupportedOperationException("Service method not yet implemented");
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
}
