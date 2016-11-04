package com.perks.emilena.service;

import com.perks.emilena.api.Assignment;
import com.perks.emilena.api.Availability;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.Rota;
import com.perks.emilena.api.Staff;
import com.perks.emilena.dao.ClientDAO;
import com.perks.emilena.dao.StaffDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Geoff Perks
 * Date: 06/09/2016.
 */
public class RotaService {

    private static final Logger logger = LoggerFactory.getLogger(RotaService.class);

    private final ClientDAO clientDAO;
    private final StaffDAO staffDAO;

    public RotaService(ClientDAO clientDAO, StaffDAO staffDAO) {
        this.clientDAO = clientDAO;
        this.staffDAO = staffDAO;
    }

    public Rota rotaForWeek(LocalDate localDate) {

        List<Client> clients = clientDAO.findAll();
        List<Staff> staff = staffDAO.findAll();

        Rota rota = new Rota();
        rota.setWeekCommencing(localDate);
        rota.setMonday(assignments(DayOfWeek.MONDAY, clients, staff));
        rota.setTuesday(assignments(DayOfWeek.TUESDAY, clients, staff));
        rota.setWednesday(assignments(DayOfWeek.WEDNESDAY, clients, staff));
        rota.setThursday(assignments(DayOfWeek.THURSDAY, clients, staff));
        rota.setFriday(assignments(DayOfWeek.FRIDAY, clients, staff));
        rota.setSaturday(assignments(DayOfWeek.SATURDAY, clients, staff));
        rota.setSunday(assignments(DayOfWeek.SUNDAY, clients, staff));
        
        return rota;
    }

    private Collection<Assignment> assignments(DayOfWeek dayOfWeek, List<Client> clients, List<Staff> staff) {

        List<Availability> staffAvailabilities = staff.stream().map(s -> s.getAvailabilities())
                .flatMap(a -> a.stream())
                .collect(Collectors.toList());

       return null;

    }


}
