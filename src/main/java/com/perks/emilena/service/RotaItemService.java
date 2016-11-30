package com.perks.emilena.service;

import com.perks.emilena.api.Client;
import com.perks.emilena.api.RotaItem;
import com.perks.emilena.api.Staff;
import com.perks.emilena.dao.ClientDAO;
import com.perks.emilena.dao.StaffDAO;
import com.perks.emilena.value.Appointment;

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
        List<RotaItem> rotaItems = new ArrayList<>();

        Consumer<DayOfWeek> dayOfWeekConsumer = (dayOfWeek) -> transform(staff, clients, dayOfWeek, rotaItems);

        Arrays.stream(DayOfWeek.values()).forEach(dayOfWeekConsumer);

        return rotaItems;
    }

    private void transform(List<Staff> staff, List<Client> clients, DayOfWeek dayOfWeek, List<RotaItem> rotaItems) {
        List<Appointment> appointments =
                appointmentService.pairByConfigurableLocationRadius(staff, clients, dayOfWeek);
        List<RotaItem> items = appointments.stream()
                .map(a -> {
                    RotaItem item = new RotaItem();
                    item.setStaff(a.getStaff());
                    item.setClient(a.getClient());
                    item.setDayOfWeek(dayOfWeek);
                    item.setStart(a.getStart());
                    item.setFinish(a.getFinish());
                    return item;
                })
                .collect(Collectors.toList());
        rotaItems.addAll(items);
    }

}
