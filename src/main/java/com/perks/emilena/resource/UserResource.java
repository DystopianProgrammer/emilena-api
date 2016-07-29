package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.security.User;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Geoff Perks
 * Date: 28/07/2016.
 */
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {


    @POST
    @Path("/login")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"SYSTEM"})
    public Boolean login(@Valid User user) {
        return Boolean.TRUE;
    }
}
