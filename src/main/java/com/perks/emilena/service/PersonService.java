package com.perks.emilena.service;

import com.perks.emilena.api.Person;
import io.dropwizard.hibernate.AbstractDAO;

/**
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
interface PersonService<T extends Person> {

    T create (T person);

    <E extends AbstractDAO<E>> E getDataAccessObject();
}
