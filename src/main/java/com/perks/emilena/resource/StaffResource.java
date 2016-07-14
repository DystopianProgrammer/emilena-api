package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Staff;
import com.perks.emilena.dao.StaffDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

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

    public StaffResource(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    @Path("/{id}")
    @GET
    @Timed
    @UnitOfWork
    public Staff findPerson(@PathParam("id") LongParam id) {
        return staffDAO.findById(id.get());
    }

    @Path("/all")
    @GET
    @Timed
    @UnitOfWork
    public List<Staff> findAll() {
        return staffDAO.findAll();
    }

    @Path("/add")
    @POST
    @Timed
    @UnitOfWork
    public Long add(Staff staff) {
        return staffDAO.create(staff);
    }
}
