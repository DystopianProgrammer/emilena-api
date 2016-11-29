package com.perks.emilena.service;

import com.perks.emilena.api.Client;
import com.perks.emilena.api.RotaItem;
import com.perks.emilena.api.Staff;
import com.perks.emilena.dao.ClientDAO;
import com.perks.emilena.dao.StaffDAO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class RotaItemService {

    private final StaffDAO staffDAO;
    private final ClientDAO clientDAO;
    private final AppointmentService appointmentService;

    public RotaItemService(StaffDAO staffDAO, ClientDAO clientDAO, AppointmentService appointmentService) {
        this.staffDAO = staffDAO;
        this.clientDAO = clientDAO;
        this.appointmentService = appointmentService;
    }

    public List<RotaItem> rotaItems(LocalDate weekCommencing) {


        List<Staff> staff = this.staffDAO.findAllActive();
        List<Client> clients = this.clientDAO.findAllActive();

        Consumer<DayOfWeek> dayOfWeekConsumer = (dayOfWeek) -> {
            appointmentService.pairByConfigurableLocationRadius(staff, clients, dayOfWeek);
        };

        Arrays.stream(DayOfWeek.values()).forEach(dayOfWeekConsumer);

        List<RotaItem> rotaItems = new ArrayList<>();
        return rotaItems;
    }

}
