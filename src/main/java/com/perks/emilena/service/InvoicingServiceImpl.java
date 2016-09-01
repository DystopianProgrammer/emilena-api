package com.perks.emilena.service;

import com.perks.emilena.api.Appointment;
import com.perks.emilena.api.Invoice;
import com.perks.emilena.api.Person;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Geoff Perks
 * Date: 31/08/2016.
 */
public class InvoicingServiceImpl implements InvoicingService {

    @Override
    public Collection<Invoice> invoiceByDuration(LocalDateTime start, LocalDateTime end, Person person) {

        Predicate<Appointment> startDatePredicate = (appt) -> {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(appt.getFromDate().toInstant(), ZoneId.systemDefault());
            return localDateTime.isAfter(start) || localDateTime.isEqual(start);
        };

        Predicate<Appointment> endDatePredicate = (appt) -> {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(appt.getToDate().toInstant(), ZoneId.systemDefault());
            return localDateTime.isBefore(end) || localDateTime.isEqual(end);
        };

        Function<Appointment, Invoice> mapToInvoice = (appt) -> {
            Instant startTime = appt.getFromDate().toInstant();
            Instant endTime = appt.getToDate().toInstant();
            long hours = Duration.between(startTime, endTime).toMinutes();
            Invoice invoice = new Invoice();
            invoice.setAppointment(appt);
            invoice.setInferredHours(hours);
            return invoice;
        };

        return person.getAppointments().stream()
                .filter(startDatePredicate)
                .filter(endDatePredicate)
                .filter(Appointment::getComplete)
                .filter(appt -> appt.getInvoice() == null)
                .map(mapToInvoice)
                .collect(Collectors.toList());
    }
}
