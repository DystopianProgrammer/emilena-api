package com.perks.emilena.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 06/09/2016.
 */
@Entity
@Table(name = "assignment")
public class Assignment implements Serializable {

    private static final long serialVersionUID = -4916650199922161794L;

    @Id
    @Column(name = "assignment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Staff staff;

    @OneToOne
    private Client client;

    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(name = "time_from")
    private LocalTime timeFrom;

    @Column(name = "time_to")
    private LocalTime timeTo;

    @Column(name = "hours")
    private Long hours;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(LocalTime timeFrom) {
        this.timeFrom = timeFrom;
    }

    public LocalTime getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(LocalTime timeTo) {
        this.timeTo = timeTo;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Assignment that = (Assignment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(staff, that.staff) &&
                Objects.equals(client, that.client) &&
                dayOfWeek == that.dayOfWeek &&
                Objects.equals(timeFrom, that.timeFrom) &&
                Objects.equals(timeTo, that.timeTo) &&
                Objects.equals(hours, that.hours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, staff, client, dayOfWeek, timeFrom, timeTo, hours);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Assignment{");
        sb.append("id=").append(id);
        sb.append(", staff=").append(staff);
        sb.append(", client=").append(client);
        sb.append(", dayOfWeek=").append(dayOfWeek);
        sb.append(", timeFrom=").append(timeFrom);
        sb.append(", timeTo=").append(timeTo);
        sb.append(", hours=").append(hours);
        sb.append('}');
        return sb.toString();
    }
}
