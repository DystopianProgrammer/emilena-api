package com.perks.emilena.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
public class Staff extends Person {

    @Enumerated
    private ContractType contractType;
    @Enumerated
    private StaffType staffType;
    @Column
    @ManyToMany(targetEntity = Client.class)
    private List<Client> clients;

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public StaffType getStaffType() {
        return staffType;
    }

    public void setStaffType(StaffType staffType) {
        this.staffType = staffType;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
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
        Staff staff = (Staff) o;
        return contractType == staff.contractType &&
                staffType == staff.staffType &&
                Objects.equals(clients, staff.clients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contractType, staffType, clients);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Staff{");
        sb.append("contractType=").append(contractType);
        sb.append(", staffType=").append(staffType);
        sb.append(", clients=").append(clients);
        sb.append('}');
        return sb.toString();
    }
}
