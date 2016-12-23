package com.perks.emilena.validation;

import com.perks.emilena.api.Availability;
import com.perks.emilena.api.RotaItem;

import java.util.Set;

/**
 * Emilena project copyright - 2016
 * Created by Geoff Perks.
 * Date: 21/12/2016
 */
public class ValidateTimeConflict implements ValidateCompare<RotaItem, RotaItem> {

    /**
     * Checks that first rota item (already assigned) doesn't conflict with second rota item (proposed)
     *
     * @param existing - the already assigned and planned rota item (appointment)
     * @param proposed - the proposed rota item (appointment)
     * @return true if valid, otherwise false
     */
    @Override
    public Boolean isValid(RotaItem existing, RotaItem proposed) {

        if (existing.equals(proposed)) return false; // pure duplicate
        if (proposed.getStart().equals(existing.getStart())) return false;
        if (proposed.getFinish().equals(existing.getFinish())) return false;
        if (proposed.getStart().isAfter(existing.getStart()) &&
                proposed.getFinish().isBefore(existing.getFinish())) return false;
        if (proposed.getStart().isBefore(existing.getStart()) &&
                proposed.getFinish().isAfter(existing.getStart()) &&
                proposed.getFinish().isBefore(existing.getFinish())) return false;


        return true;
    }
}
