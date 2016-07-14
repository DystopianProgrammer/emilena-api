package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Staff;
import com.perks.emilena.dao.StaffDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
public class StaffResource {

    private final StaffDAO staffDAO;

    public StaffResource(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    public Staff findPerson(@PathParam("id") LongParam id) {
        return staffDAO.findById(id.get());
    }
}
