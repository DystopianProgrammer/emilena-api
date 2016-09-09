package com.perks.emilena.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 06/09/2016.
 */
@Entity
@Table(name = "rota")
public class Rota implements Serializable {

    private static final long serialVersionUID = -4916650133477722794L;

    @Id
    @Column(name = "rota_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "week_commencing")
    private LocalDateTime weekCommencing;

    @OneToMany(mappedBy = "id")
    private Collection<Assignment> monday;

    @OneToMany(mappedBy = "id")
    private Collection<Assignment> tuesday;

    @OneToMany(mappedBy = "id")
    private Collection<Assignment> wednesday;

    @OneToMany(mappedBy = "id")
    private Collection<Assignment> thursday;

    @OneToMany(mappedBy = "id")
    private Collection<Assignment> friday;

    @OneToMany(mappedBy = "id")
    private Collection<Assignment> saturday;

    @OneToMany(mappedBy = "id")
    private Collection<Assignment> sunday;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getWeekCommencing() {
        return weekCommencing;
    }

    public void setWeekCommencing(LocalDateTime weekCommencing) {
        this.weekCommencing = weekCommencing;
    }

    public Collection<Assignment> getMonday() {
        return monday;
    }

    public void setMonday(Collection<Assignment> monday) {
        this.monday = monday;
    }

    public Collection<Assignment> getTuesday() {
        return tuesday;
    }

    public void setTuesday(Collection<Assignment> tuesday) {
        this.tuesday = tuesday;
    }

    public Collection<Assignment> getWednesday() {
        return wednesday;
    }

    public void setWednesday(Collection<Assignment> wednesday) {
        this.wednesday = wednesday;
    }

    public Collection<Assignment> getThursday() {
        return thursday;
    }

    public void setThursday(Collection<Assignment> thursday) {
        this.thursday = thursday;
    }

    public Collection<Assignment> getFriday() {
        return friday;
    }

    public void setFriday(Collection<Assignment> friday) {
        this.friday = friday;
    }

    public Collection<Assignment> getSaturday() {
        return saturday;
    }

    public void setSaturday(Collection<Assignment> saturday) {
        this.saturday = saturday;
    }

    public Collection<Assignment> getSunday() {
        return sunday;
    }

    public void setSunday(Collection<Assignment> sunday) {
        this.sunday = sunday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rota rota = (Rota) o;
        return Objects.equals(id, rota.id) &&
                Objects.equals(weekCommencing, rota.weekCommencing) &&
                Objects.equals(monday, rota.monday) &&
                Objects.equals(tuesday, rota.tuesday) &&
                Objects.equals(wednesday, rota.wednesday) &&
                Objects.equals(thursday, rota.thursday) &&
                Objects.equals(friday, rota.friday) &&
                Objects.equals(saturday, rota.saturday) &&
                Objects.equals(sunday, rota.sunday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weekCommencing, monday, tuesday, wednesday, thursday, friday, saturday, sunday);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rota{");
        sb.append("id=").append(id);
        sb.append(", weekCommencing=").append(weekCommencing);
        sb.append(", monday=").append(monday);
        sb.append(", tuesday=").append(tuesday);
        sb.append(", wednesday=").append(wednesday);
        sb.append(", thursday=").append(thursday);
        sb.append(", friday=").append(friday);
        sb.append(", saturday=").append(saturday);
        sb.append(", sunday=").append(sunday);
        sb.append('}');
        return sb.toString();
    }
}
