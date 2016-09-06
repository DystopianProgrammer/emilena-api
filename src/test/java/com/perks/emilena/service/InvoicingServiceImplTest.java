package com.perks.emilena.service;

import com.google.common.collect.Lists;
import com.perks.emilena.api.Appointment;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.Invoice;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Geoff Perks
 * Date: 31/08/2016.
 */
public class InvoicingServiceImplTest {


    @Test
    public void invoiceByDurationForJanuary() throws Exception {

        // given
        LocalDateTime start = LocalDateTime.of(2016, Month.JANUARY, 1, 10, 00);
        LocalDateTime end = LocalDateTime.of(2016, Month.JANUARY, 31, 10, 30);

        // pass
        Appointment appt1 = new Appointment();
        appt1.setFromDate(fromLocalDateTime(2016, Month.JANUARY, 1, 10, 00));
        appt1.setToDate(fromLocalDateTime(2016, Month.JANUARY, 1, 11, 00));
        appt1.setIsComplete(true);

        // fail
        Appointment appt2 = new Appointment();
        appt2.setFromDate(fromLocalDateTime(2016, Month.FEBRUARY, 1, 10, 00));
        appt2.setToDate(fromLocalDateTime(2016, Month.FEBRUARY, 1, 12, 00));
        appt2.setIsComplete(true);

        // fail
        Appointment appt3 = new Appointment();
        appt3.setFromDate(fromLocalDateTime(2016, Month.JANUARY, 20, 10, 00));
        appt3.setToDate(fromLocalDateTime(2016, Month.JANUARY, 20, 13, 00));
        appt3.setIsComplete(false);

        // pass
        Appointment appt4 = new Appointment();
        appt4.setFromDate(fromLocalDateTime(2016, Month.JANUARY, 29, 10, 00));
        appt4.setToDate(fromLocalDateTime(2016, Month.JANUARY, 29, 12, 00));
        appt4.setIsComplete(true);

        ArrayList<Appointment> appointments = Lists.newArrayList(appt1, appt2, appt3, appt4);

        Client client = new Client();
        client.setAppointments(appointments);

        // when
        InvoicingService service = new InvoicingServiceImpl();
        List<Invoice> invoices = Lists.newArrayList(service.invoiceByDuration(start, end, client));

        // then
        assertThat(invoices.size()).isEqualTo(2);
        assertThat(invoices.get(1).getInferredHours()).isEqualTo(120L);
        assertThat(invoices.get(1).getInferredHours()).isEqualTo(120L);
    }

    @Test
    public void invoiceByDurationForFebruary() throws Exception {

        // given
        LocalDateTime start = LocalDateTime.of(2016, Month.FEBRUARY, 1, 10, 00);
        LocalDateTime end = LocalDateTime.of(2016, Month.FEBRUARY, 29, 10, 30);

        // pass
        Appointment appt1 = new Appointment();
        appt1.setFromDate(fromLocalDateTime(2016, Month.FEBRUARY, 1, 10, 30));
        appt1.setToDate(fromLocalDateTime(2016, Month.FEBRUARY, 1, 11, 30));
        appt1.setIsComplete(true);

        // pass
        Appointment appt2 = new Appointment();
        appt2.setFromDate(fromLocalDateTime(2016, Month.FEBRUARY, 1, 10, 15));
        appt2.setToDate(fromLocalDateTime(2016, Month.FEBRUARY, 1, 12, 15));
        appt2.setIsComplete(true);

        // fail
        Appointment appt3 = new Appointment();
        appt3.setFromDate(fromLocalDateTime(2016, Month.FEBRUARY, 20, 10, 00));
        appt3.setToDate(fromLocalDateTime(2016, Month.FEBRUARY, 20, 13, 00));
        appt3.setIsComplete(false);

        // fail
        Appointment appt4 = new Appointment();
        appt4.setFromDate(fromLocalDateTime(2016, Month.FEBRUARY, 27, 10, 00));
        appt4.setToDate(fromLocalDateTime(2016, Month.FEBRUARY, 27, 12, 00));
        appt4.setIsComplete(true);
        appt4.setInvoice(new Invoice());

        ArrayList<Appointment> appointments = Lists.newArrayList(appt1, appt2, appt3, appt4);

        Client client = new Client();
        client.setAppointments(appointments);

        // when
        InvoicingService service = new InvoicingServiceImpl();
        List<Invoice> invoices = Lists.newArrayList(service.invoiceByDuration(start, end, client));

        // then
        assertThat(invoices.size()).isEqualTo(2);
        assertThat(invoices.get(0).getInferredHours()).isEqualTo(60L);
        assertThat(invoices.get(1).getInferredHours()).isEqualTo(120L);
    }

    @Test
    public void testIntervalsUnderAnHour() throws Exception {

        // given
        LocalDateTime start = LocalDateTime.of(2016, Month.MARCH, 1, 10, 00);
        LocalDateTime end = LocalDateTime.of(2016, Month.MARCH, 29, 10, 30);

        // pass
        Appointment appt1 = new Appointment();
        appt1.setFromDate(fromLocalDateTime(2016, Month.MARCH, 1, 10, 30));
        appt1.setToDate(fromLocalDateTime(2016, Month.MARCH, 1, 11, 00));
        appt1.setIsComplete(true);

        // pass
        Appointment appt2 = new Appointment();
        appt2.setFromDate(fromLocalDateTime(2016, Month.MARCH, 1, 13, 15));
        appt2.setToDate(fromLocalDateTime(2016, Month.MARCH, 1, 13, 45));
        appt2.setIsComplete(true);

        // pass
        Appointment appt3 = new Appointment();
        appt3.setFromDate(fromLocalDateTime(2016, Month.MARCH, 20, 10, 00));
        appt3.setToDate(fromLocalDateTime(2016, Month.MARCH, 20, 13, 30));
        appt3.setIsComplete(true);

        // pass
        Appointment appt4 = new Appointment();
        appt4.setFromDate(fromLocalDateTime(2016, Month.MARCH, 27, 10, 00));
        appt4.setToDate(fromLocalDateTime(2016, Month.MARCH, 27, 12, 00));
        appt4.setIsComplete(true);

        ArrayList<Appointment> appointments = Lists.newArrayList(appt1, appt2, appt3, appt4);

        Client client = new Client();
        client.setAppointments(appointments);

        // when
        InvoicingService service = new InvoicingServiceImpl();
        List<Invoice> invoices = Lists.newArrayList(service.invoiceByDuration(start, end, client));

        // then
        assertThat(invoices.size()).isEqualTo(4);
        assertThat(invoices.get(0).getInferredHours()).isEqualTo(30L);
        assertThat(invoices.get(1).getInferredHours()).isEqualTo(30L);
        assertThat(invoices.get(2).getInferredHours()).isEqualTo(210L);
        assertThat(invoices.get(3).getInferredHours()).isEqualTo(120L);
    }

    private Date fromLocalDateTime(int year, Month month, int day, int hours, int minutes) {
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hours, minutes);
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }
}