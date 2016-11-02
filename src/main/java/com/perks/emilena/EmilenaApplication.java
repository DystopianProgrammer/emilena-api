package com.perks.emilena;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.perks.emilena.api.Appointment;
import com.perks.emilena.api.SystemUser;
import com.perks.emilena.config.EmilenaConfiguration;
import com.perks.emilena.dao.AbsenceDAO;
import com.perks.emilena.dao.AppointmentDAO;
import com.perks.emilena.dao.AvailabilityDAO;
import com.perks.emilena.dao.ClientDAO;
import com.perks.emilena.dao.StaffDAO;
import com.perks.emilena.dao.SystemUserDAO;
import com.perks.emilena.resource.AbsenceResource;
import com.perks.emilena.resource.AlertsResource;
import com.perks.emilena.resource.AppointmentResource;
import com.perks.emilena.resource.AvailabilityResource;
import com.perks.emilena.resource.ClientResource;
import com.perks.emilena.resource.InvoiceResource;
import com.perks.emilena.resource.RotaResource;
import com.perks.emilena.resource.StaffResource;
import com.perks.emilena.resource.UserResource;
import com.perks.emilena.security.CustomCredentialAuthFilter;
import com.perks.emilena.security.SimpleAuthenticator;
import com.perks.emilena.security.SimpleAuthorizer;
import com.perks.emilena.service.AlertsService;
import com.perks.emilena.service.AppointmentService;
import com.perks.emilena.service.AppointmentServiceImpl;
import com.perks.emilena.service.ClientService;
import com.perks.emilena.service.RotaService;
import com.perks.emilena.service.StaffService;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.CachingAuthenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class EmilenaApplication extends Application<EmilenaConfiguration> {

    public static void main(String[] args) throws Exception {
        new EmilenaApplication().run(args);
    }

    public void run(EmilenaConfiguration emilenaConfiguration, Environment environment) throws Exception {

        environment.getObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // DAOs
        StaffDAO staffDAO = new StaffDAO(scanningHibernate.getSessionFactory());
        ClientDAO clientDAO = new ClientDAO(scanningHibernate.getSessionFactory());
        AbsenceDAO absenceDAO = new AbsenceDAO(scanningHibernate.getSessionFactory());
        AvailabilityDAO availabilityDAO = new AvailabilityDAO(scanningHibernate.getSessionFactory());
        AppointmentDAO appointmentDAO = new AppointmentDAO((scanningHibernate.getSessionFactory()));
        SystemUserDAO systemUserDAO = new SystemUserDAO(scanningHibernate.getSessionFactory());

        // Services
        ClientService clientService = new ClientService(clientDAO);
        StaffService staffService = new StaffService(staffDAO, systemUserDAO);
        AppointmentService<Appointment> appointmentService = new AppointmentServiceImpl(appointmentDAO);
        AlertsService alertsService = new AlertsService(appointmentDAO);
        RotaService rotaService = new RotaService(clientDAO);

        // Resources
        environment.jersey().register(new AvailabilityResource(availabilityDAO));
        environment.jersey().register(new StaffResource(staffService, staffDAO));
        environment.jersey().register(new ClientResource(clientService, clientDAO));
        environment.jersey().register(new AbsenceResource(absenceDAO));
        environment.jersey().register(new UserResource());
        environment.jersey().register(new AppointmentResource(appointmentService));
        environment.jersey().register(new InvoiceResource(appointmentService));
        environment.jersey().register(new AlertsResource(alertsService));
        environment.jersey().register(new RotaResource(rotaService));

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
        // If you want to use @Auth to inject a custom Principal type into your resource
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
