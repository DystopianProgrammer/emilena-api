package com.perks.emilena.api;

import com.perks.emilena.api.type.AbsenceType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
@Table(name = "absence")
public class Absence implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Enumerated
    private AbsenceType absenceType;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "reason")
    private String reason;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Absence absence = (Absence) o;
        return Objects.equals(id, absence.id) &&
                absenceType == absence.absenceType &&
                Objects.equals(date, absence.date) &&
                Objects.equals(reason, absence.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, absenceType, date, reason);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Absence{");
        sb.append("id=").append(id);
        sb.append(", absenceType=").append(absenceType);
        sb.append(", date=").append(date);
        sb.append(", reason='").append(reason).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
