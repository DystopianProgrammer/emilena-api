package com.perks.emilena;

import com.perks.emilena.api.Person;
import com.perks.emilena.config.EmilenaConfiguration;
import com.perks.emilena.dao.StaffDAO;
import com.perks.emilena.resource.StaffResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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

        // Resources
        environment.jersey().register(new StaffResource(staffDAO));
    }

    @Override
    public String getName() {
        return "Emilena";
    }

    @Override
    public void initialize(Bootstrap<EmilenaConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    private final HibernateBundle<EmilenaConfiguration> hibernate = new HibernateBundle<EmilenaConfiguration>(Person.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(EmilenaConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };
}
