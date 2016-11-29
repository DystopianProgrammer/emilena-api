package com.perks.emilena;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.perks.emilena.api.SystemUser;
import com.perks.emilena.config.EmilenaConfiguration;
import com.perks.emilena.dao.*;
import com.perks.emilena.resource.*;
import com.perks.emilena.security.CustomCredentialAuthFilter;
import com.perks.emilena.security.SimpleAuthenticator;
import com.perks.emilena.security.SimpleAuthorizer;
import com.perks.emilena.service.*;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.CachingAuthenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.client.Client;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class EmilenaApplication extends Application<EmilenaConfiguration> {

    public static void main(String[] args) throws Exception {
        new EmilenaApplication().run(args);
    }

    @Override
    public void run(EmilenaConfiguration emilenaConfiguration, Environment environment) throws Exception {

        environment.getObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Jersey client
        final Client client = new JerseyClientBuilder(environment)
                .using(emilenaConfiguration.getJerseyClientConfiguration())
                .build(getName());

        // DAOs
        StaffDAO staffDAO = new StaffDAO(scanningHibernate.getSessionFactory());
        ClientDAO clientDAO = new ClientDAO(scanningHibernate.getSessionFactory());
        AvailabilityDAO availabilityDAO = new AvailabilityDAO(scanningHibernate.getSessionFactory());
        SystemUserDAO systemUserDAO = new SystemUserDAO(scanningHibernate.getSessionFactory());
        RotaDAO rotaDAO = new RotaDAO(scanningHibernate.getSessionFactory());
        RotaItemDAO rotaItemDAO = new RotaItemDAO(scanningHibernate.getSessionFactory());
        TrafficDAO trafficDAO = new TrafficDAO((scanningHibernate.getSessionFactory()));
        InvoiceDAO invoiceDAO = new InvoiceDAO(scanningHibernate.getSessionFactory());

        // Services
        LocationService locationService = new LocationService(client, emilenaConfiguration.getApplicationConfiguration());
        AppointmentService appointmentService =
                new AppointmentService(emilenaConfiguration.getApplicationConfiguration(), locationService);
        RotaItemService rotaItemService = new RotaItemService(staffDAO, clientDAO, appointmentService);
        RotaService rotaService = new RotaService(rotaItemService, rotaDAO);
        InvoiceService invoiceService = new InvoiceService(invoiceDAO, rotaDAO);

        // Resources
        environment.jersey().register(new AvailabilityResource(availabilityDAO));
        environment.jersey().register(new StaffResource(staffDAO));
        environment.jersey().register(new ClientResource(clientDAO));
        environment.jersey().register(new RotaResource(rotaService, rotaDAO));
        environment.jersey().register(new UserResource());
        environment.jersey().register(new TrafficResource(trafficDAO));
        environment.jersey().register(new InvoiceResource(invoiceDAO, invoiceService));
        environment.jersey().register(new RotaItemResource(rotaItemDAO));
        environment.jersey().register(new ConfigurationResource(emilenaConfiguration.getApplicationConfiguration()));
        environment.jersey().register(new ExternalServiceResource(client, emilenaConfiguration.getApplicationConfiguration()));

        // Security
        //
        // Currently creating transactions with the @UnitOfWork annotation works out-of-box only for resources managed by Jersey. If you want to use it
        // outside Jersey resources, e.g. in authenticators, you should instantiate your class with UnitOfWorkAwareProxyFactory
        // http://www.dropwizard.io/1.0.0/docs/manual/hibernate.html#transactional-resource-methods
        SimpleAuthenticator simpleAuthenticator = new SimpleAuthenticator(systemUserDAO);

        CachingAuthenticator<BasicCredentials, SystemUser> cachingAuthenticator = new CachingAuthenticator<>(
                environment.metrics(), simpleAuthenticator, emilenaConfiguration.getAuthenticationCachePolicy());

        environment.jersey().register(new AuthDynamicFeature(
                new CustomCredentialAuthFilter.Builder<SystemUser>().setAuthenticator(cachingAuthenticator)
                        .setAuthorizer(new SimpleAuthorizer())
                        .setRealm("EMILENA")
                        .buildAuthFilter()));

        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(SystemUser.class));
    }

    @Override
    public String getName() {
        return "Emilena";
    }

    @Override
    public void initialize(Bootstrap<EmilenaConfiguration> bootstrap) {
        bootstrap.addBundle(scanningHibernate);
    }

    private final ScanningHibernateBundle<EmilenaConfiguration> scanningHibernate =
            new ScanningHibernateBundle<EmilenaConfiguration>("com.perks.emilena.api") {
                @Override
                public PooledDataSourceFactory getDataSourceFactory(EmilenaConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

}
