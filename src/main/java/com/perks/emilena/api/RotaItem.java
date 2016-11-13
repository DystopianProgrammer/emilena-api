package com.perks.emilena.api;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class RotaItem implements Comparable<RotaItem> {

    private Long id;
    private DayOfWeek dayOfWeek;
    private LocalTime start;
    private LocalTime finish;
    private Client client;
    private Staff staff;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getFinish() {
        return finish;
    }

    public void setFinish(LocalTime finish) {
        this.finish = finish;
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

    @Override
    public int compareTo(RotaItem other) {
        if(this.getClient().equals(other.getClient()) && this.getDayOfWeek().equals(other.getDayOfWeek())
                && this.getStart().equals(other.getStart())) {
            return 0;
        } else if(this.getStaff().equals(other.getStaff()) && this.getDayOfWeek().equals(other.getDayOfWeek())
                && this.getStart().equals(other.getStart())) {
            return 0;
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RotaItem rotaItem = (RotaItem) o;
        return Objects.equals(id, rotaItem.id) &&
                dayOfWeek == rotaItem.dayOfWeek &&
                Objects.equals(start, rotaItem.start) &&
                Objects.equals(finish, rotaItem.finish) &&
                Objects.equals(client, rotaItem.client) &&
                Objects.equals(staff, rotaItem.staff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dayOfWeek, start, finish, client, staff);
    }
}
