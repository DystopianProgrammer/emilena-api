package com.perks.emilena.service;

import com.perks.emilena.api.Client;
import com.perks.emilena.dao.ClientDAO;
import com.perks.emilena.dao.StaffDAO;

import java.time.DayOfWeek;
import java.util.List;

/**
 * More a delegate than a service really, but creates a separation from the resources and the DAOs and faciliates
 * adding appropriate logic if necessary
 *
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public class ClientService extends PersonService {

    public ClientService(ClientDAO clientDAO, StaffDAO staffDAO) {
        super(clientDAO, staffDAO);
    }

    public void updateClient(Client client) {
        this.clientDAO.create(transform(client));
    }

    public void deleteClient(Client client) {
        this.clientDAO.delete(client);
    }

    public List<Client> listAllClients() {
        return this.clientDAO.findAll();
    }

    public List<Client> listAllActiveClients() {
        return this.clientDAO.findAllActive();
    }

    public Client findById(Long id) {
        return this.clientDAO.findById(id);
    }

    public List<Client> listClientsByAvailability(DayOfWeek dayOfWeek) {
        return clientDAO.clientAvailabilityByDayOfWeek(dayOfWeek);
    }

}
