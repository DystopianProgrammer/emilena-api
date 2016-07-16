package com.perks.emilena.api;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Embeddable
public class Address implements Serializable {

    @Column
    private String houseNumber;

    @Column
    private String firstLine;

    @Column
    private String secondLine;

    @Column
    private String town;

    @Column
    private String postCode;

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getFirstLine() {
        return firstLine;
    }

    public void setFirstLine(String firstLine) {
        this.firstLine = firstLine;
    }

    public String getSecondLine() {
        return secondLine;
    }

    public void setSecondLine(String secondLine) {
        this.secondLine = secondLine;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(houseNumber, address.houseNumber) &&
                Objects.equals(firstLine, address.firstLine) &&
                Objects.equals(secondLine, address.secondLine) &&
                Objects.equals(town, address.town) &&
                Objects.equals(postCode, address.postCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseNumber, firstLine, secondLine, town, postCode);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Address{");
        sb.append("houseNumber='").append(houseNumber).append('\'');
        sb.append(", town='").append(town).append('\'');
        sb.append(", postCode='").append(postCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
