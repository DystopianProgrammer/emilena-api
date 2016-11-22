package com.perks.emilena.resource;

import com.codahale.metrics.annotation.Timed;
import com.perks.emilena.api.Invoice;
import com.perks.emilena.dao.InvoiceDAO;
import com.perks.emilena.service.InvoiceService;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
@Path("/invoice")
@Produces(MediaType.APPLICATION_JSON)
public class InvoiceResource {

    private final InvoiceDAO invoiceDAO;
    private final InvoiceService invoiceService;

    public InvoiceResource(InvoiceDAO invoiceDAO, InvoiceService invoiceService) {
        this.invoiceDAO = invoiceDAO;
        this.invoiceService = invoiceService;
    }

    /**
     * Produces invoices from completed {@link com.perks.emilena.api.RotaItem}s
     * @return a list of invoices
     */
    @Path("/produce")
    @GET
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public Response produce() {
        Set<Invoice> invoices = this.invoiceService.produce();
        if(invoices.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(invoices).build();
    }

    @POST
    @Path("/update")
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public Response update(Invoice invoice) {
        this.invoiceService.update(invoice);
        return Response.ok().build();
    }

    @Path("/list/issued/{date}")
    @GET
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public List<Invoice> listByIssueDate(@PathParam("date") String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
        return this.invoiceDAO.listByIssueDate(LocalDate.parse(date, formatter));
    }

    @Path("/list/all")
    @GET
    @Timed
    @UnitOfWork
    @RolesAllowed(value = {"ADMIN", "STAFF"})
    public Response fetchAll() {
        return Response.ok(invoiceDAO.fetchAll()).build();
    }
}
