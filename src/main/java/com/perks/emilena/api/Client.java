package com.perks.emilena.api;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
@Table(name = "client")
@DiscriminatorValue("C")
public class Client extends Person {

	private static final long serialVersionUID = -2585794734130213464L;
	
	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Staff.class)
    @JoinTable(name = "clients_staff",
            joinColumns =
            @JoinColumn(name = "client_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "staff_id", referencedColumnName = "id"))
    private Collection<Staff> staff;

    public Collection<Staff> getStaff() {
        return staff;
    }

    public void setStaff(Collection<Staff> staff) {
        this.staff = staff;
    }
}
