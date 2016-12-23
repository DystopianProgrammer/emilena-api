package com.perks.emilena.service;

import com.perks.emilena.api.Availability;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.RotaItem;
import com.perks.emilena.api.Staff;
import com.perks.emilena.api.type.PersonType;
import com.perks.emilena.config.ApplicationConfiguration;
import com.perks.emilena.dao.AvailabilityDAO;
import com.perks.emilena.validation.ValidateCompare;
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

    private final AvailabilityDAO availabilityDAO;
    private final ValidateCompare<Staff, Client> distanceBetweenValidator;
    private final ValidateCompare<RotaItem, Availability> bookableValidator;
    private final ValidateCompare<RotaItem, RotaItem> timeConflictValidator;

    public RotaItemService(AvailabilityDAO availabilityDAO,
                           ValidateCompare<Staff, Client> distanceBetweenValidator,
                           ValidateCompare<RotaItem, Availability> bookableValidator,
                           ValidateCompare<RotaItem, RotaItem> timeConflictValidator) {

        this.availabilityDAO = availabilityDAO;
        this.distanceBetweenValidator = distanceBetweenValidator;
        this.bookableValidator = bookableValidator;
        this.timeConflictValidator = timeConflictValidator;
    }

    public List<RotaItem> rotaItems(LocalDate weekCommencing) {

        // stream through each day of the week
        List<RotaItem> rotaItems = stream(DayOfWeek.values())
                .map(dayOfWeek -> addRotaItems(dayOfWeek, weekCommencing.plusDays(dayOfWeek.ordinal())))
                .flatMap(ris -> ris.stream())
                .collect(Collectors.toList());

        return new ArrayList<>(rotaItems);
    }

    // per day operation
    private List<RotaItem> addRotaItems(DayOfWeek dayOfWeek, LocalDate localDate) {

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
        List<RotaItem> assignedItems = unassignedItems.stream()
                .map(rotaItem -> assign(rotaItem, staffAvailabilities))
                .flatMap(rotaItems -> rotaItems.stream())
                .filter(rotaItem -> rotaItem.getStaff() != null)
                .distinct()
                .collect(Collectors.toList());

        return assignedItems;
    }

    /**
     * Staff and client must be within the specified radius.
     * Staff and client must have matching availability time
     * Staff must not overlap self
     * Staff must have reasonable travelling time for each appointment
     * Staff may have a single availability spanning the entire day. So we need to track their remaining time
     *
     * @param rotaItem            - the current unassigned rota item consisting over only a client
     * @param staffAvailabilities - a list of staff availabilities
     */
    private List<RotaItem> assign(RotaItem rotaItem, List<Availability> staffAvailabilities) {

        shuffle(staffAvailabilities);

        // filter availabilities that can be used
        List<Availability> availabilities = staffAvailabilities.stream()
                .filter(availability -> this.bookableValidator.isValid(rotaItem, availability))
                .filter(availability -> this.distanceBetweenValidator.isValid((Staff) availability.getPerson(), rotaItem.getClient()))
                .collect(Collectors.toList());

        // now we have a proposed list of potential availabilities
        return processProposals(rotaItem, availabilities);
    }

    private List<RotaItem> processProposals(RotaItem rotaItem, List<Availability> availabilities) {

        List<RotaItem> proposedItems = availabilities.stream()
                .map(a -> {
                    RotaItem proposedItem = new RotaItem();
                    proposedItem.setStaff((Staff) a.getPerson());
                    proposedItem.setStart(rotaItem.getStart());
                    proposedItem.setFinish(rotaItem.getFinish());
                    proposedItem.setDayOfWeek(rotaItem.getDayOfWeek());
                    proposedItem.setSupportDate(rotaItem.getSupportDate());
                    proposedItem.setClient(rotaItem.getClient());
                    return proposedItem;
                })
                .collect(Collectors.toList());

        shuffle(proposedItems);

        List<RotaItem> assigned = new ArrayList<>();

        createEntry(assigned, proposedItems, rotaItem);

        return assigned;
    }

    private void createEntry(List<RotaItem> assigned, List<RotaItem> proposedItems, RotaItem rotaItem) {

        if(proposedItems.size() == 0) return;

        RotaItem proposedItem = proposedItems.get(0);
        if(assigned.size() == 0) {
            rotaItem.setStaff(proposedItem.getStaff());
            assigned.add(rotaItem);
            proposedItems.remove(0);
            return;
        } else {
            boolean anyMatch = assigned.stream()
                    .anyMatch(ri -> this.timeConflictValidator.isValid(ri, proposedItem));
            if(!anyMatch) {
                rotaItem.setStaff(proposedItem.getStaff());
                assigned.add(rotaItem);
                proposedItems.remove(0);
            } else {
                proposedItems.remove(0);
                createEntry(assigned, proposedItems, rotaItem);
            }
        }
    }

}
