package com.perks.emilena.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Date;
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
public class Availability implements Serializable {

    /**
     * The Date and time the availability is from
     */
    @Column(name = "DATE_AND_TIME")
    private Date dateAndTime;

    /**
     * A representation for the day - this can be derived from date and time.
     */
    @Column(name = "DAY_OF_WEEK")
    @Enumerated
    private DayOfWeek dayOfWeek;

    /**
     * The number of hours for the given day that can be allocated.
     */
    @Column(name = "NUMBER_OF_HOURS")
    private Integer numberOfHours;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getNumberOfHours() {
        return numberOfHours;
    }

    public void setNumberOfHours(Integer numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Availability that = (Availability) o;
        return Objects.equals(dateAndTime, that.dateAndTime) &&
                dayOfWeek == that.dayOfWeek &&
                Objects.equals(numberOfHours, that.numberOfHours) &&
                Objects.equals(id, that.id) &&
                Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateAndTime, dayOfWeek, numberOfHours, id, person);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Availability{");
        sb.append("dateAndTime=").append(dateAndTime);
        sb.append(", dayOfWeek=").append(dayOfWeek);
        sb.append(", numberOfHours=").append(numberOfHours);
        sb.append(", id=").append(id);
        sb.append(", person=").append(person);
        sb.append('}');
        return sb.toString();
    }
}
