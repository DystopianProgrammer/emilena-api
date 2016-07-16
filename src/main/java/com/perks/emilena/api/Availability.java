package com.perks.emilena.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
public class Availability implements Serializable {

    @Column(name = "DATE_AND_TIME")
    private LocalDateTime dateAndTime;

    @Column
    @OneToMany(mappedBy = "availability", targetEntity = Staff.class, fetch = FetchType.EAGER)
    private List<Staff> staff;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
