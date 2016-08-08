package com.perks.emilena.service;

import com.perks.emilena.api.Appointment;

import java.util.List;

/**
 *
 * Service for creating and retrieving appointments
 *
 * Created by Geoff Perks
 * Date: 08/08/2016.
 */
public interface AppointmentService<T extends Appointment> {

    /**
     * Creates/Updates a new/existing appointment. This checks that it doesn't overlap with existing appointments
     * for both Staff and Clients
     * @param appointment - the appointment to create or update
     * @return the updated/created appointment
     */
    T create(T appointment);

    /**
     * Cancels an appointment. This does not remove it from the database, but simply marks it as cancelled.
     * @param id - The appointment ID
     * @return The cancelled appointment
     */
    T cancel(Long id);

    /**
     * Lists all active appointments - that is appointments not marked as complete
     * @return all active appointments
     */
    List<T> allActive();

    /**
     * Lists all appointments - both complete and incomplete
     * @return
     */
    List<T> all();

    /**
     * Gets the appointment by Id
     * @param id the ID of the appointments
     * @return the Appointment
     */
    T fetchById(Long id);
}
