package com.perks.emilena.api;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Objects;

/**
 *
 * In a Many-to-one relationship (Availability.* - Person.1) The availability represents a single day with the number of hours
 * for that day. A person can have many 'availabilities'
 *
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
@Table(name = "availability")
public class Availability implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    /**
     * The availability time from
     */
    @Column(name = "from_time", nullable = false)
    private LocalTime fromTime;

    /**
     * The availability time to
     */
    @Column(name = "to_time", nullable = false)
    private LocalTime toTime;

    /**
     * A representation for the day - this can be derived from date and time.
     * This is inferred.
     */
    @Column(name = "day_of_week")
    @Enumerated
    private DayOfWeek dayOfWeek;

    /**
     * The number of hours for the given day that can be allocated.
     */
    @Column(name = "number_of_hours")
    private Long numberOfHours;

    public LocalTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(LocalTime fromTime) {
        this.fromTime = fromTime;
    }

    public LocalTime getToTime() {
        return toTime;
    }

    public void setToTime(LocalTime toTime) {
        this.toTime = toTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Long getNumberOfHours() {
        return numberOfHours;
    }

    public void setNumberOfHours(Long numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Availability that = (Availability) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(fromTime, that.fromTime) &&
                Objects.equals(toTime, that.toTime) &&
                dayOfWeek == that.dayOfWeek &&
                Objects.equals(numberOfHours, that.numberOfHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromTime, toTime, dayOfWeek, numberOfHours);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Availability{");
        sb.append("id=").append(id);
        sb.append(", fromTime=").append(fromTime);
        sb.append(", toTime=").append(toTime);
        sb.append(", dayOfWeek=").append(dayOfWeek);
        sb.append(", numberOfHours=").append(numberOfHours);
        sb.append('}');
        return sb.toString();
    }
}
