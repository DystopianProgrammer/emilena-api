package com.perks.emilena.value;

import com.perks.emilena.api.Appointment;

import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 04/09/2016.
 */
public class Alerts {

    private final List<Appointment> pendingAlerts;
    private final List<Appointment> futureAlerts;

    public Alerts(List<Appointment> pendingAlerts, List<Appointment> futureAlerts) {
        this.pendingAlerts = pendingAlerts;
        this.futureAlerts = futureAlerts;
    }

    public List<Appointment> getPendingAlerts() {
        return pendingAlerts;
    }

    public List<Appointment> getFutureAlerts() {
        return futureAlerts;
    }
}
