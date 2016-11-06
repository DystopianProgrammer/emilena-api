package com.perks.emilena.service;

import com.google.common.collect.Lists;
import com.perks.emilena.api.Availability;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.Staff;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;


/**
 * Created by 466707 on 05/11/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class GroupingServiceTest {

    @Mock
    GroupingService groupingService;

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
        reset(groupingService);
    }


    @Test
    public void shouldGroupClientWithPreferredStaff() {

        // given

        Availability clientsAvailability = new Availability();
        clientsAvailability.setDayOfWeek(DayOfWeek.MONDAY);
        clientsAvailability.setFromTime(LocalTime.now());
        clientsAvailability.setToTime(LocalTime.now().plusHours(2));

        // use the clients availability for a pretty close match
        Staff staff = new Staff();
        staff.setAvailabilities(Lists.newArrayList(clientsAvailability));

        Client client = new Client();
        client.setAvailabilities(Lists.newArrayList(clientsAvailability));
        client.setStaff(Lists.newArrayList(staff));

        GroupingService groupingService = new GroupingService ();
        groupingService.groupByLocalDateTime(DayOfWeek.MONDAY).ifPresent(g -> {
            assertThat(g).isNotNull();
        });






    }

    @Test
    public void shouldGroupClientWithOtherStaff() {

    }

    /**
     * This will generate an error indicating the client hasn't been assigned a support worker
     * This may happen if all staff are unavailable
     */
    @Test
    public void shouldGenerateErrorForUnallocatedClientSupport() {

    }

    /**
     * This will generate an error if the staff member has availability for a given day, but is not assigned to a client
     */
    @Test
    public void shouldGenerateErrorForUnallocatedStaffSupport() {

    }

}