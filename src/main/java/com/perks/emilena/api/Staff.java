package com.perks.emilena.api;

import com.perks.emilena.api.type.ContractType;
import com.perks.emilena.api.type.StaffType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
@Table(name = "STAFF")
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

    @Column(name = "CONTRACTED_HOURS")
    private Integer contractedHours;

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

    public Integer getContractedHours() {
        return contractedHours;
    }

    public void setContractedHours(Integer contractedHours) {
        this.contractedHours = contractedHours;
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
                Objects.equals(contractedHours, staff.contractedHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contractType, staffType, contractedHours);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Staff{");
        sb.append("contractType=").append(contractType);
        sb.append(", staffType=").append(staffType);
        sb.append(", contractedHours=").append(contractedHours);
        sb.append('}');
        return sb.toString();
    }
}
