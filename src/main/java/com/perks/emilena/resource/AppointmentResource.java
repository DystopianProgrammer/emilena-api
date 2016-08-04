package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Appointment;
import com.perks.emilena.dao.AppointmentDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Geoff Perks
 * Date: 03/08/2016.
 */
@Path("/appointment")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AppointmentResource {

    private final AppointmentDAO appointmentDAO;

    public AppointmentResource(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    @POST
    @Path("/add")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"STAFF"})
    public Appointment add(@Valid Appointment appointment) {
        return this.appointmentDAO.persist(appointment);
    }

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Appointment get(@PathParam("id") LongParam id) {
        return this.appointmentDAO.get(id.get());
    }
}
