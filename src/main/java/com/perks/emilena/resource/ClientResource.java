package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Client;
import com.perks.emilena.dao.ClientDAO;
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
@Path("/client")
@Produces(MediaType.APPLICATION_JSON)
public class ClientResource {

    private final ClientDAO clientDAO;

    public ClientResource(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Path("/all")
    @GET
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public List<Client> findAll() {
        return clientDAO.findAll();
    }

    @POST
    @Path("/create")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN"})
    public Response create(@Valid Client client) {
        clientDAO.create(client);
        return Response.ok().build();
    }

    @POST
    @Path("/update")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN"})
    public Response update(@Valid Client client) {
        clientDAO.create(client);
        return Response.ok().build();
    }

    @POST
    @Path("/delete")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN"})
    public Response delete(@Valid Client client) {
        clientDAO.delete(client);
        return Response.ok().build();
    }

    @GET
    @Path("/find/{id}")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public Client findPerson(@PathParam("id") LongParam id) {
        return clientDAO.findById(id.get());
    }

    @Path("/active")
    @GET
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public List<Client> findAllActive() {
        return clientDAO.findAllActive();
    }

    @Path("/availability/{day}")
    @GET
    @Timed
    @UnitOfWork
    public List<Client> availabilityFromDay(@PathParam("day") String dayOfWeek) {
        return clientDAO.joinPersonAvailabilityByDayOfWeek(DayOfWeek.valueOf(dayOfWeek.toUpperCase()));
    }
}

