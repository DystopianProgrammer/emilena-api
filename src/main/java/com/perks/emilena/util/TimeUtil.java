package com.perks.emilena.util;

import com.perks.emilena.aggregator.TimeAggregator;
import com.perks.emilena.api.Person;
import com.perks.emilena.value.Appointment;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static java.util.Objects.requireNonNull;

/**
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public class TimeUtil {

    /**
     * Returns the false if two appointments clash. That is if
     * 1) Each start time is the same
     * 2) Each finish time is the same
     * 3) The current appointment start time is between the other appointment's start and end times
     * 4) the current appointment end time is between the other appointment's start end time times
     * 5) the current appointment end time overlaps the duration until the other appointment's start time
     *
     * @param current   the current appointment
     * @param other     the other appointment to compare with
     * @param allowable the interval between appointments - in minutes
     * @return true if there is an overlap, false if it's safe
     */
    public static boolean isOverlap(Appointment current, Appointment other, Long allowable) {

        requireNonNull(current);
        requireNonNull(other);
        requireNonNull(allowable);

        return compare(current.getStart(), current.getFinish(), other.getStart(), other.getFinish(), allowable);
    }

    /**
     * Checks whether staff start and finish times are bookable for a given appointment time.
     *
     * @param staffStart
     * @param staffFinish
     * @param apptStart
     * @param apptFinish
     * @return true if bookable, otherwise false
     */
    public static boolean isBookable(LocalTime staffStart,
                                   LocalTime staffFinish,
                                   LocalTime apptStart,
                                   LocalTime apptFinish) {

        requireNonNull(staffStart);
        requireNonNull(staffFinish);
        requireNonNull(apptStart);
        requireNonNull(apptFinish);

        return (staffStart.isBefore(apptStart) || staffStart.equals(apptStart) &&
                staffFinish.isAfter(apptFinish) || staffFinish.equals(apptFinish));
    }

    private static boolean compare(LocalTime start1,
                                   LocalTime finish1,
                                   LocalTime start2,
                                   LocalTime finish2,
                                   Long allowable) {

        requireNonNull(start1);
        requireNonNull(finish1);
        requireNonNull(start2);
        requireNonNull(finish2);
        requireNonNull(allowable);

        if (start1.equals(start2)) return true;
        if (finish1.equals(finish2)) return true;
        if (start1.isAfter(start2) && start1.isBefore(finish2)) return true;
        if (finish1.isAfter(start2) && finish1.isBefore(finish2)) return true;
        if (ChronoUnit.MINUTES.between(finish1, start2) < allowable) return true;

        return false;
    }
}
