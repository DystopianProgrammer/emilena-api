package com.perks.emilena.service;

import com.perks.emilena.api.Availability;
import com.perks.emilena.api.Person;
import com.perks.emilena.api.Rota;
import com.perks.emilena.api.RotaItem;
import com.perks.emilena.dao.PersonDAO;
import com.perks.emilena.dao.RotaDAO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class RotaService {

    private final RotaItemService rotaItemService;
    private final RotaDAO rotaDAO;
    private final PersonDAO personDAO;

    public RotaService(RotaItemService rotaItemService, RotaDAO rotaDAO, PersonDAO personDAO) {
        this.rotaItemService = rotaItemService;
        this.rotaDAO = rotaDAO;
        this.personDAO = personDAO;
    }

    /**
     * Simple calls on the DAO to create a new Rota. The service commits the week starting date to the first
     * Monday from the date specified in the method.
     *
     * @param weekStarting - the date the request was made, the first Monday from this date is determined.
     * @return the generated rota based on availability
     */
    public Rota create(LocalDate weekStarting) {

        List<RotaItem> rotaItems = rotaItemService.rotaItems(weekStarting);
        Rota rota = new Rota();
        rota.setWeekStarting(weekStarting);
        rota.setRotaItems(rotaItems);

        return rota;
    }

    /**
     * Finds all people who have specified availabilities but no association for an appointment.
     *
     * @param id of the rota
     * @return
     */
    public List<Unallocated> unallocated(Long id) {

        List<Unallocated> unallocatedItems = new ArrayList<>();
        List<Availability> availabilities = personDAO.listActive().stream()
                .map(Person::getAvailabilities)
                .flatMap(a -> a.stream())
                .distinct()
                .collect(Collectors.toList());

        List<RotaItem> rotaItems = rotaDAO.findById(id).getRotaItems();
        Consumer<DayOfWeek> dayOfWeekConsumer = (day -> compare(availabilities, rotaItems, day, unallocatedItems));
        Arrays.stream(DayOfWeek.values()).forEach(dayOfWeekConsumer);

        return unallocatedItems;
    }

    private void compare(List<Availability> availabilities,
                         List<RotaItem> rotaItems,
                         DayOfWeek dayOfWeek, List<Unallocated> unallocatedItems) {

        if (availabilities.size() == 0) {
            return;
        }

        Availability availability = availabilities.get(0);

        boolean present = rotaItems.stream()
                .filter(rotaItem -> rotaItem.getDayOfWeek().equals(dayOfWeek))
                .filter(rotaIem -> rotaIem.getClient().equals(availability.getPerson())
                        || rotaIem.getStaff().equals(availability.getPerson()))
                .findAny()
                .isPresent();

        if (!present) {
            unallocatedItems.add(new Unallocated(availability.getPerson(), availability.getDayOfWeek()));
        }

        availabilities.remove(0);

        compare(availabilities, rotaItems, dayOfWeek, unallocatedItems);
    }


    public static final class Unallocated {

        private final Person person;
        private final DayOfWeek day;

        public Unallocated(Person person, DayOfWeek day) {
            this.person = person;
            this.day = day;
        }

        public Person getPerson() {
            return person;
        }

        public DayOfWeek getDay() {
            return day;
        }
    }
}

