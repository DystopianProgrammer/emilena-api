package com.perks.emilena.validator;

import com.perks.emilena.api.Appointment;
import com.perks.emilena.dao.AppointmentDAO;

/**
 * Created by Geoff Perks
 * Date: 06/09/2016.
 */
public class AppointmentValidator implements Validator<Appointment> {

    private final AppointmentDAO appointmentDAO;

    public AppointmentValidator(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    @Override
    public Boolean isValid(Appointment object) {
        return null;
    }
}
