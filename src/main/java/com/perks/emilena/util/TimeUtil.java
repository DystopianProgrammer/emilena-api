package com.perks.emilena.util;

import java.time.LocalTime;

import static java.util.Objects.requireNonNull;

/**
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public class TimeUtil {


    public static boolean isBookable(LocalTime clientStart, LocalTime clientEnd,
                                   LocalTime staffStart, LocalTime staffEnd) {

        requireNonNull(clientStart);
        requireNonNull(clientEnd);
        requireNonNull(staffStart);
        requireNonNull(staffEnd);

        if (clientStart.equals(staffStart) && clientEnd.equals(staffEnd)) return true;
        if (clientStart.isAfter(staffStart) && clientStart.isBefore(staffEnd)) return true;
        if (clientEnd.isAfter(staffStart) && clientEnd.isBefore(staffEnd)) return true;
        if (clientEnd.isAfter(staffStart) && clientEnd.equals(staffEnd)) return true;

        return false;
    }
}
