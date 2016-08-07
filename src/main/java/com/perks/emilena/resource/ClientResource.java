package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Client;
import com.perks.emilena.dao.ClientDAO;
import com.perks.emilena.service.ClientService;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.validation.Valid;
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
@Path("/client")
@Produces(MediaType.APPLICATION_JSON)
public class ClientResource {

    private final ClientService clientService;
    private final ClientDAO clientDAO;

    public ClientResource(ClientService clientService) {
        this.clientService = clientService;
        this.clientDAO = clientService.getDataAccessObject();
    }

    @Path("/all")
    @GET
    @Timed
    @UnitOfWork
    public List<Client> findAll() {
        return clientDAO.findAll();
    }

    @POST
    @Path("/add")
    @Timed
    @UnitOfWork
    public Client add(@Valid Client client) {
        return clientService.create(client);
    }

    @POST
    @Path("/update")
    @Timed
    @UnitOfWork
    public Client update(@Valid Client client) {
        return clientDAO.update(client);
    }

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Client findPerson(@PathParam("id") LongParam id) {
        return clientDAO.findById(id.get());
    }

    @Path("/active")
    @GET
    @Timed
    @UnitOfWork
    public List<Client> findAllActive() {
        return clientDAO.findAllActive();
    }
}

