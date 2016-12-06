package com.perks.emilena.service;

import com.perks.emilena.api.Client;
import com.perks.emilena.api.RotaItem;
import com.perks.emilena.api.Staff;
import com.perks.emilena.api.type.PersonType;
import com.perks.emilena.value.Appointment;
import com.perks.emilena.value.TemporalStore;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.stream;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class RotaItemService {

    private final StaffService staffService;
    private final ClientService clientService;
    private final AppointmentService appointmentService;
    private final AllocationService allocationService;

    public RotaItemService(StaffService staffService,
                           ClientService clientService,
                           AppointmentService appointmentService,
                           AllocationService allocationService) {

        this.staffService = staffService;
        this.clientService = clientService;
        this.appointmentService = appointmentService;
        this.allocationService = allocationService;
    }

    public List<RotaItem> rotaItems(LocalDate weekCommencing) {

        List<Staff> staff = this.staffService.listAllActiveStaff();
        List<Client> clients = this.clientService.listAllActiveClients();

        Function<DayOfWeek, List<RotaItem>> itemMapper = (day) -> this.transform(staff, clients, day, weekCommencing);

        return stream(DayOfWeek.values())
                .flatMap(day -> itemMapper.apply(day).stream())
                .collect(Collectors.toList());
    }

    private List<RotaItem> transform(List<Staff> staff,
                                     List<Client> clients,
                                     DayOfWeek dayOfWeek,
                                     LocalDate weekCommencing) {


        // reduce by availability for the day of the week
        Map<PersonType, List<TemporalStore>> temporalStoreMap =
                appointmentService.reduceByAvailability(staff, clients, dayOfWeek);

        // group the temporal stores into buckets of acceptable radii and return unevenly distributed groups of
        // appointments
        List<List<Appointment>> appointmentMatrix =
                this.allocationService.allocateByRadius(temporalStoreMap.getOrDefault(PersonType.STAFF, newArrayList()),
                        temporalStoreMap.getOrDefault(PersonType.CLIENT, newArrayList()));

        // we need to even distribute staff across the appointments
        List<Appointment> appointments = appointmentService.distributeGroupedAppointments(appointmentMatrix);

        // finally transform to Rota Items!
        return appointments.stream()
                .map(a -> {
                    RotaItem item = new RotaItem();
                    item.setStaff(a.getStaff());
                    item.setClient(a.getClient());
                    item.setDayOfWeek(dayOfWeek);
                    item.setSupportDate(weekCommencing.plusDays(dayOfWeek.ordinal()));
                    item.setStart(a.getStart());
                    item.setFinish(a.getFinish());
                    return item;
                })
                .collect(Collectors.toList());
    }

}
