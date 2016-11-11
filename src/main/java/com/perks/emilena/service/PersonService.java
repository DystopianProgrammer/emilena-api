package com.perks.emilena.service;

import com.perks.emilena.api.Availability;
import com.perks.emilena.api.Person;
import com.perks.emilena.dao.AbsenceDAO;
import com.perks.emilena.dao.AvailabilityDAO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public class PersonService {

    private final AvailabilityDAO availabilityDAO;
    private final AbsenceDAO absenceDAO;

    public PersonService(AvailabilityDAO availabilityDAO, AbsenceDAO absenceDAO) {
        this.availabilityDAO = availabilityDAO;
        this.absenceDAO = absenceDAO;
    }

    public void update(Person person) {

        List<Availability> availabilities = person.getAvailabilities().stream()
                .map(availability -> {
                    Availability a = new Availability();
                    a.setPerson(person);
                    a.setFromTime(availability.getFromTime());
                    a.setToTime(availability.getToTime());
                    a.setDayOfWeek(availability.getDayOfWeek());
                    return a;
                })
                .collect(Collectors.toList());

        availabilities.forEach(this.availabilityDAO::update);

        // FIXME put this back in
//        if(person.getAbsences() != null) {
//            person.getAbsences().forEach(this.absenceDAO::update);
//        }
    }
}
