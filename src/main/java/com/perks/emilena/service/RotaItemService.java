package com.perks.emilena.service;

import com.google.common.base.Preconditions;
import com.perks.emilena.api.Availability;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.RotaItem;
import com.perks.emilena.api.Staff;
import com.perks.emilena.dao.AbsenceDAO;
import com.perks.emilena.dao.AvailabilityDAO;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class RotaItemService {

    private final AvailabilityDAO availabilityDAO;
    private final AbsenceDAO absenceDAO;

    public RotaItemService(AvailabilityDAO availabilityDAO, AbsenceDAO absenceDAO) {
        this.availabilityDAO = availabilityDAO;
        this.absenceDAO = absenceDAO;
    }

    /**
     * The initial sweep, this could be improved with the sorting mechanism
     * perhaps do this with recursion and in parallel chunks - this needs some improvement
     * identify where any client & staff overlaps exist
     *
     * @param date
     * @return
     */
    public List<RotaItem> rotaItems(LocalDate date) {

        BiConsumer<DayOfWeek, List<RotaItem>> consumer = (d, l) -> {
            List<Availability> clientAvailabilities = this.availabilityDAO.findByDay(d, Client.class);
            List<Availability> staffAvailabilities = this.availabilityDAO.findByDay(d, Staff.class);
            List<AllocationTracker> allocationTrackers = new ArrayList<>();
            // then pop a staff off the list, and find whether there's a match using a recursive call
            // WE CAN ONLY ALLOCATE THROUGH STAFF AVAILABILITY - NOT ENOUGH SUPPORT, THEN FTF NEEDS TO SORT THAT OUT!
            allocateStaff(l, staffAvailabilities, clientAvailabilities, allocationTrackers, d);
        };

        List<RotaItem> rotaItems = new ArrayList<>();
        Arrays.stream(DayOfWeek.values()).forEach(d -> consumer.accept(d, rotaItems));

        addSupportDate(rotaItems, date);

        return rotaItems;
    }

    /**
     * This gets called for each day. The DAO queries for the specific day, so we won't need to do additional checking
     *
     * @param items
     * @param staffAvailabilities
     * @param clientAvailabilities
     */
    private void allocateStaff(List<RotaItem> items,
                               List<Availability> staffAvailabilities,
                               List<Availability> clientAvailabilities,
                               List<AllocationTracker> allocationTrackers,
                               DayOfWeek dayOfWeek) {

        Preconditions.checkArgument(items != null, "items cannot be null");
        Preconditions.checkArgument(staffAvailabilities != null, "staff availabilities cannot be null");
        Preconditions.checkArgument(clientAvailabilities != null, "client availabilities cannot be null");

        // we can't make an allocation in either of these scenarios
        if (clientAvailabilities.isEmpty()) return;

        for (Availability staffAvailability : staffAvailabilities) {

            Availability clientAvailability = clientAvailabilities.get(0);

            if (allocationTrackers.stream().anyMatch(a ->
                    a.isAllocated(clientAvailability, staffAvailability.getPerson().getId()))) {
                continue;
            }

            // check for a match
            // 1. client & staff day must be the same
            // 2. client start time must be equal or after staff start time
            // 3. client finish time must be before or equal to staff finish time
            if (hasTimeMatch(clientAvailability, staffAvailability)) {

                if (!alreadyAllocated(clientAvailability, items)) {
                    // we have a match
                    RotaItem rotaItem = new RotaItem();
                    rotaItem.setStaff((Staff) staffAvailability.getPerson());
                    rotaItem.setClient((Client) clientAvailability.getPerson());
                    rotaItem.setStart(clientAvailability.getFromTime());
                    rotaItem.setFinish(clientAvailability.getToTime());
                    rotaItem.setDayOfWeek(dayOfWeek);
                    items.add(rotaItem);

                    // staff is not booked at this time
                    allocationTrackers.add(new AllocationTracker(clientAvailability.getFromTime(),
                            clientAvailability.getToTime(), dayOfWeek, staffAvailability.getPerson().getId()));
                }
            }
        }

        // now remove that availability. It was either allocated, or couldn't find a match.
        clientAvailabilities.remove(0);

        allocateStaff(items, staffAvailabilities, clientAvailabilities, allocationTrackers, dayOfWeek);
    }

    // check for a match
    // 1. client & staff day must be the same
    // 2. client start time must be equal or after staff start time
    // 3. client finish time must be before or equal to staff finish time
    private boolean hasTimeMatch(Availability clientAvailability, Availability staffAvailability) {
        // initial check
        if (clientAvailability.getDayOfWeek().equals(staffAvailability.getDayOfWeek())) {
            if (clientAvailability.getFromTime().equals(staffAvailability.getFromTime()) ||
                    clientAvailability.getFromTime().isAfter(staffAvailability.getFromTime()) &&
                            clientAvailability.getToTime().equals(staffAvailability.getToTime()) ||
                    clientAvailability.getToTime().isBefore(staffAvailability.getToTime())) {
                return true;
            }
        }
        return false;
    }

    private boolean alreadyAllocated(Availability client, List<RotaItem> items) {
        return items.stream().anyMatch(item -> (Objects.equals(client.getDayOfWeek(), item.getDayOfWeek()) &&
                Objects.equals(client.getPerson(), item.getClient()) &&
                Objects.equals(client.getFromTime(), item.getStart()) &&
                Objects.equals(client.getToTime(), item.getFinish())));
    }

    private void addSupportDate(List<RotaItem> items, LocalDate rotaForDate) {
        items.forEach(item -> {
            switch(item.getDayOfWeek()) {
                case MONDAY: item.setSupportDate(LocalDate.from(rotaForDate)); break;
                case TUESDAY: item.setSupportDate(LocalDate.from(rotaForDate).plusDays(1)); break;
                case WEDNESDAY: item.setSupportDate(LocalDate.from(rotaForDate).plusDays(2)); break;
                case THURSDAY: item.setSupportDate(LocalDate.from(rotaForDate).plusDays(3)); break;
                case FRIDAY: item.setSupportDate(LocalDate.from(rotaForDate).plusDays(4)); break;
                case SATURDAY: item.setSupportDate(LocalDate.from(rotaForDate).plusDays(5)); break;
                case SUNDAY: item.setSupportDate(LocalDate.from(rotaForDate).plusDays(6)); break;
                default: throw new RuntimeException("A date is required for a rota item");
            }
        });
    }

    /**
     * Class for tracking staff availability. Created at time of allocating staff
     */
    private static final class AllocationTracker {

        private final LocalTime start;
        private final LocalTime finish;
        private final DayOfWeek day;
        private final Long staffId;

        public AllocationTracker(LocalTime start, LocalTime finish, DayOfWeek day, Long staffId) {
            this.start = start;
            this.finish = finish;
            this.day = day;
            this.staffId = staffId;
        }

        /**
         * returns true if staff is allocated at this time, otherwise returns false;
         *
         * @param availability - the other availability
         * @return true if allocated
         */
        private boolean isAllocated(Availability availability, Long staffId) {
            long start = Duration.between(this.start, availability.getFromTime()).getSeconds();
            return this.staffId.equals(staffId) && start < 3600L && day.equals(availability.getDayOfWeek());
        }
    }

}
