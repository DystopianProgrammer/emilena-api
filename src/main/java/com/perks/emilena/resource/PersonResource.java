package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Person;
import com.perks.emilena.dao.PersonDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    @POST
    @Path("/delete")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN"})
    public Response delete(@Valid Person person) {
        personDAO.delete(person);
        return Response.ok().build();
    }
}
