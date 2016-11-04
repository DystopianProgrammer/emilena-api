package com.perks.emilena.api;

import com.perks.emilena.api.type.ContractType;
import com.perks.emilena.api.type.StaffType;

import javax.persistence.*;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
@Table(name = "staff")
@DiscriminatorValue("S")
public class Staff extends Person {

	private static final long serialVersionUID = -4916650133488161794L;

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
