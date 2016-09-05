package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.service.AlertsService;
import com.perks.emilena.value.Alerts;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Geoff Perks
 * Date: 04/09/2016.
 */
@Path("/alerts")
@Produces(MediaType.APPLICATION_JSON)
public class AlertsResource {

    private final AlertsService alertsService;

    public AlertsResource(AlertsService alertsService) {
        this.alertsService = alertsService;
    }

    @GET
    @Path("/staff/{id}/appointments")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public Alerts alertsByStaffId(@PathParam("id") LongParam id) {
        return this.alertsService.sortAppointmentsByStaffId(id.get());
    }
}
