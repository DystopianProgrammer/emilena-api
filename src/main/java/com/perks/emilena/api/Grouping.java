package com.perks.emilena.api;

import java.time.LocalDateTime;

/**
 * Created by 466707 on 05/11/2016.
 */
public class Grouping {

    LocalDateTime start;

    LocalDateTime end;

    Client client;

    Staff staff;

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
