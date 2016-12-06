package com.perks.emilena.service;

import com.perks.emilena.api.Client;
import com.perks.emilena.api.Staff;
import com.perks.emilena.api.type.AddressType;
import com.perks.emilena.config.ApplicationConfiguration;
import com.perks.emilena.value.Appointment;
import com.perks.emilena.value.DistanceMatrix;
import com.perks.emilena.value.TemporalStore;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.upperCase;

/**
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public class AllocationService {

    private static final Logger LOG = LoggerFactory.getLogger(AllocationService.class);

    private final LocationService locationService;
    private final ApplicationConfiguration configuration;

    public AllocationService(LocationService locationService, ApplicationConfiguration configuration) {
        this.locationService = requireNonNull(locationService);
        this.configuration = requireNonNull(configuration);
    }

    /**
     * Creates a matrix of appointments based on location, that is if client and staff are within allowable radius.
     *
     * @param staffTemporalStores  - the staff for this day
     * @param clientTemporalStores - the client for this day
     * @return a matrix of allowable appointments.
     */
    List<List<Appointment>> allocateByRadius(List<TemporalStore> staffTemporalStores,
                                                 List<TemporalStore> clientTemporalStores) {

        Integer minDistanceRadius =
                requireNonNull(configuration.getMinDistanceRadius(),
                        "minDistanceRadius must not be null - have you configured this in the yml?");
        Integer maxDistanceRadius =
                requireNonNull(configuration.getMaxDistanceRadius(),
                        "maxDistanceRadius must not be null - have you configured this in the yml?");


        // for each staff temporal, check client distance. If within range, add to list
        List<List<Appointment>> groupedByRadius = new ArrayList<>();
        group(groupedByRadius, staffTemporalStores, clientTemporalStores, minDistanceRadius, maxDistanceRadius);

        return groupedByRadius;
    }

    private void group(List<List<Appointment>> groupedByRadius,
                       List<TemporalStore> staffTemporalStores,
                       List<TemporalStore> clientTemporalStores,
                       Integer minDistanceRadius, Integer maxDistanceRadius) {

        if (staffTemporalStores.size() == 0) return;

        TemporalStore sts = staffTemporalStores.get(0);
        List<Appointment> appointments = new ArrayList<>();

        Consumer<TemporalStore> clientConsumer = (cts) ->
                locationService.distanceMatrix(upperCase(cts.getPostCode()),
                        upperCase(sts.getPostCode()))
                        .ifPresent(dm -> allocate(minDistanceRadius, maxDistanceRadius, sts, cts, dm)
                                .ifPresent(appointments::add));

        // then compare against each client
        clientTemporalStores.forEach(clientConsumer);

        if (!appointments.isEmpty()) groupedByRadius.add(appointments);

        staffTemporalStores.remove(0);

        group(groupedByRadius, staffTemporalStores, clientTemporalStores, minDistanceRadius, maxDistanceRadius);
    }

    private Optional<Appointment> allocate(Integer minDistanceRadius,
                                           Integer maxDistanceRadius,
                                           TemporalStore sts,
                                           TemporalStore cts,
                                           DistanceMatrix dm) {

        Double miles = locationService.convertMetersToMiles(dm.getMeters());
        LOG.info("Comparing post codes {} and {} for max and min radii", upperCase(cts.getPostCode()), upperCase(sts.getPostCode()));

        if (miles <= maxDistanceRadius && miles >= minDistanceRadius) {
            return Optional.ofNullable(create(sts, cts, dm));
        }

        return Optional.empty();
    }

    private Appointment create(TemporalStore sts, TemporalStore cts, DistanceMatrix dm) {

        AddressType addressType = requireNonNull(AddressType.valueOf(configuration.getDefaultAddress()));

        Appointment.Builder builder = new Appointment.Builder();
        builder.staff((Staff) sts.getPerson())
                .client((Client) cts.getPerson())
                .distanceMatrix(dm)
                .dayOfWeek(cts.getAvailability().getDayOfWeek())
                .start(cts.getAvailability().getFromTime())
                .finish(cts.getAvailability().getToTime());

        if (AddressType.CLIENT.equals(addressType)) {
            builder.address(cts.getPerson().getAddress());
        } else {
            builder.address(sts.getPerson().getAddress());
        }

        return builder.build();
    }
}
