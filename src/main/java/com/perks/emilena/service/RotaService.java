package com.perks.emilena.service;

import com.perks.emilena.api.Rota;
import com.perks.emilena.api.RotaItem;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class RotaService {

    private final RotaItemService rotaItemService;

    public RotaService(RotaItemService rotaItemService) {
        this.rotaItemService = rotaItemService;
    }

    public Rota create(LocalDate weekStarting) {

        List<RotaItem> rotaItems = rotaItemService.rotaItems(weekStarting);

        Rota rota = new Rota();
        rota.setWeekStarting(weekStarting);
        rota.setRotaItems(rotaItems);

        return rota;
    }
}

