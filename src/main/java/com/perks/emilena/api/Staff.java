package com.perks.emilena.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

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

    @ManyToOne
    @JoinColumn(name = "STAFF_ID", referencedColumnName = "ID")
    private Availability availability;

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


}
