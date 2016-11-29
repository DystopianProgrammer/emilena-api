package com.perks.emilena.service;

import com.perks.emilena.api.Person;
import com.perks.emilena.api.Rota;
import com.perks.emilena.dao.RotaDAO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class RotaService {

    private static final int MAX_WEEKS = 24;

    private final RotaItemService rotaItemService;
    private final RotaDAO rotaDAO;

    public RotaService(RotaItemService rotaItemService, RotaDAO rotaDAO) {
        this.rotaItemService = rotaItemService;
        this.rotaDAO = rotaDAO;
    }

    public Rota create(LocalDate weekCommencing) {
        Rota rota = new Rota();
        rota.setWeekStarting(weekCommencing);
        rota.setRotaItems(rotaItemService.rotaItems(weekCommencing));
        return rota;
    }

    public List<LocalDate> weeks() {
        LocalDate current = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        List<LocalDate> dates = new ArrayList<>();
        dates.add(current);
        return addWeeks(current, dates);
    }

    private List<LocalDate> addWeeks(LocalDate date, List<LocalDate> dates) {

        Objects.requireNonNull(date, "date must not be null");
        Objects.requireNonNull(dates, "dates must not be null");

        if(dates.size() == MAX_WEEKS) { return dates; }

        LocalDate next = LocalDate.from(date).with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        dates.add(next);

        return addWeeks(next, dates);
    }

    List<Person> unallocated(Long id) {
        List<Person> people = new ArrayList<>();
        return people;
    }
}

