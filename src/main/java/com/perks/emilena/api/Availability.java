package com.perks.emilena.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

/**
 * In a Many-to-one relationship (Availability.* - Person.1) The availability represents a single day with the number of hours
 * for that day. A person can have many 'availabilities'
 * <p>
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "availability")
public class Availability implements Comparable<Availability> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "from_time", nullable = false)
    private LocalTime fromTime;

    @Column(name = "to_time", nullable = false)
    private LocalTime toTime;

    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    // TODO possibly remove this annotation. It may save us from the PersonDeserializer hack though.
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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
                Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromTime, toTime, dayOfWeek, person);
    }

    @Override
    public int compareTo(Availability other) {

        if (this.person.getClass().isInstance(other.getPerson())) {
            return 1;
        }

        if (this.person instanceof Client && this.dayOfWeek.equals(other.dayOfWeek)) {
            if (this.fromTime.equals(other.getFromTime()) || this.fromTime.isAfter(other.getFromTime()) &&
                    this.toTime.equals(other.getToTime()) || this.toTime.isBefore(other.getToTime())) {
                return 0;
            }
        }

        if (this.person instanceof Staff && this.dayOfWeek.equals(other.dayOfWeek)) {
            if (this.fromTime.equals(other.getFromTime()) || this.fromTime.isBefore(other.getFromTime()) &&
                    this.toTime.equals(other.getToTime()) || this.toTime.isAfter(other.getToTime())) {
                return 0;
            }
        }

        return 1;
    }
}
