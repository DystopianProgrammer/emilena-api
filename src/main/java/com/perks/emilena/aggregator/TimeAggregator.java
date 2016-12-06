package com.perks.emilena.aggregator;

import com.perks.emilena.api.Person;

import java.time.LocalTime;

/**
 * Works for time within a 24 hour period i.e. a single day
 *
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public interface TimeAggregator<T extends Person> {

    /**
     * Calculates the remaining time available for a given person. So if they are available from 9 - 5, and have
     * several appointments during the course of the day, this essentially tracks remaining time by looking at
     * their finish time, and their last appointment time.
     *
     * The logic requires efficiently assigning appointments from the earliest available time in the day to effectively
     * maximise resource.
     *
     * @return total hours remaining.
     */
    Long hoursRemaining();

    /**
     * Provides the person for the aggregation
     * @return the person
     */
    T getPerson();

    /**
     * Holds this persons finish time for the day
     * @return the finish time of the day
     */
    LocalTime getFinishTime();

    /**
     * Holds this person's last appointment time
     * @return the person's last appointment time
     */
    LocalTime getLastAppointment();
}
