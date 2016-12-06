package com.perks.emilena.value;

import com.perks.emilena.api.Address;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.Staff;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class Appointment {

    private final DistanceMatrix distanceMatrix;
    private final DayOfWeek dayOfWeek;
    private final Client client;
    private final Staff staff;
    private final LocalTime start;
    private final LocalTime finish;
    private final Address address;

    public static class Builder {
        private DistanceMatrix distanceMatrix;
        private DayOfWeek dayOfWeek;
        private Client client;
        private Staff staff;
        private LocalTime start;
        private LocalTime finish;
        private Address address;

        public Builder distanceMatrix(DistanceMatrix dm) {
            this.distanceMatrix = dm;
            return this;
        }

        public Builder dayOfWeek(DayOfWeek day) {
            this.dayOfWeek = day;
            return this;
        }

        public Builder client(Client c) {
            this.client = c;
            return this;
        }

        public Builder staff(Staff s) {
            this.staff = s;
            return this;
        }

        public Builder start(LocalTime s) {
            this.start = s;
            return this;
        }

        public Builder finish(LocalTime f) {
            this.finish = f;
            return this;
        }

        public Builder address(Address a) {
            this.address = a;
            return this;
        }

        public Appointment build() {
            return new Appointment(this);
        }
    }

    private Appointment(Builder builder) {
        this.distanceMatrix = builder.distanceMatrix;
        this.dayOfWeek = builder.dayOfWeek;
        this.staff = builder.staff;
        this.client = builder.client;
        this.start = builder.start;
        this.finish = builder.finish;
        this.address = builder.address;
    }

    public DistanceMatrix getDistanceMatrix() {
        return distanceMatrix;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public Client getClient() {
        return client;
    }

    public Staff getStaff() {
        return staff;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getFinish() {
        return finish;
    }

    public Address getAddress() {
        return address;
    }
}
