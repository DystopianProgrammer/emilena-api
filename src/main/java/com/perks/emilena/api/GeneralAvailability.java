package com.perks.emilena.api;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.DayOfWeek;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 01/08/2016.
 */
@Entity
@Table(name = "general_availability")
public class GeneralAvailability {

    @Id
    @Column(name = "ga_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @ElementCollection(targetClass = DayOfWeek.class, fetch = FetchType.EAGER)
    @JoinTable(name = "days_of_week_link", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "days_of_week", nullable = false)
    @Enumerated(EnumType.STRING)
    private Collection<DayOfWeek> daysOfWeek;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(Collection<DayOfWeek> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
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
        GeneralAvailability that = (GeneralAvailability) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(daysOfWeek, that.daysOfWeek) &&
                Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, daysOfWeek, person);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GeneralAvailability{");
        sb.append("id=").append(id);
        sb.append(", daysOfWeek=").append(daysOfWeek);
        sb.append(", person=").append(person);
        sb.append('}');
        return sb.toString();
    }
}
