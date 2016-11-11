package com.perks.emilena.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.Person;

import java.io.IOException;

/**
 * Created by 466707 on 11/11/2016.
 */
public class PersonDeserializer extends JsonDeserializer<Person> {


    /**
     * This is a really nasty hack. Because of the bi-direction one to many relationship, and because of the inheritence
     * of the abstract class, and because we persist availabilities and cascade references to the staff and clients,
     * person is never set on the availability. Not at this point anyway, so this requires some futher investigation,
     * but the problem being is I actually have no idea what the hell is going on with jackson and bi-directional mappings
     * with abstract classes. It gets a bit crazy.
     *
     * This only ever seems to get called from the reference from Availability -> Person. Person at this point is always null
     * so this doesn't seem to cause problems - YET!
     */
    @Override
    public Person deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        return new Client();
    }
}
