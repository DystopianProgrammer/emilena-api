package com.perks.emilena.api;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * Simply for storing visitor ip addresses.
 *
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
@Table(name = "traffic")
public class Traffic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "traffic_date")
    private String date;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Traffic traffic = (Traffic) o;
        return Objects.equals(ipAddress, traffic.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipAddress);
    }
}
