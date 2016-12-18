package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Rota;
import com.perks.emilena.dao.RotaDAO;
import com.perks.emilena.service.RotaService;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

/**
 * Created by Geoff Perks
 * Date: 08/09/2016.
 */
@Path("/rota")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RotaResource {

    private static final Logger LOG = LoggerFactory.getLogger(RotaResource.class);
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
        return Response.ok(rotaService.create(requestedDate)).build();
    }

    @GET
    @Path("/unallocated/{id}")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public Response unallocated(@PathParam("id") LongParam id) {
        return Response.ok(rotaService.unallocated(id.get())).build();
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

    @GET
    @Path("/find-by-week/{date}")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public Response findByDate(@PathParam("date") String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate requestedDate = LocalDate.parse(date, formatter);
        TemporalField temporalField = WeekFields.of(Locale.UK).dayOfWeek();
        LocalDate localDate = requestedDate.with(temporalField, 1);
        Long id = null;
        try {
            id = rotaDAO.findByWeekCommencing(localDate).stream().map(r -> r.getId()).reduce(Long::max).get();
        } catch (NoSuchElementException e) {
            LOG.error("Could not get rota with id {}", id, e);
        }
        if (id != null) {
            return Response.ok(rotaDAO.findById(id)).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/weeks")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public Response weeks() {
        return Response.ok(this.rotaService.weeks()).build();
    }
}
