package com.perks.emilena.service;

import com.perks.emilena.api.Availability;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.RotaItem;
import com.perks.emilena.api.Staff;
import com.perks.emilena.api.type.PersonType;
import com.perks.emilena.config.ApplicationConfiguration;
import com.perks.emilena.dao.AvailabilityDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.perks.emilena.util.TimeUtil.isBookable;
import static java.util.Arrays.stream;
import static java.util.Collections.shuffle;
import static java.util.Objects.requireNonNull;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class RotaItemService {

    private static final Logger LOG = LoggerFactory.getLogger(RotaItemService.class);

    private final LocationService locationService;
    private final ApplicationConfiguration configuration;
    private final AvailabilityDAO availabilityDAO;

    public RotaItemService(LocationService locationService,
                           ApplicationConfiguration configuration, AvailabilityDAO availabilityDAO) {

        this.locationService = locationService;
        this.configuration = configuration;
        this.availabilityDAO = availabilityDAO;
    }

    public List<RotaItem> rotaItems(LocalDate weekCommencing) {

        Set<RotaItem> rotaItems = new HashSet<>();

        stream(DayOfWeek.values()).forEach(dayOfWeek ->
                addRotaItems(dayOfWeek, rotaItems, weekCommencing.plusDays(dayOfWeek.ordinal())));


        List<RotaItem> filtered = new ArrayList<>(rotaItems);
        return filtered;
    }

    // per day operation
    private void addRotaItems(DayOfWeek dayOfWeek, Set<RotaItem> rotaItems, LocalDate localDate) {

        LOG.info("Fetching all availabilities and filtering by active person for {}", dayOfWeek);
        List<Availability> availabilities = availabilityDAO.findForDayOfWeek(dayOfWeek).stream()
                .filter(a -> a.getPerson() != null)
                .filter(a -> a.getPerson().getActive())
                .collect(Collectors.toList());

        LOG.info("Separating availabilities by Staff");
        List<Availability> staffAvailabilities = availabilities.stream()
                .filter(a -> a.getPerson().getPersonType().equals(PersonType.STAFF))
                .collect(Collectors.toList());

        LOG.info("Separating availabilities by Client");
        List<Availability> clientAvailabilities = availabilities.stream()
                .filter(a -> a.getPerson().getPersonType().equals(PersonType.CLIENT))
                .collect(Collectors.toList());

        LOG.info("Mapping Client availabilities to RotaItems");
        Set<RotaItem> unassignedItems = clientAvailabilities.stream()
                .map(a -> {
                    RotaItem item = new RotaItem();
                    item.setStart(a.getFromTime());
                    item.setFinish(a.getToTime());
                    item.setClient((Client) a.getPerson());
                    item.setDayOfWeek(dayOfWeek);
                    item.setSupportDate(localDate);
                    return item;
                })
                .distinct()
                .collect(Collectors.toSet());

        LOG.info("Allocating Staff to RotaItems for {}", dayOfWeek);
        Set<RotaItem> assignedItems = new HashSet<>();

        // 1st sweep
        List<Availability> bucket = new ArrayList<>();
        unassignedItems.forEach(i -> assign(assignedItems, i, staffAvailabilities, bucket));

        // 2nd sweep
        List<Availability> disposable = new ArrayList<>();
        unassignedItems.forEach(i -> assign(assignedItems, i, bucket, disposable));

        LOG.info("Finalising sorted RotaItems for {}", dayOfWeek);
        List<RotaItem> sorted = assignedItems.stream()
                .filter(i -> i.getStaff() != null)
                .distinct()
                .collect(Collectors.toList());

        rotaItems.addAll(sorted);
    }

    /**
     * Staff and client must be within the specified radius.
     * Staff and client must have matching availability time
     * Staff must not overlap self
     * Staff must have reasonable travelling time for each appointment
     * Staff may have a single availability spanning the entire day. So we need to track their remaining time
     *
     * @param assignedItems       - RotaItems with both client and staff
     * @param rotaItem            - the current unassigned rota item consisting over only a client
     * @param staffAvailabilities - a list of staff availabilities
     */
    private void assign(Set<RotaItem> assignedItems, RotaItem rotaItem, List<Availability> staffAvailabilities,
                        List<Availability> bucket) {

        shuffle(staffAvailabilities);

        for (Availability availability : staffAvailabilities) {

            // is there an overlap?
            if (isConflicting(availability, assignedItems)) {
                bucket.add(availability);
                continue;
            }

            // is it bookable
            if (isBookable(rotaItem.getStart(), rotaItem.getFinish(),
                    availability.getFromTime(), availability.getToTime())) {
                validateRadiusAndAdd(assignedItems, rotaItem, availability);
            } else {
                bucket.add(availability);
            }
        }
    }

    private boolean isConflicting(Availability availability, Set<RotaItem> assignedItems) {
        return assignedItems.stream()
                .anyMatch(rotaItem -> checkConflict(rotaItem, availability));
    }

    private void validateRadiusAndAdd(Set<RotaItem> assignedItems, RotaItem rotaItem, Availability availability) {
        locationService.distanceMatrix(availability.getPerson().getAddress().getPostCode(),
                rotaItem.getClient().getAddress().getPostCode())
                .ifPresent(dm -> {
                    if (configuration.getMaxDistanceRadius() >=
                            locationService.convertMetersToMiles(dm.getMeters())) {
                        // if yes to all of the above, then assign
                        rotaItem.setStaff((Staff) availability.getPerson());
                        assignedItems.add(rotaItem);
                    }
                });
    }

    private boolean checkConflict(RotaItem rotaItem, Availability availability) {

        requireNonNull(rotaItem);
        requireNonNull(availability);

        // FIXME requires some thought!!

        return false;
    }

}
