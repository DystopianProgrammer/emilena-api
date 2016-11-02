package com.perks.emilena.service;

import com.google.common.collect.Lists;
import com.perks.emilena.api.Assignment;
import com.perks.emilena.api.Availability;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.Rota;
import com.perks.emilena.api.Staff;
import com.perks.emilena.dao.ClientDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by Geoff Perks
 * Date: 06/09/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class RotaServiceTest {

    @Mock
    private ClientDAO clientDAO;

    @InjectMocks
    private RotaService rotaService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        Mockito.reset(clientDAO);
    }

    @Test
    public void testRotaGenerationForWeekForSingleMatch() {

        // Staff setup
        Snapshot sa1 = new Snapshot(LocalTime.of(10, 00), LocalTime.of(17, 00), DayOfWeek.MONDAY);
        Snapshot sa2 = new Snapshot(LocalTime.of(10, 00), LocalTime.of(17, 00), DayOfWeek.TUESDAY);
        Snapshot sa3 = new Snapshot(LocalTime.of(10, 00), LocalTime.of(17, 00), DayOfWeek.WEDNESDAY);

        List<Snapshot> staffSnapshots = Lists.newArrayList(sa1, sa2, sa3);
        Staff preferrableStaff1 = this.staffBuilder(staffSnapshots);

        List<Staff> preferredStaff = Lists.newArrayList(preferrableStaff1);

        // client setup
        Snapshot ca1 = new Snapshot(LocalTime.of(14, 00), LocalTime.of(17, 00), DayOfWeek.TUESDAY);
        Snapshot ca2 = new Snapshot(LocalTime.of(14, 00), LocalTime.of(16, 00), DayOfWeek.THURSDAY);
        Snapshot ca3 = new Snapshot(LocalTime.of(15, 00), LocalTime.of(16, 00), DayOfWeek.SATURDAY);

        List<Snapshot> clientSnapshots = Lists.newArrayList(ca1, ca2, ca3);

        Client client1 = this.clientBuilder(clientSnapshots, preferredStaff);
        client1.setId(1L);

        List<Client> clients = Lists.newArrayList(client1);

        when(clientDAO.findAllActive()).thenReturn(clients);

        Rota rota = this.rotaService.rotaForWeek(LocalDateTime.now());

        assertThat(rota).isNotNull();
        assertThat(rota.getTuesday()).isNotNull();
    }

    @Test
    public void testStaffAllocationWhenClientNotAvailable() {

        // given the following staff availabilities
        Snapshot sa1 = new Snapshot(LocalTime.of(10, 00), LocalTime.of(17, 00), DayOfWeek.MONDAY);
        Snapshot sa2 = new Snapshot(LocalTime.of(10, 00), LocalTime.of(17, 00), DayOfWeek.WEDNESDAY);
        Snapshot sa3 = new Snapshot(LocalTime.of(10, 00), LocalTime.of(17, 00), DayOfWeek.FRIDAY);
        Snapshot sa4 = new Snapshot(LocalTime.of(10, 00), LocalTime.of(15, 00), DayOfWeek.SATURDAY);

        List<Snapshot> staffSnapshots = Lists.newArrayList(sa1, sa2, sa3, sa4);
        Staff preferrableStaff1 = this.staffBuilder(staffSnapshots);

        List<Staff> preferredStaff = Lists.newArrayList(preferrableStaff1);

        // and given the following client availabilities
        Snapshot ca1 = new Snapshot(LocalTime.of(14, 00), LocalTime.of(17, 00), DayOfWeek.TUESDAY);
        Snapshot ca2 = new Snapshot(LocalTime.of(14, 00), LocalTime.of(16, 00), DayOfWeek.THURSDAY);
        Snapshot ca3 = new Snapshot(LocalTime.of(15, 00), LocalTime.of(16, 00), DayOfWeek.SATURDAY);

        List<Snapshot> clientSnapshots = Lists.newArrayList(ca1, ca2, ca3);

        Client client = this.clientBuilder(clientSnapshots, preferredStaff);

        // although staff is available, client is not, therefore this should not do an assignment
        // of staff to client
        Optional<Assignment> assignment = this.rotaService.forDay(client, DayOfWeek.MONDAY);
        assertThat(assignment.isPresent()).isFalse();

    }

    @Test
    public void testStaffAllocationWhenClientIsAvailable() {

        // Staff setup
        Snapshot sa1 = new Snapshot(LocalTime.of(10, 00), LocalTime.of(17, 00), DayOfWeek.MONDAY);
        Snapshot sa2 = new Snapshot(LocalTime.of(10, 00), LocalTime.of(17, 00), DayOfWeek.TUESDAY);
        Snapshot sa3 = new Snapshot(LocalTime.of(10, 00), LocalTime.of(17, 00), DayOfWeek.WEDNESDAY);

        List<Snapshot> staffSnapshots = Lists.newArrayList(sa1, sa2, sa3);

        Staff preferrableStaff1 = this.staffBuilder(staffSnapshots);

        List<Staff> preferredStaff = Lists.newArrayList(preferrableStaff1);

        // Client setup
        Snapshot ca1 = new Snapshot(LocalTime.of(14, 00), LocalTime.of(17, 00), DayOfWeek.TUESDAY);
        Snapshot ca2 = new Snapshot(LocalTime.of(14, 00), LocalTime.of(16, 00), DayOfWeek.THURSDAY);
        Snapshot ca3 = new Snapshot(LocalTime.of(15, 00), LocalTime.of(16, 00), DayOfWeek.SATURDAY);

        List<Snapshot> clientSnapshots = Lists.newArrayList(ca1, ca2, ca3);

        Client client = this.clientBuilder(clientSnapshots, preferredStaff);

        Optional<Assignment> assignment = this.rotaService.forDay(client, DayOfWeek.TUESDAY);
        assertThat(assignment.isPresent()).isFalse();//FIXME
    }

    // This should never be the case, but for the sake of being holy
    @Test
    public void testWhenClientAvailabilitiesAreNull() {
        Optional<Assignment> assignment = this.rotaService.forDay(new Client(), DayOfWeek.MONDAY);

        assertThat(assignment.isPresent()).isFalse();
    }

    // again this should never be the case - db constraint
    @Test
    public void testWhenStaffAvailabilitiesAreNull() {

        thrown.expectMessage("No available staff");

        // given
        Snapshot ca1 = new Snapshot(LocalTime.of(14, 00), LocalTime.of(17, 00), DayOfWeek.TUESDAY);
        Snapshot ca2 = new Snapshot(LocalTime.of(14, 00), LocalTime.of(16, 00), DayOfWeek.THURSDAY);
        Snapshot ca3 = new Snapshot(LocalTime.of(15, 00), LocalTime.of(16, 00), DayOfWeek.SATURDAY);

        List<Snapshot> clientSnapshots = Lists.newArrayList(ca1, ca2, ca3);

        List<Staff> staff = Lists.newArrayList(new Staff());

        Client client = this.clientBuilder(clientSnapshots, staff);

        this.rotaService.forDay(client, DayOfWeek.TUESDAY);

    }

    // TODO questionable
    @Test
    public void testWhenNoStaffAllocatedToClient() {

        // given
        Snapshot ca1 = new Snapshot(LocalTime.of(14, 00), LocalTime.of(17, 00), DayOfWeek.TUESDAY);
        Snapshot ca2 = new Snapshot(LocalTime.of(14, 00), LocalTime.of(16, 00), DayOfWeek.THURSDAY);

        List<Snapshot> clientSnapshots = Lists.newArrayList(ca1, ca2);

        Client client = this.clientBuilder(clientSnapshots, null);

        Optional<Assignment> assignment = this.rotaService.forDay(client, DayOfWeek.TUESDAY);

        // sadly we can't find suitable staff to care for this client
        assertThat(assignment.isPresent()).isFalse();
    }


    private Client clientBuilder(Collection<Snapshot> clientSnapshots, Collection<Staff> staff) {

        Function<Snapshot, Availability> availabilityMapper =
                getSnapshotAvailabilityFunction();

        List<Availability> availabilities = clientSnapshots.stream()
                .map(availabilityMapper)
                .collect(Collectors.toList());

        Client client = new Client();
        client.setAvailabilities(availabilities);
        client.setStaff(staff);

        return client;

    }


    private Staff staffBuilder(Collection<Snapshot> staffSnapshots) {

        Function<Snapshot, Availability> availabilityMapper = getSnapshotAvailabilityFunction();

        List<Availability> availabilities = staffSnapshots.stream()
                .map(availabilityMapper)
                .collect(Collectors.toList());

        Staff staff = new Staff();
        staff.setAvailabilities(availabilities);

        // and two-way relationship
        availabilities.forEach(a -> a.setPerson(Lists.newArrayList(staff)));

        return staff;

    }

    private Function<Snapshot, Availability> getSnapshotAvailabilityFunction() {
        return (snapshot) -> {

            Duration between = Duration.between(snapshot.getFrom(), snapshot.getTo());
            Availability availability = new Availability();
            availability.setDayOfWeek(snapshot.getDay());
            availability.setFromTime(snapshot.getFrom());
            availability.setToTime(snapshot.getTo());
            availability.setNumberOfHours(between.toHours());
            return availability;
        };
    }


    private static class Snapshot {

        private final LocalTime from;
        private final LocalTime to;
        private final DayOfWeek day;

        public Snapshot(LocalTime from, LocalTime to, DayOfWeek day) {
            this.from = from;
            this.to = to;
            this.day = day;
        }

        public LocalTime getFrom() {
            return from;
        }

        public LocalTime getTo() {
            return to;
        }

        public DayOfWeek getDay() {
            return day;
        }
    }

}