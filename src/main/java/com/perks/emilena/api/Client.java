package com.perks.emilena.api;

import java.util.List;
import java.util.Set;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class Client extends Person {

    private Long id;
    private Set<Staff> staff;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Staff> getStaff() {
        return staff;
    }

    public void setStaff(Set<Staff> staff) {
        this.staff = staff;
    }
}
