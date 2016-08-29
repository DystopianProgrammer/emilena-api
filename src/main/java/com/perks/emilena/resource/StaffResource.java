package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.Staff;
import com.perks.emilena.dao.StaffDAO;
import com.perks.emilena.service.StaffService;
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
    private final StaffService staffService;

    public StaffResource(StaffService staffService, StaffDAO staffDAO) {
        this.staffService = staffService;
        this.staffDAO = staffDAO;
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
    @Path("/add")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN"})
    public Staff add(@Valid Staff staff) {
        return staffService.create(staff);
    }

    @POST
    @Path("/update")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN"})
    public Staff update(@Valid Staff staff) {
        return staffDAO.update(staff);
    }

    @Path("/staff/clients/{id}")
    @GET
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public List<Client> clientsFromStaffId(@PathParam("id") LongParam id) {
        return staffDAO.clientsByStaffId(id.get());
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
