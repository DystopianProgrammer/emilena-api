package com.perks.emilena.validation;

import com.perks.emilena.api.Availability;
import com.perks.emilena.api.RotaItem;

/**
 * Emilena project copyright - 2016
 * Created by Geoff Perks.
 * Date: 20/12/2016
 */
public class ValidateBookableAppointment implements ValidateCompare<RotaItem, Availability> {


    /**
     * Validates that the availability is suitable for an appointment with a given rota item
     * @param first the rota item containing the start and end time for an appointment
     * @param second - the availability for a given person
     * @return true is bookable appointment, otherwise false
     */
    @Override
    public Boolean isValid(RotaItem first, Availability second) {

        boolean isStartInRange =
                second.getFromTime().isBefore(first.getStart()) || second.getFromTime().equals(first.getStart());

        boolean isFinishInRange =
                second.getToTime().isAfter(first.getFinish()) || second.getToTime().equals(first.getFinish());

        return isStartInRange && isFinishInRange;
    }
}
