package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Appointment;
import com.perks.emilena.service.AppointmentService;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Geoff Perks
 * Date: 01/09/2016.
 */
@Path("/invoice")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InvoiceResource {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceResource.class);

    private final AppointmentService<Appointment> appointmentService;

    public InvoiceResource(AppointmentService<Appointment> appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GET
    @Path("/staff/{id}/appointments")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public Response alertsByStaffId(@PathParam("id") LongParam id) {
        try {
            return Response.ok(appointmentService.activeStaffAppointments(id.get())).build();
        } catch (Exception e) {
            logger.error("Unable to generate alerts for staff id {}", id.get(), e);
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
