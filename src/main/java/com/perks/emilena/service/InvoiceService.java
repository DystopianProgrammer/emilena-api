package com.perks.emilena.service;

import com.perks.emilena.api.Invoice;
import com.perks.emilena.api.Rota;
import com.perks.emilena.api.RotaItem;
import com.perks.emilena.config.ApplicationConfiguration;
import com.perks.emilena.dao.InvoiceDAO;
import com.perks.emilena.dao.RotaDAO;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.time.Duration.between;
import static java.time.LocalDate.now;

/**
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public class InvoiceService {

    private final InvoiceDAO invoiceDAO;
    private final RotaDAO rotaDAO;
    private final ApplicationConfiguration applicationConfiguration;

    public InvoiceService(InvoiceDAO invoiceDAO, RotaDAO rotaDAO, ApplicationConfiguration applicationConfiguration) {
        this.invoiceDAO = invoiceDAO;
        this.rotaDAO = rotaDAO;
        this.applicationConfiguration = applicationConfiguration;
    }

    public Set<Invoice> produce() {

        Function<RotaItem, Invoice> invoiceFunction = (rotaItem) -> {
            Invoice invoice = new Invoice();
            invoice.setRotaItem(rotaItem);
            invoice.setCreated(now(ZoneId.systemDefault()));
            invoice.setIssued(now(ZoneId.systemDefault()));
            invoice.setDuration(between(rotaItem.getStart(), rotaItem.getFinish()).toHours());
            invoice.setAmount(amount(between(rotaItem.getStart(), rotaItem.getFinish()).toHours()));
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

    public void update(Invoice invoice) {
        invoice.setIssued(now(ZoneId.systemDefault()));
        this.invoiceDAO.update(invoice);
    }

    private BigDecimal amount(Long hours) {
        BigDecimal defaultHourlyRate = this.applicationConfiguration.getDefaultHourlyRate();
        BigDecimal h = BigDecimal.valueOf(hours);
        return defaultHourlyRate.multiply(h);
    }

}
