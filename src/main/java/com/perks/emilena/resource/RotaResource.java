package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Geoff Perks
 * Date: 08/09/2016.
 */
@Path("/rota")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RotaResource {

    @GET
    @Path("/{date}")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public Response rota(@PathParam("date") String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return Response.ok().build();
    }
}
