package com.perks.emilena.service;

import com.perks.emilena.api.Rota;
import com.perks.emilena.api.RotaItem;
import com.perks.emilena.filter.RotaItemFilter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class RotaService {

    private final AppointmentService appointmentService;
    private final RotaItemFilter rotaItemFilter;

    public RotaService(AppointmentService appointmentService, RotaItemFilter rotaItemFilter) {
        this.appointmentService = appointmentService;
        this.rotaItemFilter = rotaItemFilter;
    }

    public Rota create(LocalDate weekStarting) {

        List<RotaItem> rotaItems = this.appointmentService.appointmentsByDate(weekStarting).stream()
                .map(appointment -> {
                    RotaItem rotaItem = new RotaItem();
                    rotaItem.setDayOfWeek(appointment.getDay());
                    rotaItem.setStart(appointment.getStart());
                    rotaItem.setFinish(appointment.getFinish());
                    rotaItem.setClient(appointment.getClient());
                    rotaItem.setStaff(appointment.getStaff());
                    return rotaItem;
                })
                .filter(rotaItem -> rotaItem.getClient() != null && rotaItem.getStaff() != null)
                .collect(Collectors.toList());


        Rota rota = new Rota();
        rota.setRotaItems(rotaItems);
        rota.setWeekStarting(weekStarting);

        return rotaItemFilter.removeDuplicates(rota);
    }
}

