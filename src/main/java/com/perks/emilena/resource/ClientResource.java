package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Client;
import com.perks.emilena.dao.ClientDAO;
import com.perks.emilena.service.ClientService;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
@Path("/client")
@Produces(MediaType.APPLICATION_JSON)
public class ClientResource {

    private final ClientService clientService;
    private final ClientDAO clientDAO;

    public ClientResource(ClientService clientService, ClientDAO clientDAO) {
        this.clientService = clientService;
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
    @Path("/add")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public Client add(@Valid Client client) {
        return clientService.create(client);
    }

    @POST
    @Path("/update")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public Client update(@Valid Client client) {
        return clientDAO.update(client);
    }

    @GET
    @Path("/{id}")
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
}

