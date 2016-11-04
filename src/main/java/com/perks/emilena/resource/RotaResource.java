package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Rota;
import com.perks.emilena.service.RotaService;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Geoff Perks
 * Date: 08/09/2016.
 */
@Path("/rota")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RotaResource {

    private final RotaService rotaService;

    public RotaResource(RotaService rotaService) {
        this.rotaService = rotaService;
    }

    @GET
    @Path("/{date}")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public Rota rota(@PathParam("date") String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return this.rotaService.rotaForWeek(localDate);
    }
}
