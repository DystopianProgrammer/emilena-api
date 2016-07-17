package com.perks.emilena.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "com.perks.emilena.Staff.findAll",
                    query = "select s from Staff s")
})
public class Staff extends Person {

    @Column(name = "CONTRACT_TYPE")
    @Enumerated
    private ContractType contractType;

    @Column(name = "STAFF_TYPE")
    @Enumerated
    private StaffType staffType;

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
                staffType == staff.staffType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contractType, staffType);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Staff{");
        sb.append("contractType=").append(contractType);
        sb.append(", staffType=").append(staffType);
        sb.append('}');
        return sb.toString();
    }
}
