package com.perks.emilena.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
public class Absence implements Serializable {

    @Enumerated
    private AbsenceType absenceType;
    @Column
    private LocalDateTime date;
    @Column
    private String reason;
    @Column
    private Person person;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AbsenceType getAbsenceType() {
        return absenceType;
    }

    public void setAbsenceType(AbsenceType absenceType) {
        this.absenceType = absenceType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
        Absence absence = (Absence) o;
        return absenceType == absence.absenceType &&
                Objects.equals(date, absence.date) &&
                Objects.equals(reason, absence.reason) &&
                Objects.equals(person, absence.person) &&
                Objects.equals(id, absence.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(absenceType, date, reason, person, id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Absence{");
        sb.append("absenceType=").append(absenceType);
        sb.append(", date=").append(date);
        sb.append(", reason='").append(reason).append('\'');
        sb.append(", person=").append(person);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
