package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.RotaItem;
import com.perks.emilena.dao.RotaItemDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
@Path("/rota-item")
@Produces(MediaType.APPLICATION_JSON)
public class RotaItemResource {

    private final RotaItemDAO rotaItemDAO;

    public RotaItemResource(RotaItemDAO rotaItemDAO) {
        this.rotaItemDAO = rotaItemDAO;
    }

    @POST
    @Path("/update")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN"})
    public Response update(@Valid  RotaItem rotaItem) {
        this.rotaItemDAO.update(rotaItem);
        return Response.ok().build();
    }
}
