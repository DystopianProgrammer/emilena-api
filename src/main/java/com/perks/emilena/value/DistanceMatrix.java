package com.perks.emilena.value;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class DistanceMatrix {

    private final String destinationAddress;
    private final String originAddress;
    private final Integer duration;
    private final Long meters;

    public DistanceMatrix(String destinationAddress, String originAddress, Integer duration, Long meters) {
        this.destinationAddress = destinationAddress;
        this.originAddress = originAddress;
        this.duration = duration;
        this.meters = meters;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public Integer getDuration() {
        return duration;
    }

    public Long getMeters() {
        return meters;
    }
}
