package com.perks.emilena.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 31/08/2016.
 */
@Entity
@Table(name = "invoice")
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @OneToOne
    @JoinColumn(name = "appt_id")
    private Appointment appointment;

    /**
     * Calculated from the appointment start and end times
     */
    @Column(name = "inferred_hours")
    private Long inferredHours;

    /**
     * Updateable if appointment had modified duration
     */
    @Column(name = "actual_hours")
    private Long actualHours;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Long getInferredHours() {
        return inferredHours;
    }

    public void setInferredHours(Long inferredHours) {
        this.inferredHours = inferredHours;
    }

    public Long getActualHours() {
        return actualHours;
    }

    public void setActualHours(Long actualHours) {
        this.actualHours = actualHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id) &&
                Objects.equals(appointment, invoice.appointment) &&
                Objects.equals(inferredHours, invoice.inferredHours) &&
                Objects.equals(actualHours, invoice.actualHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appointment, inferredHours, actualHours);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Invoice{");
        sb.append("id=").append(id);
        sb.append(", appointment=").append(appointment);
        sb.append(", inferredHours=").append(inferredHours);
        sb.append(", actualHours=").append(actualHours);
        sb.append('}');
        return sb.toString();
    }
}
