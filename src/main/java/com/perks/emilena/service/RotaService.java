package com.perks.emilena.service;

import com.perks.emilena.api.Rota;
import com.perks.emilena.api.RotaItem;
import com.perks.emilena.dao.RotaDAO;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class RotaService {

    private final RotaItemService rotaItemService;
    private final RotaDAO rotaDAO;

    public RotaService(RotaItemService rotaItemService, RotaDAO rotaDAO) {
        this.rotaItemService = rotaItemService;
        this.rotaDAO = rotaDAO;
    }

    public Rota create(LocalDate weekStarting) {

        List<RotaItem> rotaItems = rotaItemService.rotaItems(weekStarting);

        Rota rota = new Rota();
        rota.setWeekStarting(weekStarting);
        rota.setRotaItems(rotaItems);

        return rota;
    }

    public void update(Rota rota) {
        this.rotaDAO.update(rota);
    }
}

