package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Rota;
import com.perks.emilena.dao.RotaDAO;
import com.perks.emilena.service.RotaService;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.next;

/**
 * Created by Geoff Perks
 * Date: 08/09/2016.
 */
@Path("/rota")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RotaResource {

    private final RotaService rotaService;
    private final RotaDAO rotaDAO;

    public RotaResource(RotaService rotaService, RotaDAO rotaDAO) {
        this.rotaService = rotaService;
        this.rotaDAO = rotaDAO;
    }

    @GET
    @Path("/{date}")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public Response rota(@PathParam("date") String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
        LocalDate requestedDate = LocalDate.parse(date, formatter);

        // first check that we haven't got a generated rota for this week.
        LocalDate weekCommencing = requestedDate.with(next(DayOfWeek.MONDAY));
        List<Rota> commencing = rotaDAO.findByWeekCommencing(weekCommencing);

        if(commencing.isEmpty()) {
            return Response.ok(rotaService.create(weekCommencing)).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @GET
    @Path("/unallocated/{id}")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public List<RotaService.Unallocated> unallocated(@PathParam("id") LongParam id) {
        return rotaService.unallocated(id.get());
    }

    @POST
    @Path("/update")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN"})
    public Rota update(@Valid Rota rota) {
        return this.rotaDAO.update(rota);
    }

    @POST
    @Path("/delete")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN"})
    public Response delete(@Valid Rota rota) {
        this.rotaDAO.delete(rota);
        return Response.accepted().build();
    }

    @GET
    @Path("/all")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public List<Rota> rota() {
        return rotaDAO.fetchAll();
    }

    @GET
    @Path("/find/{id}")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public Rota findById(@PathParam("id") LongParam id) {
        return this.rotaDAO.findById(id.get());
    }
}
