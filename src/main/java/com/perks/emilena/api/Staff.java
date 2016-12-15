package com.perks.emilena.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.perks.emilena.api.type.ContractType;
import com.perks.emilena.api.type.StaffType;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@JsonDeserialize(as = Staff.class)
@Entity
@DiscriminatorValue("S")
public class Staff extends Person {

    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    @Enumerated(EnumType.STRING)
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

    @Override
    public int compareTo(Person o) {
        if(Objects.equals(this.getId(), o.getId()) &&
                StringUtils.equalsIgnoreCase(this.getForename(), o.getForename()) &&
                StringUtils.equalsIgnoreCase(this.getSurname(), o.getSurname())) {
            return 0;
        }
        return -1;
    }
}
