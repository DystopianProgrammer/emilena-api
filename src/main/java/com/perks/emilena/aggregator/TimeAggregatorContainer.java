package com.perks.emilena.aggregator;

import com.perks.emilena.api.Person;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static java.util.Objects.requireNonNull;

/**
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public class TimeAggregatorContainer<T extends Person> implements TimeAggregator {

    private final T person;
    private final LocalTime finishTime;
    private final LocalTime lastAppointment;

    public TimeAggregatorContainer(T person, LocalTime finishTime, LocalTime lastAppointment) {
        this.person = requireNonNull(person, "person must not be null");
        this.finishTime = requireNonNull(finishTime, "finish time must not be null");
        this.lastAppointment = requireNonNull(lastAppointment, "last appointment must not be null");
    }

    @Override
    public T getPerson() {
        return person;
    }

    @Override
    public LocalTime getFinishTime() {
        return finishTime;
    }

    @Override
    public LocalTime getLastAppointment() {
        return lastAppointment;
    }

    @Override
    public Long hoursRemaining() {
        return ChronoUnit.HOURS.between(getFinishTime(), getLastAppointment());
    }
}
