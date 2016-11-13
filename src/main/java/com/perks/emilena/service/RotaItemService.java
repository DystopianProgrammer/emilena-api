package com.perks.emilena.service;

import com.google.common.base.Preconditions;
import com.perks.emilena.api.Availability;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.RotaItem;
import com.perks.emilena.api.Staff;
import com.perks.emilena.dao.AbsenceDAO;
import com.perks.emilena.dao.AvailabilityDAO;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
            // then pop a staff off the list, and find whether there's a match using a recursive call
            // WE CAN ONLY ALLOCATE THROUGH STAFF AVAILABILITY - NOT ENOUGH SUPPORT, THEN FTF NEEDS TO SORT THAT OUT!
            allocateStaff(l, staffAvailabilities, clientAvailabilities, d);
        };

        List<RotaItem> rotaItems = new ArrayList<>();
        Arrays.stream(DayOfWeek.values()).forEach(d -> consumer.accept(d, rotaItems));

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
                               DayOfWeek dayOfWeek) {

        Preconditions.checkArgument(items != null, "items cannot be null");
        Preconditions.checkArgument(staffAvailabilities != null, "staff availabilities cannot be null");
        Preconditions.checkArgument(clientAvailabilities != null, "client availabilities cannot be null");

        // we can't make an allocation in either of these scenarios
        if (clientAvailabilities.isEmpty()) return;
        if (staffAvailabilities.isEmpty()) return;

        Availability staffAvailability = staffAvailabilities.get(0);
        Availability clientAvailability = clientAvailabilities.get(0);

        // check for a match
        if (clientAvailability.getDayOfWeek().equals(staffAvailability.getDayOfWeek()) &&
                clientAvailability.getFromTime().equals(staffAvailability.getFromTime()) ||
                clientAvailability.getFromTime().isAfter(staffAvailability.getFromTime()) &&
                        clientAvailability.getToTime().equals(staffAvailability.getToTime()) ||
                clientAvailability.getToTime().isBefore(staffAvailability.getToTime())) {

            if (!alreadyAllocated(clientAvailability, items)) {
                // we have a match
                RotaItem rotaItem = new RotaItem();
                rotaItem.setStaff((Staff) staffAvailability.getPerson());
                rotaItem.setClient((Client) clientAvailability.getPerson());
                rotaItem.setStart(clientAvailability.getFromTime());
                rotaItem.setFinish(clientAvailability.getToTime());
                rotaItem.setDayOfWeek(dayOfWeek);
                items.add(rotaItem);
            }
        }

        // now remove that availability. It was either allocated, or couldn't find a match.
        staffAvailabilities.remove(0);
        clientAvailabilities.remove(0);

        allocateStaff(items, staffAvailabilities, clientAvailabilities, dayOfWeek);
    }

    private boolean alreadyAllocated(Availability client, List<RotaItem> items) {
        return items.stream().anyMatch(item -> (Objects.equals(client.getDayOfWeek(), item.getDayOfWeek()) &&
                Objects.equals(client.getPerson(), item.getClient()) &&
                Objects.equals(client.getFromTime(), item.getStart()) &&
                Objects.equals(client.getToTime(), item.getFinish())));
    }

}
