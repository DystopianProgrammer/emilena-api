package com.perks.emilena.api;

import com.perks.emilena.api.type.ContractType;
import com.perks.emilena.api.type.StaffType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
@Table(name = "staff")
@NamedQueries({
        @NamedQuery(name = "com.perks.emilena.Staff.findAll",
                    query = "select s from Staff s")
})
public class Staff extends Person {

    @Column(name = "contract_type")
    @Enumerated
    private ContractType contractType;

    @Column(name = "staff_type")
    @Enumerated
    private StaffType staffType;

    @Column(name = "contracted_hours")
    private Integer contractedHours;

    @OneToOne
    @JoinColumn(name = "system_user_id")
    private SystemUser systemUser;

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

    public SystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }
}
