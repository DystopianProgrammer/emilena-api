package com.perks.emilena.service;

import com.perks.emilena.config.ApplicationConfiguration;
import com.perks.emilena.error.UnknownAddressMappingError;
import com.perks.emilena.mapper.DistanceMatrixMapper;
import com.perks.emilena.value.DistanceMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class LocationService {

    private static final Logger LOG = LoggerFactory.getLogger(LocationService.class);
    private static final Double METERS_PER_MILE = 1609.344;

    private final Client client;
    private final ApplicationConfiguration applicationConfiguration;

    public LocationService(Client client, ApplicationConfiguration applicationConfiguration) {
        this.client = client;
        this.applicationConfiguration = applicationConfiguration;
    }

    public Optional<DistanceMatrix> distanceMatrix(String origin, String destination) {

        WebTarget webTarget = client.target(applicationConfiguration.getGoogleMapsDistanceMatrixApiUrl())
                .path("maps")
                .path("api")
                .path("distancematrix")
                .path("json")
                .queryParam("units", "imperial")
                .queryParam("origins", origin)
                .queryParam("destinations", destination)
                .queryParam("key", applicationConfiguration.getGoogleMapsDistanceMatrixApiKey());


        Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).get();
        if(response.getStatus() == Response.Status.OK.getStatusCode()) {
            DistanceMatrix distanceMatrix = null;
            try {
                distanceMatrix = new DistanceMatrixMapper().transformResponse(response.readEntity(HashMap.class));
            }catch(UnknownAddressMappingError e) {
                LOG.warn("Could not resolve address mapping for either {} or {}", origin, destination, e);
            }
            return ofNullable(distanceMatrix);
        }

        return Optional.empty();
    }

    public Double convertMetersToMiles(Long meters) {
        return meters / METERS_PER_MILE;
    }
}
