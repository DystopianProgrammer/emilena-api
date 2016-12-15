package com.perks.emilena.util;

import com.perks.emilena.api.Availability;
import com.perks.emilena.api.RotaItem;

import java.time.Duration;
import java.time.LocalTime;

import static java.util.Objects.requireNonNull;

/**
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public class TimeUtil {


    public static boolean hasOverlap(RotaItem rotaItem, Availability availability) {

        requireNonNull(rotaItem);
        requireNonNull(availability);

        LocalTime rotaItemStart = rotaItem.getStart();
        LocalTime rotaItemFinish = rotaItem.getFinish();

        long appointmentLength = Duration.between(rotaItemStart, rotaItemFinish).toHours();

        if(rotaItemStart.equals(availability.getFromTime())) return true;
        if(rotaItemFinish.equals(availability.getFromTime())) return true; // maybe

//        Long durationFromAvailToRotaItem =
//                Duration.between(availability.getFromTime(), rotaItem.getFinish()).toHours();
//
//        if(appointmentLength - durationFromAvailToRotaItem > 60L) return true; // TODO configurable

        return false;
    }

    public static boolean isBookable(LocalTime clientStart, LocalTime clientEnd,
                                   LocalTime staffStart, LocalTime staffEnd) {

        requireNonNull(clientStart);
        requireNonNull(clientEnd);
        requireNonNull(staffStart);
        requireNonNull(staffEnd);

        if (clientStart.equals(staffStart)) return true;
        if (clientEnd.equals(staffEnd)) return true;
        if (clientStart.isAfter(staffStart) && clientStart.isBefore(staffEnd)) return true;
        if (clientEnd.isAfter(staffStart) && clientEnd.isBefore(staffEnd)) return true;

        return false;
    }
}
