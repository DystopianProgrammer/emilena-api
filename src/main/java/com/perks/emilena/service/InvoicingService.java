package com.perks.emilena.service;

import com.perks.emilena.api.Invoice;
import com.perks.emilena.api.Person;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * The invoicing service is responsible determining whether clients are to be invoiced at a given point in time.
 * <p>
 * For each appointment that has been made, checks need to capture whether the appointment was carried out.
 * If there is an absence, then if more than 24 hours notice was provided by the client, then the client is not invoiced.
 * <p>
 * Date ranges are specified to calculate the number of invoices, this will include the number of hours provided by a member of staff
 * to a client.
 * <p>
 * <p>
 * Created by Geoff Perks
 * Date: 31/08/2016.
 */
public interface InvoicingService<T extends Person> {

    Collection<Invoice> invoiceByDuration(LocalDateTime start, LocalDateTime end, T person);
}
