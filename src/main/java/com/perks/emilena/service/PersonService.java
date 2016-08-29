package com.perks.emilena.service;

import com.perks.emilena.api.Person;

/**
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
interface PersonService<T extends Person> {

    T create (T person);
}
