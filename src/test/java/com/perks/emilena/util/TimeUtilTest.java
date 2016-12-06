package com.perks.emilena.util;

import com.perks.emilena.value.Appointment;
import org.junit.Test;

import static com.perks.emilena.util.TimeUtil.isOverlap;
import static java.time.LocalTime.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public class TimeUtilTest {

    @Test
    public void testOverlappingStartTime() {

        Appointment current = new Appointment.Builder().start(of(10, 00)).finish(of(12, 00)).build();
        Appointment other = new Appointment.Builder().start(of(11, 00)).finish(of(13, 00)).build();

        boolean overlap = isOverlap(current, other, 60L);

        assertThat(overlap).isTrue();
    }

    @Test
    public void testOverlappingFinishTime() {

        Appointment current = new Appointment.Builder().start(of(10, 00)).finish(of(12, 00)).build();
        Appointment other = new Appointment.Builder().start(of(9, 00)).finish(of(11, 00)).build();

        boolean overlap = isOverlap(current, other, 60L);

        assertThat(overlap).isTrue();
    }

    @Test
    public void testWhereAllowableIntervalNotMet() {

        Appointment current = new Appointment.Builder().start(of(10, 00)).finish(of(12, 00)).build();
        Appointment other = new Appointment.Builder().start(of(12, 30)).finish(of(15, 00)).build();

        boolean overlap = isOverlap(current, other, 60L);

        assertThat(overlap).isTrue();
    }

    @Test
    public void testEqualStartTimes() {

        Appointment current = new Appointment.Builder().start(of(10, 00)).finish(of(12, 00)).build();
        Appointment other = new Appointment.Builder().start(of(10, 00)).finish(of(13, 00)).build();

        boolean overlap = isOverlap(current, other, 60L);

        assertThat(overlap).isTrue();

    }

    @Test
    public void testEqualFinishTimes() {

        Appointment current = new Appointment.Builder().start(of(10, 00)).finish(of(12, 00)).build();
        Appointment other = new Appointment.Builder().start(of(11, 00)).finish(of(12, 00)).build();

        boolean overlap = isOverlap(current, other, 60L);

        assertThat(overlap).isTrue();
    }

    @Test
    public void testWhereAllIsGood() {

        Appointment current = new Appointment.Builder().start(of(10, 00)).finish(of(12, 00)).build();
        Appointment other = new Appointment.Builder().start(of(13, 00)).finish(of(15, 00)).build();

        boolean overlap = isOverlap(current, other, 60L);

        assertThat(overlap).isFalse();
    }
}