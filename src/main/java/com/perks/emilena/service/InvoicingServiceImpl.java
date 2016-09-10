package com.perks.emilena.service;

import com.perks.emilena.api.Appointment;
import com.perks.emilena.api.Invoice;
import com.perks.emilena.api.Person;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
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
    public Collection<Invoice> invoiceByDuration(LocalDate appointmentDate, LocalTime start, LocalTime end, Person person) {

        Predicate<Appointment> startDatePredicate = (appt) -> appt.getStartTime().isAfter(start) || appt.getStartTime().equals(start);


        Predicate<Appointment> endDatePredicate = (appt) -> {
            return appt.getEndTime().isBefore(end) || appt.getEndTime().equals(end);
        };

        Function<Appointment, Invoice> mapToInvoice = (appt) -> {
            long hours = Duration.between(appt.getStartTime(), appt.getEndTime()).toMinutes();
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
