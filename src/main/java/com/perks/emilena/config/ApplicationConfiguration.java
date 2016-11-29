package com.perks.emilena.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class ApplicationConfiguration {

    @Valid
    @NotNull
    @JsonProperty
    private String postCodeServiceUrl;

    @Valid
    @NotNull
    @JsonProperty
    private String apiKey;

    @Valid
    @NotNull
    @JsonProperty
    private String googleMapsDistanceMatrixApiUrl;

    @Valid
    @NotNull
    @JsonProperty
    private String googleMapsDistanceMatrixApiKey;

    @Valid
    @NotNull
    @JsonProperty
    private Integer maxDistanceRadius;

    @Valid
    @NotNull
    @JsonProperty
    private Integer minDistanceRadius;

    @Valid
    @NotNull
    @JsonProperty
    private String defaultAddress;

    @Valid
    @NotNull
    @JsonProperty
    private String theme;

    @Valid
    @NotNull
    @JsonProperty
    private String organisation;

    public String getPostCodeServiceUrl() {
        return postCodeServiceUrl;
    }

    public void setPostCodeServiceUrl(String postCodeServiceUrl) {
        this.postCodeServiceUrl = postCodeServiceUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getGoogleMapsDistanceMatrixApiUrl() {
        return googleMapsDistanceMatrixApiUrl;
    }

    public void setGoogleMapsDistanceMatrixApiUrl(String googleMapsDistanceMatrixApiUrl) {
        this.googleMapsDistanceMatrixApiUrl = googleMapsDistanceMatrixApiUrl;
    }

    public String getGoogleMapsDistanceMatrixApiKey() {
        return googleMapsDistanceMatrixApiKey;
    }

    public void setGoogleMapsDistanceMatrixApiKey(String googleMapsDistanceMatrixApiKey) {
        this.googleMapsDistanceMatrixApiKey = googleMapsDistanceMatrixApiKey;
    }

    public Integer getMaxDistanceRadius() {
        return maxDistanceRadius;
    }

    public void setMaxDistanceRadius(Integer maxDistanceRadius) {
        this.maxDistanceRadius = maxDistanceRadius;
    }

    public Integer getMinDistanceRadius() {
        return minDistanceRadius;
    }

    public void setMinDistanceRadius(Integer minDistanceRadius) {
        this.minDistanceRadius = minDistanceRadius;
    }

    public String getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(String defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }
}
