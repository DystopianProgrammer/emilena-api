package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.config.ApplicationConfiguration;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
@Path("/configuration")
@Produces(MediaType.APPLICATION_JSON)
public class ConfigurationResource {

    private final ApplicationConfiguration applicationConfiguration;

    public ConfigurationResource(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Path("/all")
    @GET
    @Timed
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public Response configuration() {
        return Response.ok(applicationConfiguration).build();
    }
}
