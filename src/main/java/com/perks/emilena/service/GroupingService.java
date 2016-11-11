package com.perks.emilena.service;

import com.perks.emilena.api.Grouping;
import com.perks.emilena.dao.AvailabilityDAO;
import com.perks.emilena.dao.ClientDAO;
import com.perks.emilena.dao.StaffDAO;

import java.time.DayOfWeek;
import java.util.Optional;

/**
 * Created by 466707 on 05/11/2016.
 */
public class GroupingService {

    private final StaffDAO staffDAO;
    private final ClientDAO clientDAO;
    private final AvailabilityDAO availabilityDAO;

    public GroupingService(StaffDAO staffDAO, ClientDAO clientDAO, AvailabilityDAO availabilityDAO) {
        this.staffDAO = staffDAO;
        this.clientDAO = clientDAO;
        this.availabilityDAO = availabilityDAO;
    }

    public Optional<Grouping> groupByLocalDateTime(DayOfWeek dayOfWeek) {
        return null;
    }

}
