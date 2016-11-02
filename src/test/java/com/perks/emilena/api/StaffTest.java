package com.perks.emilena.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perks.emilena.api.type.ContractType;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by Geoff Perks
 * Date: 07/08/2016.
 */
public class StaffTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {

        Address address = new Address();
        address.setHouseNumber("128");
        address.setFirstLine("Stapley Road");
        address.setTown("Hove");
        address.setPostCode("BN3 7FG");

        Staff person = new Staff();
        person.setForename("Emily");
        person.setSurname("Boyle");
        person.setEmail("emily.boyle@live.com");
        person.setTelephoneNumber("07730890337");
        person.setContractType(ContractType.CONTRACTOR);
        person.setContractedHours(16);

        person.setAddress(address);

        String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/staff.json"), Staff.class));

        assertThat(MAPPER.writeValueAsString(person)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {

        Address address = new Address();
        address.setHouseNumber("128");
        address.setFirstLine("Stapley Road");
        address.setTown("Hove");
        address.setPostCode("BN3 7FG");

        Staff person = new Staff();
        person.setForename("Emily");
        person.setSurname("Boyle");
        person.setEmail("emily.boyle@live.com");
        person.setTelephoneNumber("07730890337");
        person.setContractType(ContractType.CONTRACTOR);
        person.setContractedHours(16);

        person.setAddress(address);

        assertThat(MAPPER.readValue(fixture("fixtures/staff.json"), Staff.class)).isEqualTo(person);
    }
}