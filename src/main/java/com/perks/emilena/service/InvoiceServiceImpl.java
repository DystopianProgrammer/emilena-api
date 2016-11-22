package com.perks.emilena.service;

import com.perks.emilena.api.Invoice;
import com.perks.emilena.api.Rota;
import com.perks.emilena.api.RotaItem;
import com.perks.emilena.dao.InvoiceDAO;
import com.perks.emilena.dao.RotaDAO;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceDAO invoiceDAO;
    private final RotaDAO rotaDAO;

    public InvoiceServiceImpl(InvoiceDAO invoiceDAO, RotaDAO rotaDAO) {
        this.invoiceDAO = invoiceDAO;
        this.rotaDAO = rotaDAO;
    }

    @Override
    public Set<Invoice> produce() {

        Function<RotaItem, Invoice> invoiceFunction = (rotaItem) -> {
            Invoice invoice = new Invoice();
            invoice.setRotaItem(rotaItem);
            invoice.setCreated(LocalDate.now(ZoneId.systemDefault()));
            invoice.setDuration(Duration.between(rotaItem.getStart(), rotaItem.getFinish()).toHours());
            return invoice;
        };

        Predicate<RotaItem> rotaItemPredicate = (rotaItem) -> {
            Invoice invoice = invoiceDAO.findByRotaItem(rotaItem);
            return (invoice == null);
        };

        return rotaDAO.fetchAll()
                .stream()
                .map(Rota::getRotaItems)
                .flatMap(items -> items.stream())
                .filter(rotaItemPredicate)
                .map(invoiceFunction)
                .collect(Collectors.toSet());
    }

    @Override
    public void update(Invoice invoice) {
        invoice.setIssued(LocalDate.now(ZoneId.systemDefault()));
        this.invoiceDAO.update(invoice);
    }

}
