package com.perks.emilena;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.perks.emilena.api.Absence;
import com.perks.emilena.api.Availability;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.Person;
import com.perks.emilena.api.Staff;
import com.perks.emilena.config.EmilenaConfiguration;
import com.perks.emilena.dao.AbsenceDAO;
import com.perks.emilena.dao.AvailabilityDAO;
import com.perks.emilena.dao.ClientDAO;
import com.perks.emilena.dao.StaffDAO;
import com.perks.emilena.resource.AbsenceResource;
import com.perks.emilena.resource.AvailabilityResource;
import com.perks.emilena.resource.ClientResource;
import com.perks.emilena.resource.StaffResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.text.SimpleDateFormat;

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

        // Resources
        environment.jersey().register(new AvailabilityResource(availabilityDAO));
        environment.jersey().register(new StaffResource(staffDAO));
        environment.jersey().register(new ClientResource(clientDAO));
        environment.jersey().register(new AbsenceResource(absenceDAO));
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
            new HibernateBundle<EmilenaConfiguration>(Person.class, Staff.class, Client.class, Absence.class, Availability.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(EmilenaConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };
}
