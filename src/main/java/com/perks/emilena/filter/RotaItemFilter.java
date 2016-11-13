package com.perks.emilena.filter;

import com.perks.emilena.api.Rota;
import com.perks.emilena.api.RotaItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class RotaItemFilter {

    private static final Logger logger = LoggerFactory.getLogger(RotaItemFilter.class);

    public Rota removeDuplicates(Rota rota) {
        logger.info("Removing duplicate items -- filtering rota");
        Rota filtered = new Rota();
        filtered.setWeekStarting(rota.getWeekStarting());
        filtered.setRotaItems(visit(rota.getRotaItems()));

        return filtered;
    }

    private List<RotaItem> visit(List<RotaItem> rotaItems) {

        List<RotaItem> filtered = new ArrayList<>();
        for (int i = 0; i < rotaItems.size(); i++) {
            boolean hasItem = false;
            for (int k = 0; k < filtered.size(); k++) {
                hasItem = (filtered.get(k).compareTo(rotaItems.get(i)) == 0);
            }
            if (!hasItem) {
                filtered.add(rotaItems.get(i));
            } else {
                logger.info("Found duplicate item");
            }
        }

        return filtered;
    }
}
