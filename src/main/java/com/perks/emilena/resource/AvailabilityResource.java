package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Availability;
import com.perks.emilena.dao.AvailabilityDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    public List<Availability> findAll() {
        return null;
    }
}
