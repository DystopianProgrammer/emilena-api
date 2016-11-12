package com.perks.emilena.service;

import com.perks.emilena.api.*;
import com.perks.emilena.dao.AbsenceDAO;
import com.perks.emilena.dao.AvailabilityDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * Created by 466707 on 12/11/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class AppointmentServiceTest {

    @InjectMocks
    AppointmentService appointmentService;

    @Mock
    AvailabilityDAO availabilityDAO;

    @Mock
    AbsenceDAO absenceDAO;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        reset(availabilityDAO, absenceDAO);
    }

    @Test
    public void shouldGeneratePairing() {

        Client c1 = new Client();
        c1.setId(1L);

        Client c2 = new Client();
        c2.setId(2L);

        Client c3 = new Client();
        c3.setId(3L);

        Client c4 = new Client();
        c4.setId(4L);

        Staff s1 = new Staff();
        s1.setId(1L);

        Staff s2 = new Staff();
        s2.setId(2L);

        Staff s3 = new Staff();
        s3.setId(3L);

        Staff s4 = new Staff();
        s4.setId(4L);

        // given
        List<Availability> availabilities =
                Arrays.asList(
                        build(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0), c1),
                        build(DayOfWeek.MONDAY, LocalTime.of(11, 0), LocalTime.of(12, 0), c1), // m = c1 + s1
                        build(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(17, 0), s1),
                        build(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(17, 0), s1),

                        build(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(17, 0), s2), // m = c2 + s2
                        build(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0), s3),
                        build(DayOfWeek.TUESDAY, LocalTime.of(16, 0), LocalTime.of(17, 0), c2),
                        build(DayOfWeek.TUESDAY, LocalTime.of(12, 0), LocalTime.of(13, 0), c3)); // m = c3 + s3

        // when
        when(availabilityDAO.findAll()).thenReturn(availabilities);

        List<AppointmentService.Appointment> appointments = appointmentService.appointmentsByDate(LocalDate.now());

        assertThat(appointments).isNotNull();
        assertThat(appointments.size()).isEqualTo(3);
    }

    @Test
    public void shouldFilterByAbsences() {

        // given

        Client c1 = new Client();
        c1.setId(1L);

        Client c2 = new Client();
        c2.setId(2L);

        Client c3 = new Client();
        c3.setId(3L);

        Client c4 = new Client();
        c4.setId(4L);

        Staff s1 = new Staff();
        s1.setId(1L);

        Staff s2 = new Staff();
        s2.setId(2L);

        Staff s3 = new Staff();
        s3.setId(3L);

        Staff s4 = new Staff();
        s4.setId(4L);

        Absence absence = new Absence();
        absence.setId(1L);
        absence.setPerson(c2);
        absence.setDate(LocalDate.of(2016, 11, 1)); // 01/11/2016 - a Tuesday


        List<Absence> absences = Arrays.asList(absence);

        List<Availability> availabilities =
                Arrays.asList(
                        build(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0), c1),
                        build(DayOfWeek.MONDAY, LocalTime.of(11, 0), LocalTime.of(12, 0), c1), // m = c1 + s1
                        build(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(17, 0), s1),
                        build(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(17, 0), s1),

                        build(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(17, 0), s2), // m = c2 + s2 *
                        build(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0), s3),
                        build(DayOfWeek.TUESDAY, LocalTime.of(16, 0), LocalTime.of(17, 0), c2), // * He's absent
                        build(DayOfWeek.TUESDAY, LocalTime.of(12, 0), LocalTime.of(13, 0), c3)); // m = c3 + s3

        // when
        when(availabilityDAO.findAll()).thenReturn(availabilities);
        when(absenceDAO.findAll()).thenReturn(absences);

        List<AppointmentService.Appointment> appointments =
                appointmentService.appointmentsByDate(LocalDate.of(2016, 10, 31)); // 31/10/2016 - Monday

        assertThat(appointments).isNotNull();
        assertThat(appointments.size()).isEqualTo(2);
    }

    private Availability build(DayOfWeek day, LocalTime from, LocalTime to, Person person) {
        Availability availability = new Availability();
        availability.setDayOfWeek(day);
        availability.setFromTime(from);
        availability.setToTime(to);
        availability.setPerson(person);
        return availability;
    }

}