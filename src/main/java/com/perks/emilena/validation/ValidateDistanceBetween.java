package com.perks.emilena.validation;

import com.perks.emilena.api.Availability;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.RotaItem;
import com.perks.emilena.api.Staff;
import com.perks.emilena.config.ApplicationConfiguration;
import com.perks.emilena.service.LocationService;
import com.perks.emilena.value.DistanceMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Emilena project copyright - 2016
 * Created by Geoff Perks.
 * Date: 20/12/2016
 */
public class ValidateDistanceBetween implements ValidateCompare<Staff, Client> {

    private static final Logger LOG = LoggerFactory.getLogger(ValidateDistanceBetween.class);
    private final LocationService locationService;
    private final ApplicationConfiguration configuration;

    public ValidateDistanceBetween(LocationService locationService, ApplicationConfiguration configuration) {
        this.locationService = locationService;
        this.configuration = configuration;
    }

    @Override
    public Boolean isValid(Staff first, Client second) {

        DistanceMatrix distanceMatrix;

        try {
            distanceMatrix = locationService.distanceMatrix(
                    first.getAddress().getPostCode(), second.getAddress().getPostCode())
                    .orElseThrow(() ->
                            new RuntimeException("Unable to find location for either " + first + " or " + second));
        }catch (RuntimeException e) {
            LOG.error("Unable to validate distances between client and staff", e);
            return false;
        }

        if(distanceMatrix != null) {
            Double milesApart = locationService.convertMetersToMiles(distanceMatrix.getMeters());
            return (milesApart <= configuration.getMaxDistanceRadius() && milesApart >= configuration.getMinDistanceRadius());
        }

        return false;
    }
}
