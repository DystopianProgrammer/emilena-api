package com.perks.emilena.service;

import com.google.common.collect.Lists;
import com.perks.emilena.api.Appointment;
import com.perks.emilena.api.Staff;
import com.perks.emilena.dao.StaffDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Geoff Perks
 * Date: 01/09/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class AppointmentServiceImplTest {

    private final StaffDAO staffDAO = mock(StaffDAO.class);

    @InjectMocks
    private AppointmentServiceImpl service;


    @Test
    public void activeStaffAppointments() throws Exception {

        Appointment app1 = new Appointment();
        app1.setComplete(null);
        Appointment app2 = new Appointment();
        app2.setComplete(true);
        Appointment app3 = new Appointment();
        app3.setComplete(false);
        Appointment app4 = new Appointment();
        app4.setComplete(false);

        List<Appointment> appointments = Lists.newArrayList(app1, app2, app3, app4);
        Staff staff = new Staff();
        staff.setAppointments(appointments);

        when(staffDAO.findById(anyLong())).thenReturn(staff);

        assertThat(service.activeStaffAppointments(1L).size()).isEqualTo(3);
    }

}