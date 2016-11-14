package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Person;
import com.perks.emilena.dao.PersonDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    private final PersonDAO personDAO;

    public PersonResource(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GET
    @Path("/find/{id}")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public Person findById(@PathParam("id") Long id) {
        return this.personDAO.findPersonById(id);
    }
}
