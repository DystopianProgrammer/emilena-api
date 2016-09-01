package com.perks.emilena.resource;

import com.google.common.collect.Lists;
import com.perks.emilena.api.Appointment;
import com.perks.emilena.service.AppointmentService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * Created by Geoff Perks
 * Date: 01/09/2016.
 */
public class InvoiceResourceTest {

    private static final AppointmentService service = mock(AppointmentService.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new InvoiceResource(service))
            .build();

    @Before
    public void setUp() throws Exception {
        when(service.activeStaffAppointments(anyLong())).thenReturn(Lists.newArrayList(new Appointment()));
    }

    @After
    public void tearDown() throws Exception {
        reset(service);
    }

    @Test
    public void alertsByStaffId() throws Exception {
        Response response = resources.client().target("/invoice/staff/1/appointments")
                .request().get();
        assertThat(response.getEntity()).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
    }

}