package com.perks.emilena.api;

/**
 * Created by 466707 on 05/11/2016.
 */
public class Grouping {

    private Client client;

    private Staff staff;

    private Availability clientAvailability;


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Availability getClientAvailability() {
        return clientAvailability;
    }

    public void setClientAvailability(Availability clientAvailability) {
        this.clientAvailability = clientAvailability;
    }
}
