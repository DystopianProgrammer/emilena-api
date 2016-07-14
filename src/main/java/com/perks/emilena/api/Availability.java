package com.perks.emilena.api;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
public class Availability implements Serializable {

    @Column
    private LocalDateTime dateAndTime;
    @Column
    @OneToMany(targetEntity = Person.class, fetch = FetchType.EAGER)
    private List<Person> persons;
    @Id
    protected Long id;

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                Objects.equals(persons, that.persons) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateAndTime, persons, id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Availability{");
        sb.append("dateAndTime=").append(dateAndTime);
        sb.append(", persons=").append(persons);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
