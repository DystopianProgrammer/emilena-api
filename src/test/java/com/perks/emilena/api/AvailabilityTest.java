package com.perks.emilena.api;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Created by 466707 on 12/11/2016.
 */
public class AvailabilityTest {

    @Test
    public void shouldReturnTrueWhereTwoAvailabilitiesOverlap() {

        // given
        DayOfWeek day = DayOfWeek.MONDAY;

        // client times
        LocalTime clientFrom = LocalTime.of(10, 0, 0);
        LocalTime clientTo = LocalTime.of(12, 0, 0);

        // the object to compare
        Availability clientAvailability = new Availability();
        clientAvailability.setDayOfWeek(day);
        clientAvailability.setFromTime(clientFrom);
        clientAvailability.setToTime(clientTo);
        clientAvailability.setPerson(new Client());

        // staff times
        LocalTime staffFrom = LocalTime.of(9, 0, 0);
        LocalTime staffTo = LocalTime.of(17, 0, 0);

        // the other object to compare
        Availability staffAvailability = new Availability();
        staffAvailability.setDayOfWeek(day);
        staffAvailability.setFromTime(staffFrom);
        staffAvailability.setToTime(staffTo);
        staffAvailability.setPerson(new Staff());

        assertThat(clientAvailability.compareTo(staffAvailability)).isEqualTo(0);
        assertThat(staffAvailability.compareTo(clientAvailability)).isEqualTo(0);
    }

    @Test
    public void shouldReturnTrueWhereTwoAvailabilitiesEqual() {

        // given
        DayOfWeek day = DayOfWeek.MONDAY;

        // client times
        LocalTime clientFrom = LocalTime.of(9, 0, 0);
        LocalTime clientTo = LocalTime.of(17, 0, 0);

        // the object to compare
        Availability clientAvailability = new Availability();
        clientAvailability.setDayOfWeek(day);
        clientAvailability.setFromTime(clientFrom);
        clientAvailability.setToTime(clientTo);
        clientAvailability.setPerson(new Client());

        // staff times
        LocalTime staffFrom = LocalTime.of(9, 0, 0);
        LocalTime staffTo = LocalTime.of(17, 0, 0);

        // the other object to compare
        Availability staffAvailability = new Availability();
        staffAvailability.setDayOfWeek(day);
        staffAvailability.setFromTime(staffFrom);
        staffAvailability.setToTime(staffTo);
        staffAvailability.setPerson(new Staff());

        assertThat(clientAvailability.compareTo(staffAvailability)).isEqualTo(0);
        assertThat(staffAvailability.compareTo(clientAvailability)).isEqualTo(0);
    }



    @Test
    public void shouldReturnFalseWhereNoAvailabiltiesWhereDayDiffers() {

            // client times
            LocalTime clientFrom = LocalTime.of(10, 0, 0);
            LocalTime clientTo = LocalTime.of(12, 0, 0);

            // the object to compare
            Availability clientAvailability = new Availability();
            clientAvailability.setDayOfWeek(DayOfWeek.MONDAY);
            clientAvailability.setFromTime(clientFrom);
            clientAvailability.setToTime(clientTo);
            clientAvailability.setPerson(new Client());

            // staff times
            LocalTime staffFrom = LocalTime.of(9, 0, 0);
            LocalTime staffTo = LocalTime.of(17, 0, 0);

            // the other object to compare
            Availability staffAvailability = new Availability();
            staffAvailability.setDayOfWeek(DayOfWeek.FRIDAY);
            staffAvailability.setFromTime(staffFrom);
            staffAvailability.setToTime(staffTo);
            staffAvailability.setPerson(new Staff());

            assertThat(clientAvailability.compareTo(staffAvailability)).isEqualTo(1);
            assertThat(staffAvailability.compareTo(clientAvailability)).isEqualTo(1);
    }

    @Test
    public void shouldReturnFalseWhenBothPersonSameType() {
        // given
        DayOfWeek day = DayOfWeek.MONDAY;

        // client times
        LocalTime clientFrom = LocalTime.of(10, 0, 0);
        LocalTime clientTo = LocalTime.of(12, 0, 0);

        // the object to compare
        Availability clientAvailability = new Availability();
        clientAvailability.setDayOfWeek(day);
        clientAvailability.setFromTime(clientFrom);
        clientAvailability.setToTime(clientTo);
        clientAvailability.setPerson(new Client());

        // staff times
        LocalTime staffFrom = LocalTime.of(9, 0, 0);
        LocalTime staffTo = LocalTime.of(17, 0, 0);

        // the other object to compare
        Availability staffAvailability = new Availability();
        staffAvailability.setDayOfWeek(day);
        staffAvailability.setFromTime(staffFrom);
        staffAvailability.setToTime(staffTo);
        staffAvailability.setPerson(new Client());

        assertThat(clientAvailability.compareTo(staffAvailability)).isEqualTo(1);
        assertThat(staffAvailability.compareTo(clientAvailability)).isEqualTo(1);
    }
}