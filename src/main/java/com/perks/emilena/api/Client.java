package com.perks.emilena.api;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
@Table(name = "CLIENT")
@NamedQueries({
        @NamedQuery(name = "com.perks.emilena.Client.findAll",
                query = "select c from Client c")
})
public class Client extends Person {

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Staff.class)
    @JoinTable(name = "CLIENTS_STAFF",
            joinColumns =
            @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID"),
            inverseJoinColumns =
            @JoinColumn(name = "STAFF_ID", referencedColumnName = "ID"))
    private List<Staff> staff;

    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Client client = (Client) o;
        return Objects.equals(staff, client.staff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), staff);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("staff=").append(staff);
        sb.append('}');
        return sb.toString();
    }
}
