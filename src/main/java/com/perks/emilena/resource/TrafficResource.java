package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Traffic;
import com.perks.emilena.dao.TrafficDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * Simply for storing visitor ip addresses.
 *
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Path("/traffic")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TrafficResource {

    private final TrafficDAO trafficDAO;

    public TrafficResource(TrafficDAO trafficDAO) {
        this.trafficDAO = trafficDAO;
    }

    @GET
    @Path("/all}")
    @Timed
    @UnitOfWork
    public List<Traffic> find() {
        return this.trafficDAO.find();
    }

    @POST
    @Path("/update")
    @Timed
    @UnitOfWork
    public Response update(@Valid Traffic traffic) {
        traffic.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        this.trafficDAO.create(traffic);
        return Response.ok().build();
    }
}
