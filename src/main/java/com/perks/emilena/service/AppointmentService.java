package com.perks.emilena.service;

import com.perks.emilena.api.Availability;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.Person;
import com.perks.emilena.api.Staff;
import com.perks.emilena.api.type.AddressType;
import com.perks.emilena.api.type.PersonType;
import com.perks.emilena.config.ApplicationConfiguration;
import com.perks.emilena.value.Appointment;
import com.perks.emilena.value.DistanceMatrix;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;

/**
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public class AppointmentService {

    private final ApplicationConfiguration applicationConfiguration;
    private final LocationService locationService;

    public AppointmentService(ApplicationConfiguration applicationConfiguration, LocationService locationService) {
        this.applicationConfiguration = applicationConfiguration;
        this.locationService = locationService;
    }

    /**
     * Calculates the remaining time available for a person for a given day. For example, if the person is contracted
     * to do a 7 hour day, and they work from 9am - 5pm, but do several 1 hour appointments through the course of the
     * day, then this needs to be tracked and accounted for.
     * <p>
     * The formula: remaining = (total - current) + next
     *
     * @param current - the current remaining time
     * @param next    - the next appointment/scheduling time
     * @param total   - the total time for the day i.e. duration
     * @return the remaining available time
     */
    public Long timeRemainingForDay(long current, long next, long total) {
        return null;
    }

    /**
     * Groups clients and staff together based on the configurable minimum and maximum radii. Note, this does not
     * check an overlapping of availability, nor does it care whether separate availabilities coincide. It simply
     * groups client and staff based on a reasonable distance radius.
     *
     * @param staff     - the staff
     * @param clients   - the clients
     * @param dayOfWeek - the day for which the appointment occurs.
     * @return an appointment object
     */
    public List<Appointment> pairByConfigurableLocationRadius(List<Staff> staff, List<Client> clients, DayOfWeek dayOfWeek) {

        requireNonNull(staff, "staff must not be null");
        requireNonNull(clients, "clients must not be null");

        // the configurable values for which we need to carry out the pairing
        Integer minDistanceRadius = applicationConfiguration.getMinDistanceRadius();
        Integer maxDistanceRadius = applicationConfiguration.getMaxDistanceRadius();
        AddressType defaultAddress = AddressType.valueOf(applicationConfiguration.getDefaultAddress());

        requireNonNull(minDistanceRadius, "minDistanceRadius must not be null - have you configured this in the yml?");
        requireNonNull(maxDistanceRadius, "maxDistanceRadius must not be null - have you configured this in the yml?");
        requireNonNull(defaultAddress, "defaultAddress must not be null - have you configured this in the yml?");

        // 1. perform a reduction on all the people who have availability for the specified day
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

        List<TemporalStore> staffTemporalStores = sortByPersonType(temporalStores, PersonType.STAFF);
        List<TemporalStore> clientTemporalStores = sortByPersonType(temporalStores, PersonType.CLIENT);

        // 2. perform a reduction on all the people within the maximum specified radius
        List<Appointment> appointments = new ArrayList<>();
        staffTemporalStores.forEach(sts -> clientTemporalStores.forEach(cts ->
                locationService.distanceMatrix(sts.getPostCode(), cts.getPostCode())
                        .ifPresent(dm -> appointments(appointments, sts, cts, dm))));

        return appointments;
    }

    private void appointments(List<Appointment> appointments, TemporalStore sts, TemporalStore cts, DistanceMatrix dm) {

        Objects.requireNonNull(appointments);
        Objects.requireNonNull(sts);
        Objects.requireNonNull(cts);
        Objects.requireNonNull(dm);

        if (locationService.convertMetersToMiles(dm.getMeters()) <= applicationConfiguration.getMaxDistanceRadius()) {
            // this is where we decide to use the client or staff's address
            if (applicationConfiguration.getDefaultAddress().equals(PersonType.CLIENT)) {
                Appointment appointment = new Appointment.Builder()
                        .staff((Staff) sts.getPerson())
                        .client((Client) cts.getPerson())
                        .address(cts.getPerson().getAddress())
                        .distanceMatrix(dm)
                        .start(cts.getAvailability().getFromTime())
                        .finish(cts.getAvailability().getToTime())
                        .build();
                appointments.add(appointment);
            } else {
                Appointment appointment = new Appointment.Builder()
                        .staff((Staff) sts.getPerson())
                        .client((Client) cts.getPerson())
                        .address(sts.getPerson().getAddress())
                        .distanceMatrix(dm)
                        .start(cts.getAvailability().getFromTime())
                        .finish(cts.getAvailability().getToTime())
                        .build();
                appointments.add(appointment);
            }
        }
    }

    private List<TemporalStore> sortByPersonType(List<TemporalStore> temporalStores, PersonType type) {
        return temporalStores.stream()
                .filter(ts -> ts.getPersonType().equals(type))
                .collect(Collectors.toList());
    }

    private static final class TemporalStore {
        private final Person person;
        private final PersonType personType;
        private final Availability availability;
        private final String postCode;

        public TemporalStore(Person person, PersonType personType, Availability availability, String postCode) {
            this.person = person;
            this.personType = personType;
            this.availability = availability;
            this.postCode = postCode;
        }

        public Person getPerson() {
            return person;
        }

        public PersonType getPersonType() {
            return personType;
        }

        public Availability getAvailability() {
            return availability;
        }

        public String getPostCode() {
            return postCode;
        }
    }


}
