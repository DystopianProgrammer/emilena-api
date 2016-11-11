package com.perks.emilena.api;

import com.perks.emilena.api.type.AbsenceType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
@Table(name = "absence")
public class Absence {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Enumerated
    private AbsenceType absenceType;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "reason")
    private String reason;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Absence absence = (Absence) o;
        return Objects.equals(id, absence.id) &&
                absenceType == absence.absenceType &&
                Objects.equals(date, absence.date) &&
                Objects.equals(reason, absence.reason) &&
                Objects.equals(person, absence.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, absenceType, date, reason, person);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Absence{");
        sb.append("id=").append(id);
        sb.append(", absenceType=").append(absenceType);
        sb.append(", date=").append(date);
        sb.append(", reason='").append(reason).append('\'');
        sb.append(", person=").append(person);
        sb.append('}');
        return sb.toString();
    }
}
