package com.perks.emilena;

import com.perks.emilena.api.Absence;
import com.perks.emilena.api.Appointment;
import com.perks.emilena.api.Availability;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.GeneralAvailability;
import com.perks.emilena.api.Person;
import com.perks.emilena.api.Role;
import com.perks.emilena.api.Staff;
import com.perks.emilena.api.SystemUser;
import com.perks.emilena.config.EmilenaConfiguration;
import com.perks.emilena.dao.AbsenceDAO;
import com.perks.emilena.dao.AppointmentDAO;
import com.perks.emilena.dao.AvailabilityDAO;
import com.perks.emilena.dao.ClientDAO;
import com.perks.emilena.dao.StaffDAO;
import com.perks.emilena.dao.SystemUserDAO;
import com.perks.emilena.resource.AbsenceResource;
import com.perks.emilena.resource.AppointmentResource;
import com.perks.emilena.resource.AvailabilityResource;
import com.perks.emilena.resource.ClientResource;
import com.perks.emilena.resource.StaffResource;
import com.perks.emilena.resource.UserResource;
import com.perks.emilena.security.CustomCredentialAuthFilter;
import com.perks.emilena.security.SimpleAuthenticator;
import com.perks.emilena.security.SimpleAuthorizer;
import com.perks.emilena.security.User;
import com.perks.emilena.service.ClientService;
import com.perks.emilena.service.StaffService;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.CachingAuthenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
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

        // DAOs
        StaffDAO staffDAO = new StaffDAO(hibernate.getSessionFactory());
        ClientDAO clientDAO = new ClientDAO(hibernate.getSessionFactory());
        AbsenceDAO absenceDAO = new AbsenceDAO(hibernate.getSessionFactory());
        AvailabilityDAO availabilityDAO = new AvailabilityDAO(hibernate.getSessionFactory());
        AppointmentDAO appointmentDAO = new AppointmentDAO((hibernate.getSessionFactory()));

        // Services
        ClientService clientService = new ClientService(clientDAO);
        StaffService staffService = new StaffService(staffDAO);

        // Resources
        environment.jersey().register(new AvailabilityResource(availabilityDAO));
        environment.jersey().register(new StaffResource(staffService));
        environment.jersey().register(new ClientResource(clientService));
        environment.jersey().register(new AbsenceResource(absenceDAO));
        environment.jersey().register(new UserResource());
        environment.jersey().register(new AppointmentResource(appointmentDAO));

        SystemUserDAO systemUserDAO = new SystemUserDAO(hibernate.getSessionFactory());

        // Security
        //
        // Currently creating transactions with the @UnitOfWork annotation works out-of-box only for resources managed by Jersey. If you want to use it
        // outside Jersey resources, e.g. in authenticators, you should instantiate your class with UnitOfWorkAwareProxyFactory
        // http://www.dropwizard.io/1.0.0/docs/manual/hibernate.html#transactional-resource-methods
        SimpleAuthenticator simpleAuthenticator = new SimpleAuthenticator(systemUserDAO);

        CachingAuthenticator<BasicCredentials, User> cachingAuthenticator = new CachingAuthenticator<>(
                environment.metrics(), simpleAuthenticator, emilenaConfiguration.getAuthenticationCachePolicy());

        environment.jersey().register(new AuthDynamicFeature(
                new CustomCredentialAuthFilter.Builder<User>().setAuthenticator(cachingAuthenticator)
                        .setAuthorizer(new SimpleAuthorizer())
                        .setRealm("EMILENA")
                        .buildAuthFilter()));

        environment.jersey().register(RolesAllowedDynamicFeature.class);
        // If you want to use @Auth to inject a custom Principal type into your resource
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

    @Override
    public String getName() {
        return "Emilena";
    }

    @Override
    public void initialize(Bootstrap<EmilenaConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    private final HibernateBundle<EmilenaConfiguration> hibernate =
            new HibernateBundle<EmilenaConfiguration>(
                    Person.class,
                    Staff.class,
                    Client.class,
                    Absence.class,
                    Availability.class,
                    SystemUser.class,
                    GeneralAvailability.class,
                    Appointment.class,
                    Role.class) {

                @Override
                public DataSourceFactory getDataSourceFactory(EmilenaConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };
}
