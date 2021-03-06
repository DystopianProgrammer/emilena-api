package com.perks.emilena.api;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
@Table(name = "rota_item")
public class RotaItem implements Comparable<RotaItem> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(name = "support_date")
    private LocalDate supportDate;

    @Column(name = "start_time")
    private LocalTime start;

    @Column(name = "finish_time")
    private LocalTime finish;

    @OneToOne(fetch = FetchType.EAGER)
    private Client client;

    @OneToOne(fetch = FetchType.EAGER)
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

    public LocalDate getSupportDate() {
        return supportDate;
    }

    public void setSupportDate(LocalDate supportDate) {
        this.supportDate = supportDate;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RotaItem rotaItem = (RotaItem) o;
        return Objects.equals(id, rotaItem.id) &&
                dayOfWeek == rotaItem.dayOfWeek &&
                Objects.equals(supportDate, rotaItem.supportDate) &&
                Objects.equals(start, rotaItem.start) &&
                Objects.equals(finish, rotaItem.finish) &&
                Objects.equals(client, rotaItem.client) &&
                Objects.equals(staff, rotaItem.staff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dayOfWeek, supportDate, start, finish, client, staff);
    }

    @Override
    public int compareTo(RotaItem other) {
        if(Objects.equals(this.getClient().getId(), other.getClient().getId()) &&
                this.getStart().equals(other.getStart())) { return 0; }
        return -1;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("startTime", start)
                .add("finishTime", finish)
                .add("dayOfWeek", dayOfWeek)
                .add("client", client.getId())
                .toString();
    }
}
