package com.perks.emilena.service;

import com.perks.emilena.api.Client;
import com.perks.emilena.dao.ClientDAO;

/**
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public class ClientService implements PersonService<Client> {

    private final ClientDAO clientDao;

    public ClientService(ClientDAO clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public Client create(Client person) {
        person.setActive(true);
        return clientDao.create(person);
    }
}
