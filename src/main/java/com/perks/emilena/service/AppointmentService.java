package com.perks.emilena.service;

import com.google.common.collect.BiMap;
import com.perks.emilena.aggregator.TimeAggregator;
import com.perks.emilena.aggregator.TimeAggregatorContainer;
import com.perks.emilena.api.Availability;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.Person;
import com.perks.emilena.api.Staff;
import com.perks.emilena.api.type.AddressType;
import com.perks.emilena.api.type.PersonType;
import com.perks.emilena.config.ApplicationConfiguration;
import com.perks.emilena.util.TimeUtil;
import com.perks.emilena.value.Appointment;
import com.perks.emilena.value.TemporalStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.google.common.collect.HashBiMap.create;
import static com.perks.emilena.util.TimeUtil.isBookable;
import static com.perks.emilena.util.TimeUtil.isOverlap;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;

/**
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public class AppointmentService {

    private static final Logger LOG = LoggerFactory.getLogger(AppointmentService.class);

    private final ApplicationConfiguration applicationConfiguration;

    public AppointmentService(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = requireNonNull(applicationConfiguration);
    }

    /**
     * Takes a matrix of grouped appointments, and evenly distributes staff resource across client availability times.
     * The multidimensional collection holds potential appointments within a specified radius. However, the distribution
     * will be heavily weighted at this point. This method resolves this issue.
     *
     * @param groups the bucket of unevenly distributed appointments
     * @return an even distribution of appointments.
     */
    List<Appointment> distributeGroupedAppointments(List<List<Appointment>> groups) {
        List<Appointment> even = new ArrayList<>();
        return distribute(groups, even);
    }

    // remember, each operation is for a single day!
    private List<Appointment> distribute(List<List<Appointment>> uneven, List<Appointment> even) {
        if (uneven.size() == 0) return even;

        List<Appointment> temporaryAppointments = uneven.stream()
                .flatMap(a -> a.stream())
                .collect(Collectors.toList());

        // then we need to recurse through even to see if it already contains the appointment
        List<TimeAggregator<Staff>> aggregators = new ArrayList<>();
        List<Appointment> appointments = partition(even, temporaryAppointments, aggregators);

        uneven.remove(0);

        return distribute(uneven, even);
    }

    @SuppressWarnings("unchecked")
    private List<Appointment> partition(List<Appointment> even,
                                        List<Appointment> temporaryAppointments,
                                        List<TimeAggregator<Staff>> aggregators) {

        if (temporaryAppointments.size() == 0) return even;

        Appointment temporary = temporaryAppointments.get(0);

        Consumer<Availability> availabilityConsumer = (availability) -> {
            if (isBookable(availability.getFromTime(), availability.getToTime(), temporary.getStart(), temporary.getFinish())) {
                if (aggregators.size() == 0 && !hasCollectionClientAppointment(even, temporary)) {
                    aggregators.add(new TimeAggregatorContainer<>(temporary.getStaff(), temporary.getStart(), temporary.getFinish()));
                    even.add(temporary);
                } else {
                    aggregators.forEach(staffTimeAggregator -> {
                        if (staffTimeAggregator.hoursRemaining() >= ChronoUnit.HOURS.between(temporary.getStart(),
                                temporary.getFinish())) {

                            if (!staffIsAlreadyAllocated(staffTimeAggregator, even)) {
                                aggregators.add(new TimeAggregatorContainer<>(temporary.getStaff(),
                                        temporary.getStart(), temporary.getFinish()));
                                even.add(temporary);
                            }
                        }
                    });
                }
            }
        };

        temporary.getStaff().getAvailabilities()
                .stream()
                .filter(a -> a.getDayOfWeek().equals(temporary.getDayOfWeek()))
                .findFirst()
                .ifPresent(availabilityConsumer);

        temporaryAppointments.remove(0);


        return partition(even, temporaryAppointments, aggregators);
    }

    private boolean staffIsAlreadyAllocated(TimeAggregator<Staff> staffTimeAggregator, List<Appointment> even) {
        return even.stream().anyMatch(appt -> staffTimeAggregator.getPerson().equals(appt.getStaff()));
    }

    private boolean hasCollectionClientAppointment(List<Appointment> even, Appointment temporary) {
        return even.stream().anyMatch(appointment -> temporary.getClient().equals(appointment.getClient()));
    }

    /**
     * Reduce clients and staff by availability person day. Note, this does not
     * check an overlapping of availability, nor does it care whether separate availabilities coincide. It simply
     * groups client and staff based on their availability for a given day
     *
     * @param staff     - the staff
     * @param clients   - the clients
     * @param dayOfWeek - the day for which the appointment occurs.
     * @return an appointment object
     */
    public Map<PersonType, List<TemporalStore>> reduceByAvailability(List<Staff> staff,
                                                                     List<Client> clients, DayOfWeek dayOfWeek) {

        requireNonNull(staff, "staff must not be null");
        requireNonNull(clients, "clients must not be null");

        LOG.info("performing a reduction on all the people who have availability for the specified day");
        List<TemporalStore> temporalStores = reduce(staff, clients, dayOfWeek);

        LOG.info("creating a temporal store (potential appointment) for clients and staff");
        List<TemporalStore> staffTemporal = sortByPersonType(temporalStores, PersonType.STAFF);
        List<TemporalStore> clientTemporal = sortByPersonType(temporalStores, PersonType.CLIENT);

        BiMap<PersonType, List<TemporalStore>> map = create();

        if (staffTemporal.size() > 0 && clientTemporal.size() > 0) {
            map.put(PersonType.STAFF, staffTemporal);
            map.put(PersonType.CLIENT, clientTemporal);
        }

        return map;
    }

    private List<TemporalStore> reduce(List<Staff> staff, List<Client> clients, DayOfWeek dayOfWeek) {
        List<TemporalStore> temporalStores = new ArrayList<>();

        Consumer<Person> personConsumer = (person) -> {
            List<TemporalStore> stores = person.getAvailabilities().stream()
                    .filter(availability -> dayOfWeek.equals(availability.getDayOfWeek()))
                    .map(availability -> new TemporalStore(person,
                            person.getPersonType(), availability, deleteWhitespace(person.getAddress().getPostCode())))
                    .collect(Collectors.toList());
            temporalStores.addAll(stores);
        };

        staff.forEach(personConsumer);
        clients.forEach(personConsumer);
        return temporalStores;
    }

    private List<TemporalStore> sortByPersonType(List<TemporalStore> temporalStores, PersonType type) {
        return temporalStores.stream()
                .filter(ts -> ts.getPersonType().equals(type))
                .collect(Collectors.toList());
    }

}
