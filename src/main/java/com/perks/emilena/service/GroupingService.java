package com.perks.emilena.service;

import com.perks.emilena.api.Grouping;
import com.perks.emilena.api.Person;

import java.time.DayOfWeek;
import java.util.Optional;

/**
 * Created by 466707 on 05/11/2016.
 */
public class GroupingService {


    public Optional<Grouping> groupByLocalDateTime(DayOfWeek dayOfWeek) {

        // we wanna get all the staff and all the clients for the given day
        Grouping grouping = null;



        return Optional.ofNullable(grouping);
    }



    boolean hasDay(Person person, DayOfWeek dayOfWeek) {
        return person.getAvailabilities().stream().anyMatch(a -> a.getDayOfWeek().equals(dayOfWeek));
    }
}
