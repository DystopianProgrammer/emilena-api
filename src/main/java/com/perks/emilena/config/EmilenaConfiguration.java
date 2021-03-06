package com.perks.emilena.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.cache.CacheBuilderSpec;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class EmilenaConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    @Valid
    @NotNull
    @JsonProperty
    private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

    @Valid
    @NotNull
    @JsonProperty
    private ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();

    @Valid
    @NotNull
    @JsonProperty
    private CacheBuilderSpec authenticationCachePolicy;

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    public CacheBuilderSpec getAuthenticationCachePolicy() {
        return authenticationCachePolicy;
    }

    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClient;
    }

    public ApplicationConfiguration getApplicationConfiguration() {
        return applicationConfiguration;
    }
}
