package com.perks.emilena.service;

import com.perks.emilena.api.Invoice;

import java.util.Set;

/**
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public interface InvoiceService {

    /**
     * Produces a list of Invoices. It retrieves all completed {@link com.perks.emilena.api.RotaItem}s and produces
     * a list of invoices from them.
     *
     * @return a list of invoices
     */
    Set<Invoice> produce();

    void update(Invoice invoice);
}
