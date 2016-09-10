package com.perks.emilena.resource;

import com.google.common.collect.Lists;
import com.perks.emilena.api.Appointment;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.Staff;
import com.perks.emilena.service.AppointmentService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Geoff Perks
 * Date: 29/08/2016.
 */
public class AppointmentResourceTest {


    private static final AppointmentService service = mock(AppointmentService.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new AppointmentResource(service)).build();

    @Before
    public void setUp() throws Exception {
        when(service.all()).thenReturn(Lists.newArrayList(new Appointment()));
        when(service.allActive()).thenReturn(Lists.newArrayList(new Appointment()));
        when(service.create(any(Appointment.class))).thenReturn(new Appointment());
    }

    @After
    public void tearDown() throws Exception {
        reset(service);
    }

    @Test
    public void add() throws Exception {

        Client client = new Client();
        client.setForename("Bob");
        client.setSurname("Smith");

        Staff staff = new Staff();
        staff.setForename("Dave");
        staff.setSurname("Jones");

        Appointment appointment = new Appointment();
        appointment.setStaff(staff);
        appointment.setClient(client);
        appointment.setAppointmentDate(LocalDate.now());
        appointment.setStartTime(LocalTime.now().plusHours(1));

        assertThat(resources.client().target("/appointment/add")
                .request()
                .post(Entity.entity(appointment, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(Appointment.class)).isNotNull();

        verify(service, times(1)).create(any(Appointment.class));
    }

    @Test
    public void get() throws Exception {
        assertThat(resources.client().target("/appointment/1")
                .request()
                .get()).isNotNull();
        verify(service, times(1)).fetchById(any(Long.class));
    }

    @Test
    public void findAll() throws Exception {
        assertThat(resources.client().target("/appointment/all")
                .request()
                .get()).isNotNull();
        verify(service, times(1)).all();
    }

}