package com.perks.emilena.api;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "com.perks.emilena.Client.findAll",
                query = "select c from Client c")
})
public class Client extends Person {
}
