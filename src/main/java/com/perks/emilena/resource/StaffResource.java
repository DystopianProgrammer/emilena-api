package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.Staff;
import com.perks.emilena.dao.StaffDAO;
import com.perks.emilena.service.PersonService;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.DayOfWeek;
import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
@Path("/staff")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StaffResource {

    private final StaffDAO staffDAO;
    private final PersonService personService;

    public StaffResource(StaffDAO staffDAO, PersonService personService) {
        this.staffDAO = staffDAO;
        this.personService = personService;
    }

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public Staff findPerson(@PathParam("id") LongParam id) {
        return staffDAO.findById(id.get());
    }

    @Path("/all")
    @GET
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public List<Staff> findAll() {
       return staffDAO.findAll();
    }

    @POST
    @Path("/update")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN"})
    public Response update(@Valid Staff staff) {
        personService.update(staff);
        return Response.ok().build();
    }

    @Path("/clients/{id}")
    @GET
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public List<Client> clientsFromStaffId(@PathParam("id") LongParam id) {
        return staffDAO.clientsByStaffId(id.get());
    }

    @Path("/availability/{day}")
    @GET
    @Timed
    @UnitOfWork
    public List<Staff> availabilityFromDay(@PathParam("day") String dayOfWeek) {
        return staffDAO.joinPersonAvailabilityByDayOfWeek(DayOfWeek.valueOf(dayOfWeek.toUpperCase()));
    }

    @Path("/active")
    @GET
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public List<Staff> findAllActive() {
        return staffDAO.findAllActive();
    }

}
