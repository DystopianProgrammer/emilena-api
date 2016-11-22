package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Availability;
import com.perks.emilena.dao.AvailabilityDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
@Path("/availability")
@Produces(MediaType.APPLICATION_JSON)
public class AvailabilityResource {

    private final AvailabilityDAO availabilityDAO;

    public AvailabilityResource(AvailabilityDAO availabilityDAO) {
        this.availabilityDAO = availabilityDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public List<Availability> findAll() {
        return this.availabilityDAO.findAll();
    }

    @Path("/{id}")
    @GET
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public List<Availability> findByPersonId(@PathParam("id") Long id) {
        return this.availabilityDAO.findByPersonId(id);
    }

    @Path("detatch")
    @POST
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN"})
    public Response detatch(Availability availability) {
        this.availabilityDAO.delete(availability);
        return Response.ok().build();
    }
}
