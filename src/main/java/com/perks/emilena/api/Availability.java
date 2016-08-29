package com.perks.emilena.api;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

    /**
	 * 
	 */
	private static final long serialVersionUID = -6450272490835617137L;

	@Id
    @Column(name = "avail_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    /**
     * The availability date
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    /**
     * The availability time from
     */
    @Temporal(TemporalType.TIME)
    @Column(name = "from_time", nullable = false)
    private Date fromDate;

    /**
     * The availability time to
     */
    @Temporal(TemporalType.TIME)
    @Column(name = "to_time", nullable = false)
    private Date toDate;

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
    private Integer numberOfHours;

    /**
     * The person associated to this availability
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
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
        return Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(fromDate, that.fromDate) &&
                Objects.equals(toDate, that.toDate) &&
                dayOfWeek == that.dayOfWeek &&
                Objects.equals(numberOfHours, that.numberOfHours) &&
                Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, fromDate, toDate, dayOfWeek, numberOfHours, person);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Availability{");
        sb.append("id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", fromDate=").append(fromDate);
        sb.append(", toDate=").append(toDate);
        sb.append(", dayOfWeek=").append(dayOfWeek);
        sb.append(", numberOfHours=").append(numberOfHours);
        sb.append(", person=").append(person);
        sb.append('}');
        return sb.toString();
    }
}
