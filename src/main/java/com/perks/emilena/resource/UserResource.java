package com.perks.emilena.resource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.security.User;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

/**
 * Created by Geoff Perks
 * Date: 28/07/2016.
 */
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {


    /**
     * The payload is unnecessary, as the credentials are in the header
     * This is simply a dummy resource to send back the authentication status
     * in the response headers.
     *
     * 200 success
     * 401 invalid
     * 403 forbidden
     *
     * @param user
     * @return
     */
    @GET
    @Path("/login")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public User login(@Auth User user) {
        return user;
    }
}
