package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Client;
import com.perks.emilena.dao.ClientDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
@Path("/client/{id}")
@Produces(MediaType.APPLICATION_JSON)
public class ClientResource {

    private final ClientDAO clientDAO;

    public ClientResource(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    public Client findPerson(@PathParam("id") LongParam id) {
        return clientDAO.findById(id.get());
    }
}

