package com.perks.emilena.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Absence;
import com.perks.emilena.dao.AbsenceDAO;

import io.dropwizard.hibernate.UnitOfWork;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
@Path("/absence")
@Produces(MediaType.APPLICATION_JSON)
public class AbsenceResource {

    private final AbsenceDAO absenceDAO;

    public AbsenceResource(AbsenceDAO absenceDAO) {
        this.absenceDAO = absenceDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public List<Absence> findAll() {
        return absenceDAO.findAll();
    }
}
