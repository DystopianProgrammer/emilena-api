package com.perks.emilena.api;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
@Table(name = "rota")
public class Rota {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "week_commencing")
    private LocalDate weekStarting;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "rota_entry", joinColumns = { @JoinColumn(name = "rota_id") },
            inverseJoinColumns = { @JoinColumn(name = "rota_item_id") })
    private List<RotaItem> rotaItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getWeekStarting() {
        return weekStarting;
    }

    public void setWeekStarting(LocalDate weekStarting) {
        this.weekStarting = weekStarting;
    }

    public List<RotaItem> getRotaItems() {
        return rotaItems;
    }

    public void setRotaItems(List<RotaItem> rotaItems) {
        this.rotaItems = rotaItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rota rota = (Rota) o;
        return Objects.equals(id, rota.id) &&
                Objects.equals(weekStarting, rota.weekStarting) &&
                Objects.equals(rotaItems, rota.rotaItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weekStarting, rotaItems);
    }
}
