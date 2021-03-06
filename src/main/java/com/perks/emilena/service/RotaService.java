package com.perks.emilena.service;

import com.perks.emilena.api.*;
import com.perks.emilena.dao.RotaDAO;
import com.perks.emilena.validation.ValidateCompare;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class RotaService {

    private static final int MAX_WEEKS = 2;

    private final RotaItemService rotaItemService;
    private final RotaDAO rotaDAO;
    private final StaffService staffService;
    private final ClientService clientService;


    public RotaService(RotaItemService rotaItemService, RotaDAO rotaDAO,
                       StaffService staffService, ClientService clientService) {

        this.rotaItemService = rotaItemService;
        this.rotaDAO = rotaDAO;
        this.staffService = staffService;
        this.clientService = clientService;
    }

    public Rota create(LocalDate weekCommencing) {
        Rota rota = new Rota();
        TemporalField temporalField = WeekFields.of(Locale.UK).dayOfWeek();
        rota.setWeekStarting(weekCommencing.with(temporalField, 1));
        rota.setRotaItems(rotaItemService.rotaItems(weekCommencing.with(temporalField, 1)));
        return rota;
    }

    public List<LocalDate> weeks() {
        LocalDate current = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        List<LocalDate> dates = new ArrayList<>();
        dates.add(current);
        return addWeeks(current, dates);
    }

    private List<LocalDate> addWeeks(LocalDate date, List<LocalDate> dates) {

        requireNonNull(date, "date must not be null");
        requireNonNull(dates, "dates must not be null");

        if (dates.size() == MAX_WEEKS) {
            return dates;
        }

        LocalDate next = LocalDate.from(date).with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        dates.add(next);

        return addWeeks(next, dates);
    }

    public Set<Person> unallocated(Long id) {

        List<RotaItem> rotaItems = this.rotaDAO.findById(id).getRotaItems();
        List<Staff> activeStaff = this.staffService.listAllActiveStaff();
        List<Client> activeClients = this.clientService.listAllActiveClients();

        List<Client> clients = rotaItems.stream()
                .map(RotaItem::getClient)
                .collect(Collectors.toList());
        List<Staff> staff = rotaItems.stream()
                .map(RotaItem::getStaff)
                .collect(Collectors.toList());

        Set<Person> people = new HashSet<>();
        Set<Person> unallocatedClients = traverseUnallocated(clients, activeClients, people);
        Set<Person> unallocatedStaff = traverseUnallocated(staff, activeStaff, people);
        people.addAll(unallocatedClients);
        people.addAll(unallocatedStaff);

        return people;
    }

    private Set<Person> traverseUnallocated(List<? extends Person> allocated,
                                             List<? extends Person> active, Set<Person> unallocated) {

        requireNonNull(allocated, "allocated must not be null");
        requireNonNull(active, "active must not be null");

        if (active.size() == 0) {
            return unallocated;
        }

        Person person = active.get(0);

        long count = allocated.stream().filter(p -> p.getId() == person.getId()).count();
        if (count == 0) {
            unallocated.add(person);
        }

        active.remove(0);

        return traverseUnallocated(allocated, active, unallocated);
    }
}

