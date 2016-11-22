package com.perks.emilena.api;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="rota_item_id")
    private RotaItem rotaItem;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "issued")
    private LocalDate issued;

    @Column(name = "created")
    private LocalDate created;

    @Column(name = "duration")
    private Long duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RotaItem getRotaItem() {
        return rotaItem;
    }

    public void setRotaItem(RotaItem rotaItem) {
        this.rotaItem = rotaItem;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getIssued() {
        return issued;
    }

    public void setIssued(LocalDate issued) {
        this.issued = issued;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id) &&
                Objects.equals(rotaItem, invoice.rotaItem) &&
                Objects.equals(amount, invoice.amount) &&
                Objects.equals(issued, invoice.issued) &&
                Objects.equals(created, invoice.created) &&
                Objects.equals(duration, invoice.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rotaItem, amount, issued, created, duration);
    }
}
